package br.com.projetoa3_sdm.ticketvalidation.model.services.dto;

public class TicketRequest {
	
	private String linhaDigitavel;
	private String nomeInformado;
	private String valor;
	private String beneficiario;
	private String pagador;

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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getPagador() {
		return pagador;
	}

	public void setPagador(String pagador) {
		this.pagador = pagador;
	}
	
	

}
