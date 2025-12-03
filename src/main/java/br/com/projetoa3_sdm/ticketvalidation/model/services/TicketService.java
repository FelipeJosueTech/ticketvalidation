package br.com.projetoa3_sdm.ticketvalidation.model.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import br.com.projetoa3_sdm.ticketvalidation.model.entities.Bank;
import br.com.projetoa3_sdm.ticketvalidation.model.entities.boleto;
import br.com.projetoa3_sdm.ticketvalidation.model.repositories.BankRepository;
import br.com.projetoa3_sdm.ticketvalidation.model.repositories.BoletoRepository;
import br.com.projetoa3_sdm.ticketvalidation.model.services.dto.TicketRequest;

@Service
public class TicketService {
	
	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private BoletoRepository boletoRepository;

	private static final LocalDate DATA_BASE = LocalDate.of(2022, 05, 29);

	// Método para validar o boleto    
	public boleto validate (TicketRequest request) {
		// Cria o objeto boleto com os dados do request (dados fornecidos pelo cliente)
		boleto boletoAux = new boleto(request.getlinhaDigitavel(), request.getnomeInformado());
		
		if (request.getValor() != null && !request.getValor().isBlank()) {
			try {
				java.math.BigDecimal v = new java.math.BigDecimal(request.getValor().replace(',', '.'));
				boletoAux.setValor(v);
			} catch (NumberFormatException e) {
				boletoAux.setValor(null);
			}
		}
		
		boletoAux.setBeneficiario(request.getBeneficiario());
		boletoAux.setPagador(request.getPagador());

		try {

			boleto boleto = parseAux(request.getlinhaDigitavel(), request.getnomeInformado());

			if (request.getPagador() != null && !request.getPagador().isBlank()) {
				boleto.setPagador(request.getPagador());
			}

			if (request.getBeneficiario() != null && !request.getBeneficiario().isBlank()) {
				boleto.setBeneficiario(request.getBeneficiario());
			}

			boolean isFraude = validateFraud(boleto, boletoAux);
			boleto.setFraude(isFraude);

			saveBoletoSafe(boleto);

			return boleto;
		} catch (Exception ex) {
			System.out.println("Erro durante validação de boleto: " + ex.getMessage());

			boletoAux.setFraude(true);
			saveBoletoSafe(boletoAux);
			return boletoAux;
		}
	}

	private void saveBoletoSafe(boleto b) {
		try {
			boletoRepository.save(b);
		} catch (Exception e) {
			System.out.println("Erro ao salvar boleto: " + e.getMessage());
		}
	}

	public boleto parse(TicketRequest request) {
		String nomeDoBanco = null; 
		return parseAux(request.getlinhaDigitavel(), nomeDoBanco);
	}

	public boleto parseAux(String linhaDigitavel, String nomeInformado) {
		boleto boleto = new boleto (linhaDigitavel, nomeInformado);

		boleto.setMoeda("REAL");

		boleto.setBeneficiario(null);
		boleto.setPagador(null);

		String codigoDeBarras = boleto.getlinhaDigitavel();
		LocalDate vencimento = obterDataVencimento(extractCampo5FromLinhaDigitavel(codigoDeBarras));

		if (vencimento != null) {
			boleto.setDataVencimento(vencimento);
		} else {
			boleto.setDataVencimento(null);
		}

		BigDecimal valor = extractValorBoleto(extractCampo5FromLinhaDigitavel(codigoDeBarras));
		boleto.setValor(valor);

		String codigoDoBanco = boleto.getlinhaDigitavel().substring(0, 3);
		Bank bank = bankRepository.findByCodigoDoBanco(codigoDoBanco).orElse(null);

		if (bank == null) {
			bank = new Bank();
    		bank.setCodigoDoBanco(codigoDoBanco);
			bank.setNomeDoBanco("Desconhecido");
		}

		boleto.setNomeDoBanco(bank.getNomeDoBanco());

		boleto.setNomeInformado(nomeInformado);

		if (nomeInformado != null && !nomeInformado.isBlank()) {
			boleto.setNomeDoBanco(nomeInformado);
		} else {
			boleto.setNomeDoBanco(bank.getNomeDoBanco());
		}
		
		boleto.setNomeInformado(nomeInformado);

		return boleto;
	}

