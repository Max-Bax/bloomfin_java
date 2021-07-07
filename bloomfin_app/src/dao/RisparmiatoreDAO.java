package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import entity.PacchettoAzionario;
import entity.Risparmiatore;

public class RisparmiatoreDAO {

	
	public static void createRisparmiatore(java.sql.Connection connection, Risparmiatore risparmiatore) throws Exception {
    	if (risparmiatore.getId() != null) {
    		System.out.println("L'oggetto è già nel database.");
    		return;
    	}
		
		PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "INSERT INTO Risparmiatori (CodiceFiscale, Nome, Cognome, DataNascita, Email, Password, IndirizzoResidenza) VALUES (?, ?, ?, ?, ?, ?, ?)" ;
    		preparedStatement = connection.prepareStatement(QUERY_SQL, Statement.RETURN_GENERATED_KEYS);
    		preparedStatement.setString(1, risparmiatore.getCodiceFiscale());
    		preparedStatement.setString(2, risparmiatore.getNome());
    		preparedStatement.setString(3, risparmiatore.getCognome());
    		preparedStatement.setDate(4, (java.sql.Date) risparmiatore.getDataNascita());
    		preparedStatement.setString(5, risparmiatore.getEmail());
    		preparedStatement.setString(6, risparmiatore.getPassword());
    		preparedStatement.setString(7, risparmiatore.getIndirizzoResidenza());
    		
    		preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next() == true) {
            	long ID = resultSet.getLong(1);
                if (resultSet.wasNull() == false) {
                    risparmiatore.setId(ID);
                }
            }
            resultSet.close();
        } catch (JdbcSQLIntegrityConstraintViolationException e) {
        	System.out.println("L'email scelta è già in uso.");
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento nel database.");
            e.printStackTrace();
        } finally {
        	preparedStatement.close();
        }
	}
    
    public static Risparmiatore readRisparmiatore(java.sql.Connection connection, String email, String password) throws Exception {
    	Risparmiatore risparmiatore = null;
    	PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "SELECT * FROM Risparmiatori WHERE Email=? AND Password=?" ;
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1, email);
    		preparedStatement.setString(2, password);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
    		if (!resultSet.isBeforeFirst()) { // il blocco viene eseguito se non ci sono risultati
    			return null;
    		} else
            while (resultSet.next() == true) { // esegue al più 1 sola volta
                risparmiatore = deserializeCurrentRecord(connection, resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
        	e.printStackTrace();
            throw new Exception("Errore durante la lettura da database.", e);
        } finally {
        	preparedStatement.close();
        }
        
        return risparmiatore;
    }
    
    private static Risparmiatore deserializeCurrentRecord(java.sql.Connection connection, ResultSet rs) throws Exception {
    	Risparmiatore risparmiatore = new Risparmiatore();
        risparmiatore.setId(rs.getLong("Id"));
        risparmiatore.setCodiceFiscale(rs.getString("CodiceFiscale"));
        risparmiatore.setNome(rs.getString("Nome"));
        risparmiatore.setCognome(rs.getString("Cognome"));
        risparmiatore.setEmail(rs.getString("Email"));
        risparmiatore.setPassword(rs.getString("Password"));
        risparmiatore.setDataNascita(rs.getDate("DataNascita"));
        risparmiatore.setIndirizzoResidenza(rs.getString("IndirizzoResidenza"));
        
        java.util.ArrayList<PacchettoAzionario> pacchettiAcquistati = readPacchettiAcquistati(connection, risparmiatore.getId());
        risparmiatore.setPacchettiAcquistati(pacchettiAcquistati);
        
        return risparmiatore;
    }
    
    public static java.util.ArrayList<PacchettoAzionario> readPacchettiAcquistati(java.sql.Connection connection, Long risparmiatoreId) throws Exception {
    	java.util.ArrayList<PacchettoAzionario> listaPacchetti = new java.util.ArrayList<PacchettoAzionario>();
    	
    	PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "SELECT Quantita, StimaRendimento, FattoreRischio, Pacchetti.Id as Id FROM Transazioni JOIN Pacchetti ON Transazioni.IdPacchetto = Pacchetti.Id WHERE IdProprietario=?" ;
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setLong(1, risparmiatoreId);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
        	PacchettoAzionario pacchetto = null;
            while (resultSet.next() == true) {
            	int quantita = resultSet.getInt("Quantita");
            	long idPacchetto = resultSet.getInt("Id");
            	pacchetto = PacchettoAzionarioDAO.readPacchettoAzionario(connection, idPacchetto);
            	for (int i = 0; i < quantita; i++) {
                	listaPacchetti.add(pacchetto);
            	}
            }
            resultSet.close();
            return listaPacchetti;
        } catch (SQLException e) {
        	e.printStackTrace();
            throw new Exception("Errore durante la lettura dei pacchetti acquistati.", e);
        } finally {
        	preparedStatement.close();
        }
    }
    
	public static void deleteRisparmiatore(java.sql.Connection connection, String mailRisparmiatore) throws Exception {
		PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "DELETE FROM Risparmiatori WHERE Email = ?";
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1, mailRisparmiatore);

    		preparedStatement.executeUpdate();

           
        } catch (SQLException e) {
        	throw new Exception("Errore durante la cancellazione dal database.");
        } finally {
        	preparedStatement.close();
        }
	}
}
