package test;
import entity.PacchettoAzionario;
import entity.Broker;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entity.Risparmiatore;
import entity.TitoloAzionario;
class TestCostruttori {

	private static final String NULL = null;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testBroker() {
		Broker broker= new Broker();
		String expected=NULL;
		assertEquals(expected,broker.getId());
	
	}
	@Test
	public void testRisparmiatore() {
		Risparmiatore risparmiatore= new Risparmiatore();
		String expected=NULL;
		assertEquals(expected,risparmiatore.getId());
	
	}
	@Test
	public void testPacchettoAzionario() {
		PacchettoAzionario pacchetto= new PacchettoAzionario();
		String expected=NULL;
		assertEquals(expected,pacchetto.getId());
	
	}
	@Test
	void testEquals() {
		TitoloAzionario t=new TitoloAzionario(10);
		int valoreAtteso = 10;
		
		float a=t.getValoreAzione();
		assertEquals(a,valoreAtteso);
		
	}

}
