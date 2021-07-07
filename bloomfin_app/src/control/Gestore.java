package control;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import boundary.Console;
import dao.BrokerDAO;
import dao.DBManager;
import dao.PacchettoAzionarioDAO;
import dao.RisparmiatoreDAO;
import dao.TitoloAzionarioDAO;
import dao.TransazioneDAO;
import entity.Broker;
import entity.PacchettoAzionario;
import entity.TitoloAzionario;
import entity.Transazione;
import entity.Risparmiatore;
import entity.Utente;

public class Gestore {
	
	// IMPLEMENTAZIONE SINGLETON
	private static Gestore instance = null;
	
	public static Gestore getInstance() {
		if (instance == null) {
			instance = new Gestore();
		}
		return instance;
	}
	
	// COSTRUTTORI
	private Gestore() {}
	
	
	// METODI
	public Utente getCurrentUser() {
		Utente utenteCorrente = Console.getCurrentUser();
		return utenteCorrente;
	}
	
	public Broker registrazioneBroker(String nome, String cognome, String email, String password, String ncc, Date data) {
		Broker utente = new Broker();
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setEmail(email);
		utente.setPassword(password);
		utente.setNumeroCameraCommercio(ncc);
		utente.setDataNascita(data);
		
		try {
			Connection conn = DBManager.getConnection();
			BrokerDAO.createBroker(conn, utente);
		} catch (Exception e) {
			System.out.println("Errore di accesso al DB.");
			System.exit(1);
		}
		
		ArrayList<PacchettoAzionario> pacchettiCreati = new ArrayList<PacchettoAzionario>();
		utente.setPacchettiCreati(pacchettiCreati);
		
		return utente;
	}
	
	public Risparmiatore registrazioneRisparmiatore(String nome, String cognome, String email, String password, String cf, Date data, String indirizzo) {
		Risparmiatore utente = new Risparmiatore();
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setEmail(email);
		utente.setPassword(password);
		utente.setCodiceFiscale(cf);
		utente.setDataNascita(data);
		utente.setIndirizzoResidenza(indirizzo);
		
		try { // NOTA: se uso il try with resources gli accessi successivi al db falliscono
			Connection conn = DBManager.getConnection();
			RisparmiatoreDAO.createRisparmiatore(conn, utente);
		} catch (Exception e) {
			System.out.println("Errore di accesso al DB.");
			System.exit(1);
		}
		
		return utente;
	}
	
	public Broker loginBroker(String email, String password) {
		
		Broker utente = null;
		try {
			Connection conn = DBManager.getConnection();
			utente = BrokerDAO.readBroker(conn, email, password);
		} catch (Exception e) {
			System.out.println("Errore di accesso al DB.");
			System.exit(1);
		}
		
		
		return utente;
		}
	
	public Risparmiatore loginRisparmiatore(String email, String password) {
		
		Risparmiatore utente = null;
		try {
			Connection conn = DBManager.getConnection();
			utente = RisparmiatoreDAO.readRisparmiatore(conn, email, password);
		} catch (Exception e) {
			System.out.println("Errore di accesso al DB.");
			System.exit(1);
		}


		
		return utente;
		}

	public float calcolaValorePacchetto(PacchettoAzionario p) {
		float risultato = 0;
		ArrayList<TitoloAzionario> titoliContenuti = p.getTitoli();
		
		for (TitoloAzionario t : titoliContenuti) {
			//System.out.println("Sommo " + t.getValoreAzione());
			risultato = risultato + t.getValoreAzione();
			//System.out.println(risultato);
		}

		
		return risultato;
	}
		
	public Long istanziaPacchetto (ArrayList<TitoloAzionario> listaTitoli, Broker creatore) {
		PacchettoAzionario pacchetto = new PacchettoAzionario();
		pacchetto.setTitoli(listaTitoli);
		pacchetto.setFattoreRischio(ThreadLocalRandom.current().nextInt(0, 100 + 1));
		System.out.println("Debug: impostato Fattore Rischio: " + pacchetto.getFattoreRischio());
		pacchetto.setStimaRendimento(ThreadLocalRandom.current().nextInt(0, 100 + 1));
		System.out.println("Debug: impostata Stima Rendimento: " + pacchetto.getStimaRendimento());
		try {
			Connection conn = DBManager.getConnection();
			PacchettoAzionarioDAO.createPacchettoAzionario(conn, pacchetto, creatore);
		} catch (Exception e) {e.printStackTrace();
			System.out.println("Errore di accesso al DB.");
			System.exit(1);
		}
		//Broker utenteCorrente = (Broker) getCurrentUser();
		Broker utenteCorrente = creatore;
		ArrayList<PacchettoAzionario> pacchettiCreati = utenteCorrente.getPacchettiCreati();
		pacchettiCreati.add(pacchetto);
		utenteCorrente.setPacchettiCreati(pacchettiCreati);
		return pacchetto.getId();
		}
	
