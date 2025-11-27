package br.com.projetoa3_sdm.ticketvalidation.model.services.dto;

public class TicketRequest {
	
	private String linhaDigitavel;
	private String nomeInformado;

	public TicketRequest(String linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}
	
	public String getlinhaDigitavel() {
		return linhaDigitavel;
	}
	public void setlinhaDigitavel(String linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}
	public String getnomeInformado() {
		return nomeInformado;
	}
	public void setnomeInformado(String nomeInformado) {
		this.nomeInformado = nomeInformado;
	}
	
	

}
