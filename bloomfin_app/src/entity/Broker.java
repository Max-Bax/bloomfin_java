package entity;

import java.util.ArrayList;

public class Broker extends Utente  {
	// ATTRIBUTI
	private String numeroCameraCommercio;
	private ArrayList<PacchettoAzionario> pacchettiCreati;
	//private static long idCounter = 0;
	
	// COSTRUTTORI
	public Broker() {
		this.id = null;
	}
	
	// GETTERS AND SETTERS
	public String getNumeroCameraCommercio() {
		return numeroCameraCommercio;
	}
	public void setNumeroCameraCommercio(String numeroCameraCommercio) {
		this.numeroCameraCommercio = numeroCameraCommercio;
	}

	public ArrayList<PacchettoAzionario> getPacchettiCreati() {
		return pacchettiCreati;
	}

	public void setPacchettiCreati(ArrayList<PacchettoAzionario> pacchettiCreati) {
		this.pacchettiCreati = pacchettiCreati;
	}
	

}