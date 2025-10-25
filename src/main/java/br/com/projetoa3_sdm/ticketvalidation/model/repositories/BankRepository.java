package br.com.projetoa3_sdm.ticketvalidation.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetoa3_sdm.ticketvalidation.model.entities.Bank;

public interface BankRepository extends JpaRepository<Bank, String> {
	
	    Optional<Bank> findByCodigoDoBanco(String codigoDoBanco);

}
