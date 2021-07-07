package entity;

public class TitoloAzionario extends Asset {
	// ATTRIBUTI
	private float valoreAzione; // valore per singola azione del titolo
	//private static long idCounter = 0; // contatore per generare ID sequenziali degli oggetti, è static, quindi è un attributo di classe e non di oggetto
	
	// COSTRUTTORI
	public TitoloAzionario(float valore) { // non usato, solo come idea
		//super(idCounter++); // incrementa counter, il prossimo oggetto istanziato avrà un ID maggiore di 1 rispetto all'oggetto istanziato ora
		super();
		this.valoreAzione = valore;
	}
	public TitoloAzionario() {
		//super();
	}
	
	// GETTERS AND SETTERS
	public float getValoreAzione() {
		return valoreAzione;
	}
	public void setValoreAzione(float valoreAzione) {
		this.valoreAzione = valoreAzione;
	}

}