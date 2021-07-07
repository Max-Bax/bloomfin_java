package boundary;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import control.Gestore;
import entity.PacchettoAzionario;
import entity.Risparmiatore;
import entity.Utente;

public class BRisparmiatore extends BUtente{
	
	// IMPLEMENTAZIONE SINGLETON
	private static BRisparmiatore instance = null;
	
	static BRisparmiatore getInstance() {
		if (instance == null) {
			instance = new BRisparmiatore();
		}
		return instance;
	}
	
	// COSTRUTTORI
	private BRisparmiatore() {}
	
	
	// METODI
	
	public Utente Registrazione(Scanner in) { 
		System.out.println("Registrazione Risparmiatore");
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
		System.out.println("Inserire il codice fiscale: ");
		String cf = null;
		while (cf == null) {
			try { 
				cf = in.nextLine(); 
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
		System.out.println("Inserire l'indirizzo di residenza: ");
		String indirizzo = null;
		while (indirizzo == null) {
			try { 
				indirizzo = in.nextLine(); 
				} catch (Exception e) {
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
		}
		Gestore gestore = Gestore.getInstance();
		return gestore.registrazioneRisparmiatore(nome, cognome, email, pw, cf, data, indirizzo);
		
		
		
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
		return gestore.loginRisparmiatore(email, pw);
		
		}
	
	public void acquistaPacchetto(Scanner in) {
		Gestore gestore = Gestore.getInstance();
		Risparmiatore risparmiatoreCorrente = (Risparmiatore) gestore.getCurrentUser();
		ArrayList<PacchettoAzionario> listaPacchettiEsistenti = gestore.elencaPacchettiEsistenti();
		if (!(listaPacchettiEsistenti.isEmpty())) { // se esistono pacchetti
			System.out.println("Seleziona il pacchetto da acquistare:");
			Long idTransazione = null;
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
							System.out.println("Hai scelto il pacchetto " + p.getId() + ". Quante unità vuoi acquistare? (Da 1 a 100)");
							trovato = true;
							String stringaQuantita = null;
							Integer quantita = 0;
							while (quantita == 0) {
								try {
									stringaQuantita = in.nextLine();
									quantita = Integer.parseInt(stringaQuantita);
									if (quantita > 0 && quantita <=100) {
										System.out.println("Transazione in corso..."); 
									} else System.out.println("Scegli un valore tra 1 e 100."); 
								} catch (NumberFormatException e) {
									System.out.println("L'input inserito non è un intero."); 
								} catch (Exception e) { 
									e.printStackTrace();
									System.out.println("Errore generico di I/O. Termino l'esecuzione.");
									System.exit(1);
								}
							}
							idTransazione = gestore.creaTransazione(p, risparmiatoreCorrente, quantita);
							
						}
					} if (trovato == false) System.out.println("Il numero inserito non corrisponde a nessun pacchetto!");
				} catch (NumberFormatException e) {
					System.out.println("L'input inserito non è un intero."); 
				} catch (Exception e) { 
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
			}
			
			if (idTransazione != null) System.out.println("L'acquisto è avvenuto con successo.");
			else System.out.println("Qualcosa è andato storto durante l'acquisto.");
			
		} else System.out.println("Al momento non sono presenti pacchetti nel sistema.\n");
	}
	
	public void consultaPacchetto(Scanner in) {
		Gestore gestore = Gestore.getInstance();
		ArrayList<PacchettoAzionario> listaPacchettiPosseduti = gestore.elencaPacchettiPosseduti();
		if (!(listaPacchettiPosseduti.isEmpty())) { // se la lista non è vuota
			System.out.println("Seleziona il pacchetto da consultare tra quelli che possiedi inserendo il suo numero:");
			List<PacchettoAzionario> possedutiSenzaDuplicati = gestore.rimuoviDuplicati(listaPacchettiPosseduti);
			
			for (PacchettoAzionario p : possedutiSenzaDuplicati) { // ciclo nella lista senza duplicati
				System.out.println("Pacchetto" + p.getId());
			}
			String stringaInserita = null;
			boolean trovato = false;
			int quantita = 0;
			while (trovato == false) {
				try {
					stringaInserita = in.nextLine();
					Long idPacchettoScelto = Long.parseLong(stringaInserita);
					for (PacchettoAzionario p : listaPacchettiPosseduti) { // ciclo for nella lista pacchetti, che può contenere duplicati
						if (p.getId() == idPacchettoScelto) { // se trovo almeno 1 istanza di quel pacchetto
							for (PacchettoAzionario scelto : listaPacchettiPosseduti) { // secondo ciclo per calcolare le unità in possesso
								if (scelto.getId() == idPacchettoScelto) {
									quantita++;
								}
							}
							System.out.println("Pacchetto" + p.getId() + "\t\t\tValore: " + gestore.calcolaValorePacchetto(p) + "\nStima di rendimento: " + p.getStimaRendimento() + "\tFattore di rischio: " + p.getFattoreRischio() + "\nUnità in tuo possesso: " + quantita + "\n");
							trovato = true;
							if (trovato == true) break;
						}
					} if (trovato == false) System.out.println("Il numero inserito non corrisponde a nessun pacchetto in tuo possesso!");
				} catch (NumberFormatException e) {
					System.out.println("L'input inserito non è un intero."); 
				} catch (Exception e) { 
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
			}
		} else System.out.println("Al momento non possiedi nessun pacchetto.\n");
	}
	
	public void vendiPacchetto(Scanner in) {
		Gestore gestore = Gestore.getInstance();
		ArrayList<PacchettoAzionario> listaPacchettiPosseduti = gestore.elencaPacchettiPosseduti();
		if (!(listaPacchettiPosseduti.isEmpty())) { // se la lista non è vuota
			System.out.println("Seleziona il pacchetto da vendere tra quelli che possiedi inserendo il suo numero:");
			List<PacchettoAzionario> possedutiSenzaDuplicati = gestore.rimuoviDuplicati(listaPacchettiPosseduti);
			
			for (PacchettoAzionario p : possedutiSenzaDuplicati) { // ciclo nella lista senza duplicati
				System.out.println("Pacchetto" + p.getId());
			}
			String stringaInserita = null;
			boolean trovato = false;
			while (trovato == false) {
				try {
					stringaInserita = in.nextLine();
					Long idPacchettoScelto = Long.parseLong(stringaInserita);
					for (PacchettoAzionario p : listaPacchettiPosseduti) { // ciclo for nella lista pacchetti, che può contenere duplicati
						if (p.getId() == idPacchettoScelto) { // se trovo almeno 1 istanza di quel pacchetto
							gestore.eliminaTransazioni(idPacchettoScelto);
							System.out.println("Hai venduto tutte le unità del pacchetto " + idPacchettoScelto + ".");
							trovato = true;
							if (trovato == true) break;
						}
					} if (trovato == false) System.out.println("Il numero inserito non corrisponde a nessun pacchetto in tuo possesso!");
				} catch (NumberFormatException e) {
					System.out.println("L'input inserito non è un intero."); 
				} catch (Exception e) { 
					System.out.println("Errore generico di I/O. Termino l'esecuzione.");
					System.exit(1);
				}
			}
		} else System.out.println("Al momento non possiedi nessun pacchetto.\n");
	}
	
}
