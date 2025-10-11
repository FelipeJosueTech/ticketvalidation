package br.com.projetoa3_sdm.ticketvalidation.model.services;

import org.springframework.stereotype.Service;

import br.com.projetoa3_sdm.ticketvalidation.model.entities.Ticket;
import br.com.projetoa3_sdm.ticketvalidation.model.services.dto.TicketRequest;

@Service
public class TicketService {
	
	public Ticket validate(TicketRequest request) {
		
		Ticket ticket = new Ticket(request.getCodigoDeBarras(), request.getNomeDoBanco());
		
		boolean fraude = false;
		String codigo = request.getCodigoDeBarras();
		
		if (codigo == null || codigo.length() != 47) {
			fraude = true;
		}
		
		if (codigo != null && codigo.startsWith("001") && !request.getNomeDoBanco().equalsIgnoreCase(codigo)) {
			fraude = true;
		}
		
		ticket.setFraude(fraude);
		
		ticket.setBeneficiario("Empresa XYZ");
		ticket.setPagador("Felipe Josué");
		ticket.setValor(150.75);
		ticket.setDataVencimento(java.time.LocalDate.now().plusDays(3));
		
		return ticket;
		
	}

}