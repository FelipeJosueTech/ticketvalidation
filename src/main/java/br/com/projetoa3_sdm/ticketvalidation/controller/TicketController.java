package br.com.projetoa3_sdm.ticketvalidation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetoa3_sdm.ticketvalidation.model.entities.Ticket;
import br.com.projetoa3_sdm.ticketvalidation.model.services.TicketService;
import br.com.projetoa3_sdm.ticketvalidation.model.services.dto.TicketRequest;

@RestController
@RequestMapping("/api/boletos")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@PostMapping("/validate")
	public Ticket validate(@RequestBody TicketRequest request) {
		return ticketService.validate(request);
	}
	
}
