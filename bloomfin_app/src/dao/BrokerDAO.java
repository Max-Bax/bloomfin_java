package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import entity.Broker;
import entity.PacchettoAzionario;


public class BrokerDAO {

	
	
	
	public static void createBroker(java.sql.Connection connection, Broker broker) throws Exception {
    	if (broker.getId() != null) {
    		System.out.println("L'oggetto è già nel database.");
    		return;
    	}
		
		PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "INSERT INTO Broker (NumeroCameraCommercio, Nome, Cognome, DataNascita, Email, Password) VALUES (?, ?, ?, ?, ?, ?)" ;
    		preparedStatement = connection.prepareStatement(QUERY_SQL, Statement.RETURN_GENERATED_KEYS);
    		preparedStatement.setString(1, broker.getNumeroCameraCommercio());
    		preparedStatement.setString(2, broker.getNome());
    		preparedStatement.setString(3, broker.getCognome());
    		preparedStatement.setDate(4, broker.getDataNascita());
    		preparedStatement.setString(5, broker.getEmail());
    		preparedStatement.setString(6, broker.getPassword());
    		
    		preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next() == true) {
            	long ID = resultSet.getLong(1); // errore: Invalid value "1" for parameter "columnIndex"
                if (resultSet.wasNull() == false) {
                    broker.setId(ID);
                    System.out.println("Trovato ID: " + ID);
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
	
    
    public static Broker readBroker(java.sql.Connection connection, String email, String password) throws Exception {
    	Broker broker = null;
    	PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "SELECT * FROM Broker WHERE Email=? AND Password=?" ;
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1, email);
    		preparedStatement.setString(2, password);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
    		if (!resultSet.isBeforeFirst()) { // il blocco viene eseguito se non ci sono risultati
    			return null;
    		} else
            while (resultSet.next() == true) { // esegue al più 1 sola volta
            	broker = deserializeCurrentRecord(connection, resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new Exception("Errore durante la lettura da database.", e);
        } finally {
        	preparedStatement.close();
        }
        
        return broker;
    }
    
    private static Broker deserializeCurrentRecord(java.sql.Connection connection, ResultSet rs) throws Exception {
        Broker broker = new Broker();
        broker.setId(rs.getLong("Id"));
        broker.setNumeroCameraCommercio(rs.getString("NumeroCameraCommercio"));
        broker.setNome(rs.getString("Nome"));
        broker.setCognome(rs.getString("Cognome"));
        broker.setEmail(rs.getString("Email"));
        broker.setPassword(rs.getString("Password"));
        broker.setDataNascita(rs.getDate("DataNascita"));
        
        java.util.ArrayList<PacchettoAzionario> pacchettiCreati = readPacchettiCreati(connection, broker.getId());
        broker.setPacchettiCreati(pacchettiCreati);
        
        return broker;
    }
    
    public static java.util.ArrayList<PacchettoAzionario> readPacchettiCreati(java.sql.Connection connection, Long brokerId) throws Exception {
    	java.util.ArrayList<PacchettoAzionario> listaPacchetti = new java.util.ArrayList<>();
    	
    	PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "SELECT * FROM PacchettiCreati WHERE IdBroker=?" ;
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setLong(1, brokerId);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
        	PacchettoAzionario pacchetto = null;
            while (resultSet.next() == true) {
            	long idPacchetto = resultSet.getLong("IdPacchetto");
            	pacchetto = PacchettoAzionarioDAO.readPacchettoAzionario(connection, idPacchetto);
            	listaPacchetti.add(pacchetto);
            }
            resultSet.close();
            return listaPacchetti;
        } catch (SQLException e) {
            throw new Exception("Errore durante la lettura dei pacchetti creati.", e);
        } finally {
        	preparedStatement.close();
        }
    }
    
	public static void deleteBroker(java.sql.Connection connection, String mailBroker) throws Exception {
		PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "DELETE FROM Broker WHERE Email = ?";
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1, mailBroker);

    		preparedStatement.executeUpdate();

           
        } catch (SQLException e) {
        	throw new Exception("Errore durante la cancellazione dal database.");
        } finally {
        	preparedStatement.close();
        }
	}
    
}
