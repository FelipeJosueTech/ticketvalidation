package br.com.projetoa3_sdm.ticketvalidation.model.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "tickets")
public class boleto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty
	private String linhaDigitavel;
	
	private String nomeDoBanco;
	
	private String beneficiario;
	
	private String pagador;
	
	private Double valor;

	private LocalDate dataVencimento;

	private Boolean fraude;
	
	public boleto() {
		
	}
	
	public boleto(String linhaDigitavel, String nomeDoBanco) {
		this.linhaDigitavel = linhaDigitavel;
		this.nomeDoBanco = nomeDoBanco;
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public long getId() {
		return id;
	}

	public Boolean isFraude() {
		return fraude;
	}
	public void setFraude(Boolean fraude) {
		this.fraude = fraude;
	}

}
