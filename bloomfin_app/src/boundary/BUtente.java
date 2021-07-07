package boundary;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import entity.Utente;

public abstract class BUtente {
	
	public abstract Utente doLogin(Scanner in);
	
	public void doLogout(Scanner in) {}
	
	public static Date readDate(Scanner sc) throws ParseException { 
		//return java.sql.Date.valueOf(sc.nextLine()); // pretende formato yyyy-mm-dd
	    java.util.Date data = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
	    //System.out.println("DEBUG, data inserita: " + data);
	    Date sqldata = new Date(data.getTime());
	    return sqldata;
	}
	
	public abstract Utente Registrazione(Scanner in);
	
	public abstract void consultaPacchetto(Scanner in);
	
	
}
