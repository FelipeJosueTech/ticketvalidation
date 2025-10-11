package br.com.projetoa3_sdm.ticketvalidation.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.projetoa3_sdm.ticketvalidation.model.entities.Ticket;

@SpringBootApplication
public class TicketvalidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketvalidationApplication.class, args);
		
		Ticket ticket = new Ticket();
		
	}

}
