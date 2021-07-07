package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import control.Gestore;
import dao.BrokerDAO;
import dao.DBManager;
import dao.RisparmiatoreDAO;
import entity.Broker;
import entity.Risparmiatore;
import entity.Utente;

class TestAutenticazioneGestore {
	
	private static	Gestore instance;
	
	private static Date data=new Date();

	
	
	private Broker createBroker() {
		Broker broker = new Broker();
		broker.setNome("Paolo");
		broker.setCognome("Brosio");
		broker.setEmail("email@broker.it");
		broker.setPassword("a");
		broker.setNumeroCameraCommercio("1");
		broker.setDataNascita(new java.sql.Date(data.getTime()));
		return broker;
    }
	
	private Risparmiatore createRisparmiatore() {
		Risparmiatore risparmiatore = new Risparmiatore();
		risparmiatore.setNome("Pier");
		risparmiatore.setCognome("Bergoglio");
		risparmiatore.setEmail("email@risparmiatore.it");
		risparmiatore.setPassword("a");
		risparmiatore.setIndirizzoResidenza("Via colombo");
		risparmiatore.setDataNascita(new java.sql.Date(data.getTime()));
		risparmiatore.setCodiceFiscale("cf");
		return risparmiatore;
    }
	
	@BeforeAll
	static void setUpBeforeClass() {
		instance = Gestore.getInstance();
	}

    @AfterAll
    public static void tearDownClass() throws SQLException {
    	Connection connessione=DBManager.getConnection();
    	try {
			BrokerDAO.deleteBroker(connessione, "email@broker.it");
			RisparmiatoreDAO.deleteRisparmiatore(connessione, "email@risparmiatore.it");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}finally {
			DBManager.closeConnection();
		}
    }

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testRegistrazioneBroker() {
		Broker brokerAtteso= createBroker();
        assertNotNull(brokerAtteso); // Post
        Broker broker=instance.registrazioneBroker("Paolo","Brosio","email@broker.it","a","1",new java.sql.Date(data.getTime()));
        assertEquals(broker.getNome(), brokerAtteso.getNome());
        assertEquals(broker.getCognome(), brokerAtteso.getCognome());
        assertEquals(broker.getEmail(), brokerAtteso.getEmail());
        assertEquals(broker.getPassword(), brokerAtteso.getPassword());
        assertEquals(broker.getNumeroCameraCommercio(), brokerAtteso.getNumeroCameraCommercio());
        assertEquals(broker.getDataNascita(), brokerAtteso.getDataNascita());
	
	}

	

	@Test
	public void testLoginBroker() {
		Utente broker=instance.loginBroker("email@broker.it", "a");
		assertNotNull(broker);
		assertEquals(broker.getCognome(), "Brosio");
	}
	
	@Test
	public void testRegistrazioneRisparmiatore() {
		Risparmiatore risparmiatoreAtteso= createRisparmiatore();
        assertNotNull(risparmiatoreAtteso); // Post
        Risparmiatore risparmiatore=instance.registrazioneRisparmiatore("Pier","Bergoglio","email@risparmiatore.it","a","cf",new java.sql.Date(data.getTime()),"Via colombo");
        assertEquals(risparmiatore.getNome(), risparmiatoreAtteso.getNome());
        assertEquals(risparmiatore.getCognome(), risparmiatoreAtteso.getCognome());
        assertEquals(risparmiatore.getEmail(), risparmiatoreAtteso.getEmail());
        assertEquals(risparmiatore.getPassword(), risparmiatoreAtteso.getPassword());
        assertEquals(risparmiatore.getCodiceFiscale(), risparmiatoreAtteso.getCodiceFiscale());
        assertEquals(risparmiatore.getDataNascita(), risparmiatoreAtteso.getDataNascita());
        assertEquals(risparmiatore.getIndirizzoResidenza(), risparmiatoreAtteso.getIndirizzoResidenza());
	
	}
	
	@Test
	public void testLoginRisparmiatore() {
	Utente risparmiatore=instance.loginRisparmiatore("email@risparmiatore.it", "a");
	assertNotNull(risparmiatore);
	assertEquals(risparmiatore.getCognome(), "Bergoglio");
	}

}
