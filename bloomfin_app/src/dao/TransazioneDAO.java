package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import control.Gestore;
import entity.Risparmiatore;
import entity.Transazione;

public class TransazioneDAO {

	
	public static void createTransazione(java.sql.Connection connection, Transazione t) throws Exception {
    	
		/*
		 * 	    CREATE TABLE Transazioni (
	        Id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
	        Quantita INTEGER,
	        Data DATE,
	        IdProprietario LONG,
	        IdPacchetto LONG,
	        FOREIGN KEY (IdProprietario) REFERENCES Risparmiatori (Id),
	        FOREIGN KEY (IdPacchetto) REFERENCES Pacchetti (Id));
		*/
		//Gestore gestore = Gestore.getInstance();
		//Risparmiatore acquirente = (Risparmiatore) gestore.getCurrentUser();
		Risparmiatore acquirente = t.getRisparmiatoreProprietario();
		PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "INSERT INTO Transazioni (IdProprietario, IdPacchetto, Quantita, Data) VALUES (?, ?, ?, ?)";
    		preparedStatement = connection.prepareStatement(QUERY_SQL, Statement.RETURN_GENERATED_KEYS);
    		preparedStatement.setLong(1, acquirente.getId());
    		preparedStatement.setLong(2, t.getPacchettoAcquistato().getId());
    		preparedStatement.setInt(3, t.getQuantita());
    		preparedStatement.setDate(4, t.getData());

    		
    		preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next() == true) {
            	long ID = resultSet.getLong(1);
                if (resultSet.wasNull() == false) {
                	t.setId(ID);
                	System.out.println("Debug: ID: " + t.getId());
                }
            }
            resultSet.close();

           
        } catch (SQLException e) {
        	throw new Exception("Errore durante l'inserimento nel database.");
        } finally {
        	preparedStatement.close();
        }
    	
	}
	
	public static void deleteTransazioni(java.sql.Connection connection, Long idPacchetto) throws Exception {
		Gestore gestore = Gestore.getInstance();
		Risparmiatore venditore = (Risparmiatore) gestore.getCurrentUser();
		PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "DELETE FROM Transazioni WHERE IdProprietario = ? AND IdPacchetto = ?";
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setLong(1, venditore.getId());
    		preparedStatement.setLong(2, idPacchetto);

    		preparedStatement.executeUpdate();

           
        } catch (SQLException e) {
        	throw new Exception("Errore durante la cancellazione dal database.");
        } finally {
        	preparedStatement.close();
        }
	}
}
