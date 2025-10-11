package br.com.projetoa3_sdm.ticketvalidation.model.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty
	private String codigoDeBarras;
	
	private double valor;
	private Date dataVencimento;
	private String tipo;
	
	public Ticket() {
		
	}

	public Ticket(String codigoDeBarras, double valor, Date dataVencimento, String tipo) {
		super();
		this.codigoDeBarras = codigoDeBarras;
		this.valor = valor;
		this.dataVencimento = dataVencimento;
		this.tipo = tipo;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String validate(String codigoDeBarras, String nomeDoBanco) {
		
	}

}
