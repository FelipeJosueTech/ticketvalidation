package br.com.projetoa3_sdm.ticketvalidation.model.services.dto;

public class TicketRequest {
	
	private String codigoDeBarras;
	private String nomeDoBanco;
	
	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}
	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}
	public String getNomeDoBanco() {
		return nomeDoBanco;
	}
	public void setNomeDoBanco(String nomeDoBanco) {
		this.nomeDoBanco = nomeDoBanco;
	}
	
	

}
