package br.com.projetoa3_sdm.ticketvalidation.model.services;

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

	private static final LocalDate DATA_BASE = LocalDate.of(1997, 10, 7);

	// Lista fixa de nomes para simular pagadores
	private static final List<String> NOMES_PAGADOR = List.of(
		"Felipe Josué","Amanda Hellen","João José","Anna Maria");
	
	private static final List<String> NOMES_EMPRESAS = List.of(
		"Nextron Solutions","VerdeVale Alimentos","Alphacred Financeira","Luminor Energia");

	// Método para validar o boleto	
	public boleto validate (TicketRequest request) {
		
		// Cria o objeto boleto com os dados do request
		boleto boleto = parseAux(request.getlinhaDigitavel(), request.getNomeDoBanco());
		
		boolean isFraude = validateFraud(boleto);
		boleto.setFraude(isFraude);
		
		return boleto;
	}

	public boleto parse(TicketRequest request) {
		String nomeDoBanco = null; 

		return parseAux(request.getlinhaDigitavel(), nomeDoBanco);
	}

	public boleto parseAux(String linhaDigitavel, String nomeDoBanco) {
		boleto boleto = new boleto (linhaDigitavel, nomeDoBanco);

		//Define informações fixas do boleto
		boleto.setBeneficiario(getRandomNomeEmpresa());
		boleto.setPagador(getRandomNomePagador());

		// Extrai o código do banco a partir do código de barras
		String codigoDeBarras = linhaDigitavelParaCodigoBarras(linhaDigitavel);
		LocalDate vencimento = obterDataVencimento(codigoDeBarras);

		if (vencimento != null) {
			boleto.setDataVencimento(vencimento);
		} else {
			boleto.setDataVencimento(null); // Sem data de vencimento
		}

		// Extrai o valor do boleto a partir do código de barras
		Double valor = extractValorBoleto(codigoDeBarras);
		boleto.setValor(valor);

		// Obtém o nome do banco a partir do código do banco no código de barras
		String codigoDoBanco = boleto.getlinhaDigitavel().substring(0, 3);
		Bank bank = bankRepository.findByCodigoDoBanco(codigoDoBanco).orElse(null);

		if (bank == null) {
			bank = new Bank();
    		bank.setCodigoDoBanco(codigoDoBanco);
    		bank.setNomeDoBanco("Desconhecido");
		}

		boleto.setNomeDoBanco(bank.getNomeDoBanco());

		return boleto;
	}

	private Double extractValorBoleto(String codigoBarras) {
		String valorDoBoletoSTR = null;
		Double valorDoBoleto = null;
		try {
			valorDoBoletoSTR = codigoBarras.substring(9, 19);
			// insere ponto antes dos dois últimos dígitos e converte para double
			if (valorDoBoletoSTR.length() >= 2) {
				String withPoint = valorDoBoletoSTR.substring(0, valorDoBoletoSTR.length() - 2)
					+ "." + valorDoBoletoSTR.substring(valorDoBoletoSTR.length() - 2);
				valorDoBoleto = Double.parseDouble(withPoint);
			} else {
				throw new NumberFormatException("valorDoBoletoSTR inválido: " + valorDoBoletoSTR);
			}
		} catch (Exception e) {
			throw new NumberFormatException("Erro ao extrair valor do boleto: " + e.getMessage());
		}
		return valorDoBoleto;
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
		
		boolean fraude = false;

		if (boleto.getlinhaDigitavel().length() != 47) {
			fraude = true;
		}

		String codigoDoBanco = null;
        try {
            codigoDoBanco = boleto.getlinhaDigitavel().substring(0, 3);
        } catch (NumberFormatException e) {
            fraude = true;
        }

		Bank bank = null;
        if (!fraude) {
            bank = bankRepository.findByCodigoDoBanco(codigoDoBanco).orElse(null);
            // Se não encontrou o banco ou o nome não bate
            if (bank == null || !bank.getNomeDoBanco().equalsIgnoreCase(boleto.getNomeDoBanco())) {
                fraude = true;
            }
        }

		return fraude; // Placeholder
	}

	public List<Bank> getBankCodes() {
		return bankRepository.findAll();
	}

    public static String linhaDigitavelParaCodigoBarras(String linha) {
        linha = linha.replaceAll("\\D", ""); // remove pontos e espaços

        if (linha.length() != 47)
            throw new IllegalArgumentException("Linha digitável deve ter 47 dígitos");

        String campo1 = linha.substring(0, 9);     // sem o DV do campo
        String campo2 = linha.substring(10, 20);   // sem o DV do campo
        String campo3 = linha.substring(21, 31);   // sem o DV do campo
        String dvGeral = linha.substring(32, 33);
        String fatorValor = linha.substring(33, 47);

        return campo1.substring(0, 4)   // Banco + Moeda
                + dvGeral              // DV geral
                + fatorValor           // Fator + Valor
                + campo1.substring(4, 9)
                + campo2
                + campo3;
    }

	public static LocalDate obterDataVencimento(String codigoBarras) {
        if (codigoBarras.length() != 44)
            throw new IllegalArgumentException("Código de barras deve ter 44 dígitos");

        String fatorStr = codigoBarras.substring(5, 9);
        int fator = Integer.parseInt(fatorStr);

        if (fator == 0)
            return null; // sem data de vencimento

        return DATA_BASE.plusDays(fator);
    }

}