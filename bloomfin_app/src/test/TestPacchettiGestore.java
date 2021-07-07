package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import control.Gestore;
import dao.DBManager;
import dao.PacchettoAzionarioDAO;
import entity.Broker;
import entity.PacchettoAzionario;
import entity.TitoloAzionario;


// ESEGUIRE IL SEGUENTE CODICE IN H2 PRIMA DEL TEST
// INSERT INTO TITOLI (Id, ValoreAzione) VALUES (38, 5)
public class TestPacchettiGestore {
	
	
	private static Gestore instance;
	
	private static Broker brokerCorrente; 
	
	private static ArrayList<TitoloAzionario> listaTitoli = new ArrayList<TitoloAzionario>();
	
	private static Date data=new Date();
	
	private PacchettoAzionario createPacchetto() {
		PacchettoAzionario pacchetto = new PacchettoAzionario();
		pacchetto.setTitoli(listaTitoli);
		return pacchetto;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		instance = Gestore.getInstance();
		TitoloAzionario titolo= new TitoloAzionario();
		brokerCorrente = instance.registrazioneBroker("NomeB", "CognomeB", "mailB", "passB", "1", new java.sql.Date(data.getTime())); 
		brokerCorrente = instance.loginBroker("mailB", "passB");
		titolo.setValoreAzione(5);
		titolo.setId(38);
		listaTitoli.add(titolo);
		
		System.out.println(brokerCorrente.getId());
		
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}


	@Test
	public void testIstanziaPacchetto() throws Exception {
		PacchettoAzionario pacchettoAtteso = createPacchetto();
		ArrayList<TitoloAzionario> listaTitoliAttesa = pacchettoAtteso.getTitoli();
		TitoloAzionario titoloAzionarioAtteso = listaTitoliAttesa.get(0);
		Long idPacchetto = instance.istanziaPacchetto(listaTitoli, brokerCorrente);
		PacchettoAzionario pacchettoCreato = PacchettoAzionarioDAO.readPacchettoAzionario(DBManager.getConnection(), idPacchetto);
		ArrayList<TitoloAzionario> listaTitoliEffettiva = pacchettoCreato.getTitoli();
		TitoloAzionario titoloAzionarioEffettivo = listaTitoliEffettiva.get(0);
		assertEquals(titoloAzionarioEffettivo.getId(), titoloAzionarioAtteso.getId());
		
		
		
	}

}
