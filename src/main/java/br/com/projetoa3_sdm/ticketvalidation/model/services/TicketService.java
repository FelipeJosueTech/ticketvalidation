package br.com.projetoa3_sdm.ticketvalidation.model.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetoa3_sdm.ticketvalidation.model.entities.Bank;
import br.com.projetoa3_sdm.ticketvalidation.model.entities.Ticket;
import br.com.projetoa3_sdm.ticketvalidation.model.repositories.BankRepository;
import br.com.projetoa3_sdm.ticketvalidation.model.services.dto.TicketRequest;

@Service
public class TicketService {
	
	@Autowired
	private BankRepository bankRepository;
	public Ticket validate(TicketRequest request) {
		
		List<String> list = new ArrayList<>();
		list.add("Felipe Josué");
		list.add("Amanda Hellen");
		list.add("João José");
		list.add("Anna Maria");
		
		Random random = new Random();
		String nomes = list.get(random.nextInt(list.size()));
		
		Ticket ticket = new Ticket(request.getCodigoDeBarras(), request.getNomeDoBanco());
		
		String codigo = request.getCodigoDeBarras();
		String nome = request.getNomeDoBanco();
		
		String valorDoBoletoSTR = null;
		Double valorDoBoleto = null;
		boolean fraude = false;
		try {
            valorDoBoletoSTR = codigo.substring(42, 47);
            // insere ponto antes dos dois últimos dígitos e converte para double
            if (valorDoBoletoSTR.length() >= 2) {
                String withPoint = valorDoBoletoSTR.substring(0, valorDoBoletoSTR.length() - 2)
                    + "." + valorDoBoletoSTR.substring(valorDoBoletoSTR.length() - 2);
                valorDoBoleto = Double.parseDouble(withPoint);
            } else {
                throw new NumberFormatException("valorDoBoletoSTR inválido: " + valorDoBoletoSTR);
            }
        } catch (Exception e) {
            System.out.println("Aconteceu um erro: " + e);
            fraude = true;
        }
		
		
		if (codigo == null || codigo.length() != 47) {
			fraude = true;
		}
		
		// Extrai os 3 primeiros dígitos do código de barras
        String codigoDoBanco = null;
        try {
            codigoDoBanco = codigo.substring(0, 3);
        } catch (NumberFormatException e) {
        	System.out.println("Aconteceu um erro" + e);
            fraude = true;
        }
		
        Bank bank = null;
        if (!fraude) {
            bank = bankRepository.findByCodigoDoBanco(codigoDoBanco).orElse(null);
            // Se não encontrou o banco ou o nome não bate
            if (bank == null || !bank.getNomeDoBanco().equalsIgnoreCase(nome)) {
                fraude = true;
            }
        }
		
		ticket.setFraude(fraude);
		
		ticket.setBeneficiario("Empresa XYZ");
		ticket.setPagador(nomes);
		// usa o valor extraído quando disponível, caso contrário mantém valor padrão
		ticket.setValor(valorDoBoleto);
		ticket.setDataVencimento(java.time.LocalDate.now().plusDays(3));
		
		return ticket;
		
	}

}