	public ArrayList<TitoloAzionario> elencaTitoli () { 
		ArrayList<TitoloAzionario> listaTitoli = null;
		
		try {
			Connection conn = DBManager.getConnection();
			listaTitoli = TitoloAzionarioDAO.readTitoliAzionari(conn);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore di accesso al DB.");
			System.exit(1);
		}
		
		
		return listaTitoli;
	}
	
	public ArrayList<PacchettoAzionario> elencaPacchettiEsistenti () { 
		ArrayList<PacchettoAzionario> listaPacchetti = null;
		try {
			Connection conn = DBManager.getConnection();
			listaPacchetti = PacchettoAzionarioDAO.readPacchettiAzionari(conn);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore di accesso al DB.");
			System.exit(1);
		}

		return listaPacchetti;
	}
	
	public ArrayList<PacchettoAzionario> elencaPacchettiPosseduti () { 
		ArrayList<PacchettoAzionario> listaPacchetti = null;
		Utente utenteCorrente = getCurrentUser();
		try {
			Connection conn = DBManager.getConnection();
			listaPacchetti = RisparmiatoreDAO.readPacchettiAcquistati(conn, utenteCorrente.getId());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore di accesso al DB.");
			System.exit(1);
		}

		return listaPacchetti;
	}
		
	public boolean aggiornaTitoli() {
		System.out.println("Questa funzione non è implementata.");
		return true;
		}
			
	public Long creaTransazione(PacchettoAzionario p, Risparmiatore r, int quantita) {
		Transazione nuovaTransazione = new Transazione();
		nuovaTransazione.setPacchettoAcquistato(p);
		nuovaTransazione.setRisparmiatoreProprietario(r);
		long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);    
		nuovaTransazione.setData(date);
		nuovaTransazione.setQuantita(quantita);
		
		//db
		try {
			Connection conn = DBManager.getConnection();
			TransazioneDAO.createTransazione(conn, nuovaTransazione);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore di accesso al DB.");
			System.exit(1);
		}
		Risparmiatore utenteCorrente = (Risparmiatore) getCurrentUser();
		ArrayList<PacchettoAzionario> pacchettiAcquistati = utenteCorrente.getPacchettiAcquistati();
		pacchettiAcquistati.add(p);
		utenteCorrente.setPacchettiAcquistati(pacchettiAcquistati);
		
		return nuovaTransazione.getId();
	}
	
	public void eliminaTransazioni(Long idPacchetto) {
		try {
			Connection conn = DBManager.getConnection();
			TransazioneDAO.deleteTransazioni(conn, idPacchetto);
		} catch (Exception e) {
			System.out.println("Errore di accesso al DB.");
			System.exit(1);
		}
	}
	
	public ArrayList<PacchettoAzionario> rimuoviDuplicati(ArrayList<PacchettoAzionario> listaConDuplicati) {
		//List<PacchettoAzionario> listaSenzaDuplicati = listaPacchettiPosseduti.stream().distinct().collect(Collectors.toList());
		ArrayList<PacchettoAzionario> listaSenzaDuplicati = new ArrayList<PacchettoAzionario>(); 
		
		for (PacchettoAzionario p : listaConDuplicati) { // rimuovo i duplicati istanziati dalla stessa transazione (stesso riferimento)
			if (!listaSenzaDuplicati.contains(p)) { 
				listaSenzaDuplicati.add(p);
			}
		}
		
		for (int i = 0; i<listaSenzaDuplicati.size(); i++) { // rimuovo i duplicati per id (diverso riferimento perché costruiti separatamente, ma di fatto lo stesso pacchetto)
			for (int j = 0; i<listaSenzaDuplicati.size(); i++) {
				if (i!=j && listaSenzaDuplicati.get(i).equals(listaSenzaDuplicati.get(j))) { // equals è implementato per valere true se gli id dei due pacchetti sono uguali
					listaSenzaDuplicati.remove(j);
				}
			}
		}

		return listaSenzaDuplicati;
	}
	
}
