package boundary;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import control.Gestore;
import entity.Utente;
import entity.Broker;
import entity.PacchettoAzionario;
import entity.TitoloAzionario;

public class BBroker extends BUtente{
	
	// IMPLEMENTAZIONE SINGLETON
	private static BBroker instance = null;
	
	static BBroker getInstance() {
		if (instance == null) {
			instance = new BBroker();
		}
		return instance;
	}
	
	// COSTRUTTORI
	private BBroker() {}
	
	
	// METODI
	

	
	public Utente Registrazione(Scanner in) { 
		System.out.println("Registrazione Broker");
		System.out.println("Inserire nome: ");
		String nome = null;
		while (nome == null) {
			try { 
				nome = in.nextLine(); 
				} catch (Exception e) {
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
		}
		System.out.println("Inserire cognome: ");
		String cognome = null;
		while (cognome == null) {
			try { 
				cognome = in.nextLine(); 
				} catch (Exception e) {
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
		}
		System.out.println("Inserire email: ");
		String email = null;
		while (email == null) {
			try { 
				email = in.nextLine(); 
				} catch (Exception e) {
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
		}
		System.out.println("Scegliere una password: ");
		String pw = null;
		while (pw == null) {
			try { 
				pw = in.nextLine(); 
				} catch (Exception e) {
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
		}
		System.out.println("Inserire il numero di camera di commercio: ");
		String ncc = null;
		while (ncc == null) {
			try { 
				ncc = in.nextLine(); 
				} catch (Exception e) {
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
		}
		System.out.println("Inserire data di nascita [formato dd/mm/yyyy]:");
		Date data = null;
		while (data == null) {
			try {
				data = readDate(in);
			} catch (ParseException e) {
				System.out.println("Formato di data non riconosciuto.");
			} catch (Exception e) {
				System.out.println("Errore generico di I/O. Termino l'esecuzione.");
				System.exit(1);
			}
		}
		
		Gestore gestore = Gestore.getInstance();
		return gestore.registrazioneBroker(nome, cognome, email, pw, ncc, data);
		
		
		
	}
	
	public Utente doLogin(Scanner in) { 
		System.out.println("Login...");
		System.out.println("Inserisci email: ");
		String email = null;
		while (email == null) {
			try { 
				email = in.nextLine(); 
				} catch (Exception e) {
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
		}
		System.out.println("Inserisci la tua password: ");
		String pw = null;
		while (pw == null) {
			try { 
				pw = in.nextLine(); 
				} catch (Exception e) {
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
		}
		
		Gestore gestore = Gestore.getInstance();
		return gestore.loginBroker(email, pw);
		
		}
	
	public void creaPacchetto(Scanner in) {
		System.out.println("Procedura di creazione pacchetto avviata.");
		Gestore gestore = Gestore.getInstance();
		ArrayList<TitoloAzionario> listaTitoliScelti = new ArrayList<TitoloAzionario>();
		ArrayList<TitoloAzionario> listaTitoliEsistenti = gestore.elencaTitoli(); // BUG
		System.out.println("Inserisci i numeri di tutti i titoli che vuoi inserire separati da un invio.\nScrivi 'ok' quando hai terminato.");
		
		for (TitoloAzionario t : listaTitoliEsistenti) {
			System.out.println("Titolo" + t.getId() + "\t\tValore per Azione: " + t.getValoreAzione());
		}
		boolean confirm = false;
		String stringaInserita = null;
		while (!confirm) {
			try {
				stringaInserita = in.nextLine();
				Long idTitoloScelto = Long.parseLong(stringaInserita);
				boolean added = false;
				for (TitoloAzionario t : listaTitoliEsistenti) {
					if (t.getId() == idTitoloScelto) {
						listaTitoliScelti.add(t);
						added = true;
					}
				} if (added == false) System.out.println("Il numero inserito non corrisponde a nessun titolo!");
			} catch (NumberFormatException e) {
				if (stringaInserita.equals("ok")) {
					confirm = true;
					System.out.println("Fine inserimento.");
				} else System.out.println("L'input inserito non è un intero."); 
			} catch (Exception e) { 
				System.out.println("Errore generico di I/O. Termino l'esecuzione.");
				System.exit(1);
			}
		}
		System.out.println("Lista titoli scelti: ");
		for (TitoloAzionario t : listaTitoliScelti) {
			System.out.println("Titolo" + t.getId() + "\t\tValore per Azione: " + t.getValoreAzione());
		}
		
		Long idPacchetto = gestore.istanziaPacchetto(listaTitoliScelti, (Broker) Console.getCurrentUser());
		if (idPacchetto != null) {
			System.out.println("Il pacchetto è stato creato con successo.");
			} else System.out.println("Qualcosa è andato storto durante la creazione del pacchetto.");
		
	}
	
	public void consultaTitoli(Scanner in) {
		System.out.println("Procedura di consultazione titoli avviata.");
		Gestore gestore = Gestore.getInstance();
		ArrayList<TitoloAzionario> listaTitoliEsistenti = gestore.elencaTitoli();
		if (!(listaTitoliEsistenti.isEmpty())) {
			for (TitoloAzionario t : listaTitoliEsistenti) {
				System.out.println("Titolo" + t.getId() + "\t\tValore per Azione: " + t.getValoreAzione());
			}
		} else System.out.println("Al momento non sono presenti titoli nel sistema.");
		System.out.println();
	}
	
	public void consultaPacchetto(Scanner in) {
		Gestore gestore = Gestore.getInstance();
		ArrayList<PacchettoAzionario> listaPacchettiEsistenti = gestore.elencaPacchettiEsistenti();
		if (!(listaPacchettiEsistenti.isEmpty())) { // se esistono pacchetti
			System.out.println("Seleziona il pacchetto da consultare inserendo il suo numero:");
			for (PacchettoAzionario p : listaPacchettiEsistenti) {
				System.out.println("Pacchetto" + p.getId());
			}
			String stringaInserita = null;
			boolean trovato = false;
			while (trovato == false) {
				try {
					stringaInserita = in.nextLine();
					Long idPacchettoScelto = Long.parseLong(stringaInserita);
					for (PacchettoAzionario p : listaPacchettiEsistenti) {
						if (p.getId() == idPacchettoScelto) {
							System.out.println("Pacchetto" + p.getId() + "\t\t\tValore: " + gestore.calcolaValorePacchetto(p) + "\nStima di rendimento: " + p.getStimaRendimento() + "\tFattore di rischio: " + p.getFattoreRischio() + "\n" );
							trovato = true;
						}
					} if (trovato == false) System.out.println("Il numero inserito non corrisponde a nessun pacchetto!");
				} catch (NumberFormatException e) {
					System.out.println("L'input inserito non è un intero."); 
				} catch (Exception e) { 
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
			}
		} else System.out.println("Al momento non sono presenti pacchetti nel sistema.\n");
	}
	

	

}
