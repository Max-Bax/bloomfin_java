package entity;

import java.util.ArrayList;

public class PacchettoAzionario extends Asset {
	// ATTRIBUTI

	private float fattoreRischio;
	private float stimaRendimento;
	private ArrayList<TitoloAzionario> titoli; // nota: può contenere duplicati
	
	// COSTRUTTORI

	public PacchettoAzionario() {}
	
	// GETTERS AND SETTERS
	public float getFattoreRischio() {
		return fattoreRischio;
	}
	public void setFattoreRischio(float fattoreRischio) {
		this.fattoreRischio = fattoreRischio;
	}
	public float getStimaRendimento() {
		return stimaRendimento;
	}
	public void setStimaRendimento(float stimaRendimento) {
		this.stimaRendimento = stimaRendimento;
	}
	public ArrayList<TitoloAzionario> getTitoli() {
		return titoli;
	}
	public void setTitoli(ArrayList<TitoloAzionario> titoli) {
		this.titoli = titoli;
	}

}
