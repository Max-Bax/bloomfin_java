package entity;

import java.sql.Time;
import java.util.ArrayList;

public class Borsa {
	// ATTRIBUTI
	private String nome;
	private Time orarioApertura;
	private Time orarioChiusura;
	private String statoAppartenenza;
	private ArrayList<TitoloAzionario> listaTitoli; // array di titoli, vengono creati da questa classe
	
	// COSTRUTTORI
	public Borsa() {}
	
	// GETTERS AND SETTERS
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Time getOrarioApertura() {
		return orarioApertura;
	}
	public void setOrarioApertura(Time orarioApertura) {
		this.orarioApertura = orarioApertura;
	}
	public Time getOrarioChiusura() {
		return orarioChiusura;
	}
	public void setOrarioChiusura(Time orarioChiusura) {
		this.orarioChiusura = orarioChiusura;
	}
	public String getStatoAppartenenza() {
		return statoAppartenenza;
	}
	public void setStatoAppartenenza(String statoAppartenenza) {
		this.statoAppartenenza = statoAppartenenza;
	}
	public ArrayList<TitoloAzionario> getListaTitoli() {
		return listaTitoli;
	}
	public void setListaTitoli(ArrayList<TitoloAzionario> listaTitoli) {
		this.listaTitoli = listaTitoli;
	}

	};

