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
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@NotEmpty
	private String codigoDeBarras;
	
	private String nomeDoBanco;
	
	private String beneficiario;
	
	private String pagador;
	
	private Double valor;
	private LocalDate dataVencimento;
	private Boolean fraude;
	
	public Ticket() {
		
	}
	
	public Ticket(String codigoDeBarras, String nomeDoBanco) {
		this.codigoDeBarras = codigoDeBarras;
		this.nomeDoBanco = nomeDoBanco;
	}

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

	public boolean isFraude() {
		return fraude;
	}
	public void setFraude(Boolean fraude) {
		this.fraude = fraude;
	}

}
