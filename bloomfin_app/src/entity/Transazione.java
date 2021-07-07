package entity;

import java.sql.Date;

public class Transazione {
	// ATTRIBUTI 
	private long id;
	private Date data;
	private int quantita;
	PacchettoAzionario pacchettoAcquistato;
	Risparmiatore risparmiatoreProprietario;
	
	// COSTRUTTORI

	
	public Transazione() {
	}

	// GETTERS AND SETTERS
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	public PacchettoAzionario getPacchettoAcquistato() {
		return pacchettoAcquistato;
	}
	public void setPacchettoAcquistato(PacchettoAzionario pacchettoAcquistato) {
		this.pacchettoAcquistato = pacchettoAcquistato;
	}
	public Risparmiatore getRisparmiatoreProprietario() {
		return risparmiatoreProprietario;
	}
	public void setRisparmiatoreProprietario(Risparmiatore risparmiatoreProprietario) {
		this.risparmiatoreProprietario = risparmiatoreProprietario;
	}

	
	
}