	private boolean validateFraud(boleto boletoParseado, boleto boletoInformado) {

		if (boletoParseado == null || boletoParseado.getlinhaDigitavel() == null) {
			return true;
		}

		String linha = apenasDigitos(boletoParseado.getlinhaDigitavel());
		if (linha.length() != 47) {
			return true;
		}

		String codigoDoBanco = linha.substring(0, 3);
		Bank bank = bankRepository.findByCodigoDoBanco(codigoDoBanco).orElse(null);
		if (bank == null) {
			return true;
		}

		String nomeCadastrado = normalize(bank.getNomeDoBanco());
		
		if (boletoInformado != null && boletoInformado.getNomeDoBanco() != null) {
			String nomeFornecido = normalize(boletoInformado.getNomeDoBanco());
			if (!nomeCadastrado.equals(nomeFornecido)) {
				return true;
			}
		}

		if (boletoInformado != null && boletoInformado.getValor() != null) {
			java.math.BigDecimal valorInformado = boletoInformado.getValor();
			java.math.BigDecimal valorExtraido = boletoParseado.getValor();

			if (valorExtraido == null || valorInformado.compareTo(valorExtraido) != 0) {
				return true;
			}
		}

		// compara beneficiario/pagador se informado pelo cliente
		if (boletoInformado != null) {
			String beneficiarioInformado = normalize(boletoInformado.getBeneficiario());
			if (!beneficiarioInformado.isEmpty()) {
				String beneficiarioExtraido = normalize(boletoParseado.getBeneficiario());
				if (!beneficiarioInformado.equals(beneficiarioExtraido)) return true;
			}
			String pagadorInformado = normalize(boletoInformado.getPagador());
			if (!pagadorInformado.isEmpty()) {
				String pagadorExtraido = normalize(boletoParseado.getPagador());
				if (!pagadorInformado.equals(pagadorExtraido)) return true;
			}
		}

		return false;
	}

	private String normalize(String s) {
		if (s == null) return "";
		String n = java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
			.replaceAll("\\p{M}", "");
		return n.trim().toLowerCase();
	}

	public List<Bank> getBankCodes() {
		return bankRepository.findAll();
	}

	private static String apenasDigitos(String s) {
        return s == null ? "" : s.replaceAll("\\D", "");
    }

	public static String extractCampo5FromLinhaDigitavel(String linhaDigitavel) {
        String s = apenasDigitos(linhaDigitavel);
        if (s.length() == 47) {
            return s.substring(33, 47);
        } 
        else {
            throw new IllegalArgumentException("Linha digitavel inválida. Esperado 47 dígitos. Recebido: " + s.length());
        }
    }

 	public static LocalDate obterDataVencimento(String campo5) {
        campo5 = campo5.replaceAll("\\D", "");
        if (campo5.length() != 14) {
            throw new IllegalArgumentException("Campo 5 deve ter 14 dígitos.");
        }
        String fatorStr = campo5.substring(0, 4);
        int fator = Integer.parseInt(fatorStr);
        if (fator == 0) {
            return null;
        }
        return DATA_BASE.plusDays(fator);
    }	 

	public static BigDecimal extractValorBoleto(String campo5) {
        campo5 = campo5.replaceAll("\\D", "");
        if (campo5.length() != 14) {
            throw new IllegalArgumentException("Campo 5 deve ter 14 dígitos.");
        }
        String valorStr = campo5.substring(5, 14); 
        long centavos = Long.parseLong(valorStr);
        return BigDecimal.valueOf(centavos).movePointLeft(2);
    }
}