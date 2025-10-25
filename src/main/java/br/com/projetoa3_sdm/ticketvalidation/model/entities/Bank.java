package br.com.projetoa3_sdm.ticketvalidation.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Bank {
	
	@Id
	@Column(name = "codigo_do_banco", length = 3)
	private String codigoDoBanco;
	
	@NotEmpty
	@Column(name = "nome_do_banco")
	private String nomeDoBanco;
	
	public Bank() {
	}

	public Bank(String codigoDoBanco, String nomeDoBanco) {
		this.codigoDoBanco = codigoDoBanco;
		this.nomeDoBanco = nomeDoBanco;
	}

	public String getCodigoDoBanco() {
		return codigoDoBanco;
	}

	public void setCodigoDoBanco(String codigoDoBanco) {
		this.codigoDoBanco = codigoDoBanco;
	}

	public String getNomeDoBanco() {
		return nomeDoBanco;
	}

	public void setNomeDoBanco(String nomeDoBanco) {
		this.nomeDoBanco = nomeDoBanco;
	}

}
