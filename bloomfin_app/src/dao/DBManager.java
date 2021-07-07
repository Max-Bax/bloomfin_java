package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	protected final static String DB_PATH = "~/test";
	protected final static String DB_MODE = ";mode=MySQL"; // settata per utilizzare 'ON DUPLICATE KEY UPDATE'
	protected final static String DB_URL = "jdbc:h2:" + DB_PATH + DB_MODE;
	protected final static String DB_USER = "sa";
	protected final static String DB_PASSWORD = ""; // nessuna password
	
	protected static Connection conn;
	
	public static Connection getConnection(){
		if (conn == null){
			try {
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
			} catch (org.h2.jdbc.JdbcSQLNonTransientConnectionException e) {
				System.out.println("Il database è probabilmente aperto in un'altra sessione.\nTerminala e riprova l'esecuzione.");
				System.exit(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	
	public static void closeConnection() throws SQLException{
		if (conn != null){
			conn.close();
			conn = null;
		}
	}

}
