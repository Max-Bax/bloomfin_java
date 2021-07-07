package boundary;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.commons.text.WordUtils;

import entity.Broker;
import entity.Utente;

public class Console { // è usato come punto di accesso all'applicazione, contiene il main

	public enum Ruoli {
		RISPARMIATORE,
		BROKER,
	}
	
	public enum Autenticazioni {
		REGISTRAZIONE,
		LOGIN,
	}
	
	public enum Azioni {
		CREA_PACCHETTO,
		CONSULTA_TITOLI,
		CONSULTA_PACCHETTO,
		COMPRA_PACCHETTO,
		VENDI_PACCHETTO,
		LOGOUT,
	}

	
	private static Ruoli ruolo;
	private static Autenticazioni autenticazione;
	private static Utente currentUser;
	private static Azioni azione;
	
	// FUNZIONI
	public static Utente getCurrentUser() {
		return currentUser;
	}
	
	private static Ruoli setRole(Scanner in) {
		System.out.println("Sei un risparmiatore o un broker? [r/b]");
		String r = null;
		while (r == null || (!r.equals("r") && !r.equals("b"))) {
			try { 
				r = in.nextLine(); 
				} catch (Exception e) {
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
		}
		Ruoli ruoloscelto = null;
		if (r.equals("r")) {
			ruoloscelto = Ruoli.RISPARMIATORE;
		} else if (r.equals("b")) {
			ruoloscelto = Ruoli.BROKER;
		}
		System.out.println("Il tuo ruolo è: " + ruoloscelto);
		return ruoloscelto;
	}
	
	private static Autenticazioni setAuth(Scanner in) {
		System.out.println("Come vuoi autenticarti?");
		System.out.println("1. Login");
		System.out.println("2. Registrazione");
		while (true) {
			try {
				int scelta = in.nextInt();
				in.nextLine(); // pulisco il buffer
				switch (scelta) {
					case 1 : return Autenticazioni.LOGIN;
					case 2 : return Autenticazioni.REGISTRAZIONE;
					default: System.out.println("Il numero inserito non è associato a nessuna azione.");
				}
			} catch (InputMismatchException e) {
				System.out.println("L'input inserito non è un intero.");
				in.nextLine(); // prende il testo che ha generato l'errore, pulendo il buffer
			} catch (Exception e) { // avviene se uso ctrl+z durante l'inserimento
				System.out.println("Errore generico di I/O. Termino l'esecuzione.");
				System.exit(1);
			}
			
		}
	}
	
	private static Azioni setAction(Scanner in) {
		System.out.println("Cosa vuoi fare?");
		Azioni azioneScelta = null;
		if (ruolo == Ruoli.RISPARMIATORE) {
			System.out.println("1. Compra Pacchetto");
			System.out.println("2. Consulta Pacchetto");
			System.out.println("3. Vendi Pacchetto");
			System.out.println("4. Logout");
			while (azioneScelta == null) {
				try {
					int scelta = in.nextInt();
					in.nextLine(); // pulisco buffer
					switch (scelta) {
						case 1: {azioneScelta = Azioni.COMPRA_PACCHETTO; break;}
						case 2: {azioneScelta = Azioni.CONSULTA_PACCHETTO; break;}
						case 3: {azioneScelta = Azioni.VENDI_PACCHETTO; break;}
						case 4: {azioneScelta = Azioni.LOGOUT; break;}
						default: System.out.println("Il numero inserito non è associato a nessuna azione.");
					}
				} catch (InputMismatchException e) {
					System.out.println("L'input inserito non è un intero.");
					in.nextLine(); // prende il testo che ha generato l'errore, pulendo il buffer
				} catch (Exception e) { // avviene se uso ctrl+z durante l'inserimento
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
			}
		} else if (ruolo == Ruoli.BROKER) {
			System.out.println("1. Crea Pacchetto");
			System.out.println("2. Consulta Pacchetto");
			System.out.println("3. Consulta Titoli");
			System.out.println("4. Logout");
			while (azioneScelta == null) {
				try {
					int scelta = in.nextInt();
					in.nextLine(); // pulisco buffer
					switch (scelta) {
						case 1: {azioneScelta = Azioni.CREA_PACCHETTO; break;}
						case 2: {azioneScelta = Azioni.CONSULTA_PACCHETTO; break;}
						case 3: {azioneScelta = Azioni.CONSULTA_TITOLI; break;}
						case 4: {azioneScelta = Azioni.LOGOUT; break;}
						default: System.out.println("Il numero inserito non è associato a nessuna azione.");
					}
				} catch (InputMismatchException e) {
					System.out.println("L'input inserito non è un intero.");
					in.nextLine(); // prende il testo che ha generato l'errore, pulendo il buffer
				} catch (Exception e) { // avviene se uso ctrl+z durante l'inserimento
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
			}
		}
		return azioneScelta;
		
	}
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in); 
		
		System.out.println("Benvenuto in Bloomfin.");
		Console.ruolo = setRole(in);
		Console.autenticazione = setAuth(in);
		
		BUtente boundary = null;
		if (ruolo == Ruoli.RISPARMIATORE) {
			boundary = BRisparmiatore.getInstance();
		} else if (ruolo == Ruoli.BROKER) {
			boundary = BBroker.getInstance();
		}
		if (autenticazione == Autenticazioni.REGISTRAZIONE) {
			currentUser = boundary.Registrazione(in); // polimorfismo
		} else if (autenticazione == Autenticazioni.LOGIN) {
			do {
				currentUser = boundary.doLogin(in); 
				if (currentUser == null) System.out.println("Combinazione di nome utente e password non trovata. Riprova.");
			} while (currentUser == null);

		}
			
		System.out.println("Benvenuto, " + currentUser.getNome() + " " + currentUser.getCognome() + "!");
		System.out.println("ID: " + currentUser.getId() + ", data nascita: " + currentUser.getDataNascita());
		
		if (ruolo == Ruoli.BROKER) {
			Integer numeroPacchettiCreati = ((Broker) currentUser).getPacchettiCreati().size(); 
			System.out.println("Al momento hai creato " + numeroPacchettiCreati + " pacchetti.");
		}

		while (azione != Azioni.LOGOUT) {
			azione = setAction(in);
			String stringaAzione = WordUtils.capitalizeFully(azione.toString(), '_').replace('_', ' '); // trasforma 'STRINGA_DI_ESEMPIO' in 'Stringa Di Esempio'
			System.out.println("L'azione che hai scelto è: " + stringaAzione + "\n");
			
			if (azione == Azioni.CREA_PACCHETTO) {
				((BBroker) boundary).creaPacchetto(in); 
			} else if (azione == Azioni.COMPRA_PACCHETTO) {
				((BRisparmiatore) boundary).acquistaPacchetto(in);
			} else if (azione == Azioni.VENDI_PACCHETTO) {
				((BRisparmiatore) boundary).vendiPacchetto(in);
			} else if (azione == Azioni.CONSULTA_PACCHETTO) {
				boundary.consultaPacchetto(in);
			} else if (azione == Azioni.CONSULTA_TITOLI) {
				((BBroker) boundary).consultaTitoli(in);
			} else if (azione == Azioni.LOGOUT) {
				currentUser = null;
				// NOTA: se gli utenti potessero modificare valori sarebbe necessario salvare le modifiche nel DB prima di uscire
			}
		}
	
		System.out.println("Arrivederci da Bloomfin.");
		
		in.close(); // ATTENZIONE: chiude anche System.in, non posso più inserire input 
	}
}
