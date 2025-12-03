package br.com.projetoa3_sdm.ticketvalidation.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetoa3_sdm.ticketvalidation.model.entities.boleto;

public interface BoletoRepository extends JpaRepository<boleto, Long> {

}
