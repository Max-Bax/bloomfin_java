package entity;

import java.util.ArrayList;

public class Risparmiatore extends Utente {
	// ATTRIBUTI
	private String indirizzoResidenza;
	private String codiceFiscale;
	//private static long idCounter = 0;
	private ArrayList<PacchettoAzionario> pacchettiAcquistati;
	
	// COSTRUTTORE

	public Risparmiatore() {
		this.id = null;
	}
	
	// GETTERS AND SETTERS
	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}
	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public ArrayList<PacchettoAzionario> getPacchettiAcquistati() {
		return pacchettiAcquistati;
	}

	public void setPacchettiAcquistati(ArrayList<PacchettoAzionario> pacchettiAcquistati) {
		this.pacchettiAcquistati = pacchettiAcquistati;
	}

}
