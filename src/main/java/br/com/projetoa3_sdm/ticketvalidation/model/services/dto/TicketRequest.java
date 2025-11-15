package br.com.projetoa3_sdm.ticketvalidation.model.services.dto;

public class TicketRequest {
	
	private String linhaDigitavel;
	private String nomeDoBanco;

	public TicketRequest(String linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}
	
	public String getlinhaDigitavel() {
		return linhaDigitavel;
	}
	public void setlinhaDigitavel(String linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}
	public String getNomeDoBanco() {
		return nomeDoBanco;
	}
	public void setNomeDoBanco(String nomeDoBanco) {
		this.nomeDoBanco = nomeDoBanco;
	}
	
	

}
