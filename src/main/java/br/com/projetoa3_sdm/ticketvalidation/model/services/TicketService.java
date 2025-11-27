package br.com.projetoa3_sdm.ticketvalidation.model.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetoa3_sdm.ticketvalidation.model.entities.Bank;
import br.com.projetoa3_sdm.ticketvalidation.model.entities.boleto;
import br.com.projetoa3_sdm.ticketvalidation.model.repositories.BankRepository;
import br.com.projetoa3_sdm.ticketvalidation.model.services.dto.TicketRequest;

@Service
public class TicketService {
	
	@Autowired
	private BankRepository bankRepository;

	private static final LocalDate DATA_BASE = LocalDate.of(2022, 05, 29);

	// Lista fixa de nomes para simular pagadores
	private static final List<String> NOMES_PAGADOR = List.of(
		"Felipe Josué","Amanda Hellen","João José","Anna Maria");
	
	private static final List<String> NOMES_EMPRESAS = List.of(
		"Nextron Solutions","VerdeVale Alimentos","Alphacred Financeira","Luminor Energia");

	// Método para validar o boleto	
	public boleto validate (TicketRequest request) {
		
		// Cria o objeto boleto com os dados do request
		boleto boletoAux = new boleto(request.getlinhaDigitavel(), request.getnomeInformado());
		boleto boleto = parseAux(request.getlinhaDigitavel(), request.getnomeInformado());
		
		boolean isFraude = validateFraud(boletoAux);
		boleto.setFraude(isFraude);
		
		return boleto;
	}

	public boleto parse(TicketRequest request) {
		String nomeDoBanco = null; 

		return parseAux(request.getlinhaDigitavel(), nomeDoBanco);
	}

	public boleto parseAux(String linhaDigitavel, String nomeInformado) {
		boleto boleto = new boleto (linhaDigitavel, nomeInformado);

		boleto.setMoeda("REAL");
		//Define informações fixas do boleto
		boleto.setBeneficiario(getRandomNomeEmpresa());
		boleto.setPagador(getRandomNomePagador());

		// Extrai o código do banco a partir do código de barras
		String codigoDeBarras = boleto.getlinhaDigitavel();
		LocalDate vencimento = obterDataVencimento(extractCampo5FromLinhaDigitavel(codigoDeBarras));

		if (vencimento != null) {
			boleto.setDataVencimento(vencimento);
		} else {
			boleto.setDataVencimento(null); // Sem data de vencimento
		}

		// Extrai o valor do boleto a partir do código de barras
		BigDecimal valor = extractValorBoleto(extractCampo5FromLinhaDigitavel(codigoDeBarras));
		boleto.setValor(valor);

		// Obtém o nome do banco a partir do código do banco no código de barras
		String codigoDoBanco = boleto.getlinhaDigitavel().substring(0, 3);
		Bank bank = bankRepository.findByCodigoDoBanco(codigoDoBanco).orElse(null);

		if (bank == null) {
			bank = new Bank();
    		bank.setCodigoDoBanco(codigoDoBanco);
			bank.setNomeDoBanco("Desconhecido");
		}

		if (nomeInformado == null || nomeInformado.isBlank()) {
			boleto.setNomeDoBanco(bank.getNomeDoBanco());
		} else {
			boleto.setNomeDoBanco(nomeInformado);
		}

		// opcional: manter o nome informado separado se houver campo específico
		boleto.setNomeInformado(nomeInformado);

		return boleto;
	}

	private String getRandomNomePagador() {
		Random random = new Random();
		int index = random.nextInt(NOMES_PAGADOR.size());
		return NOMES_PAGADOR.get(index);
	}

	private String getRandomNomeEmpresa() {
		Random random = new Random();
		int index = random.nextInt(NOMES_EMPRESAS.size());
		return NOMES_EMPRESAS.get(index);
	}

	private boolean validateFraud(boleto boleto) {

		// entradas inválidas -> suspeita de fraude
		if (boleto == null || boleto.getlinhaDigitavel() == null) {
			return true;
		}

		String linha = apenasDigitos(boleto.getlinhaDigitavel());
		if (linha.length() != 47) {
			return true;
		}

		String codigoDoBanco = linha.substring(0, 3);

		Bank bank = bankRepository.findByCodigoDoBanco(codigoDoBanco).orElse(null);
		if (bank == null) {
			return true;
		}

		String nomeCadastrado = normalize(bank.getNomeDoBanco());
		String nomeFornecido = normalize(boleto.getNomeDoBanco());
		if (!nomeCadastrado.equals(nomeFornecido)) {
			return true;
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
            // últimos 14 dígitos
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

        // fator 0000 significa boleto sem vencimento
        if (fator == 0) {
            return null; // sem vencimento
        }

        return DATA_BASE.plusDays(fator);
    }	 

	public static BigDecimal extractValorBoleto(String campo5) {
        campo5 = campo5.replaceAll("\\D", "");

        if (campo5.length() != 14) {
            throw new IllegalArgumentException("Campo 5 deve ter 14 dígitos.");
        }

        String valorStr = campo5.substring(5, 14); // últimos 10 dígitos
        long centavos = Long.parseLong(valorStr);

        return BigDecimal.valueOf(centavos).movePointLeft(2);
    }
}