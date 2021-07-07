package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.Broker;
import entity.PacchettoAzionario;
import entity.TitoloAzionario;

public class PacchettoAzionarioDAO {
	
	public static void createPacchettoAzionario(java.sql.Connection connection, PacchettoAzionario pacchetto, Broker creatore) throws Exception {
    	
		//Gestore gestore = Gestore.getInstance();
		//Broker creatore = (Broker) gestore.getCurrentUser();
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null; 
    	try {
    		final String QUERY_SQL = "INSERT INTO Pacchetti (FattoreRischio, StimaRendimento) VALUES (?, ?)";
    		preparedStatement = connection.prepareStatement(QUERY_SQL, Statement.RETURN_GENERATED_KEYS);
    		preparedStatement.setFloat(1, pacchetto.getFattoreRischio());
    		preparedStatement.setFloat(2, pacchetto.getStimaRendimento());

    		
    		preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next() == true) {
            	long ID = resultSet.getLong(1);
                if (resultSet.wasNull() == false) {
                	pacchetto.setId(ID);
                	//System.out.println("Debug: ID: " + pacchetto.getId());
                }
            }
            resultSet.close();
            /*
            CREATE TABLE ComposizionePacchetti (
	    	IdPacchetto LONG,
	        IdTitolo LONG,
	        FOREIGN KEY (IdTitolo) REFERENCES Titoli (Id),
	        FOREIGN KEY (IdPacchetto) REFERENCES Pacchetti (Id),
	        NumeroAzioni INT,
	        PRIMARY KEY(IdPacchetto,IdTitolo) );   
	        
	        CREATE TABLE PacchettiCreati (
	        IdBroker LONG,
	        IdPacchetto LONG,		
	        FOREIGN KEY (IdBroker) REFERENCES Broker (Id),
	        FOREIGN KEY (IdPacchetto) REFERENCES Pacchetti (Id),
	        PRIMARY KEY(IdBroker,IdPacchetto) );
            */
            final String QUERY_SQL2 = "INSERT INTO PacchettiCreati (IdBroker, IdPacchetto) VALUES (?, ?)"; 
            preparedStatement2 = connection.prepareStatement(QUERY_SQL2);

            preparedStatement2.setLong(1, creatore.getId());
            System.out.println("ID Broker settato: " + creatore.getId());
            preparedStatement2.setLong(2, pacchetto.getId());
            System.out.println("ID Pacchetto settato: " + pacchetto.getId());
            preparedStatement2.executeUpdate();
            System.out.println("Insert in pacchetti creati fatto.");
            final String QUERY_SQL3 = "INSERT INTO ComposizionePacchetti (IdPacchetto, IdTitolo, NumeroAzioni) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE NumeroAzioni = NumeroAzioni+1";
        	for (TitoloAzionario t : pacchetto.getTitoli()) {
        		preparedStatement3 = connection.prepareStatement(QUERY_SQL3);
                preparedStatement3.setLong(1, pacchetto.getId());
                System.out.println("ID Pacchetto settato: " + pacchetto.getId());
                preparedStatement3.setLong(2, t.getId());
                System.out.println("ID Titolo settato: " + t.getId());
                preparedStatement3.setInt(3, 1);
                preparedStatement3.executeUpdate(); // org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
        	}


        } catch (SQLException e) {
        	System.out.println("Errore");
        	e.printStackTrace();
        	throw new Exception("Errore durante l'inserimento nel database.");
        } finally {
        	preparedStatement.close();
        	preparedStatement2.close();
        	preparedStatement3.close();
        }
    	
	}
	
    public static PacchettoAzionario readPacchettoAzionario(java.sql.Connection connection, long id) throws Exception {
    	PacchettoAzionario pacchetto = null;
    	PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "SELECT * FROM Pacchetti WHERE ID=?" ;
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setLong(1, id);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next() == true) {
                pacchetto = deserializeCurrentRecord(connection, resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new Exception("Errore durante la lettura da database.", e);
        } finally {
        	preparedStatement.close();
        }
        
        return pacchetto;
    }
    
    public static ArrayList<PacchettoAzionario> readPacchettiAzionari(java.sql.Connection connection) throws Exception {
    	ArrayList<PacchettoAzionario> listaPacchetti = new ArrayList<PacchettoAzionario>();
    	
    	PreparedStatement preparedStatement = null;
		/*if (utente instanceof Broker) {
			System.out.println("Riconosciuto broker.");
	    	try {
	    		final String QUERY_SQL = "SELECT * FROM Pacchetti" ; // mostra tutti i pacchetti
	    		preparedStatement = connection.prepareStatement(QUERY_SQL);
	    		ResultSet resultSet = preparedStatement.executeQuery();
	        	PacchettoAzionario pacchetto = null;
	            while (resultSet.next() == true) {
	            	pacchetto = deserializeCurrentRecord(connection, resultSet);
	            	listaPacchetti.add(pacchetto);
	            }
	            resultSet.close();
	            
	        } catch (SQLException e) {
	            throw new Exception("Errore durante la lettura dei titoli nella borsa.", e);
	        } finally {
	        	preparedStatement.close(); // Cannot invoke "java.sql.PreparedStatement.close()" because "preparedStatement" is null
	        }
			
		} else if (utente instanceof Risparmiatore) {
			System.out.println("Riconosciuto risparmiatore.");
	    	try {
	    		final String QUERY_SQL = "SELECT StimaRendimento, FattoreRischio, Pacchetti.Id as Id FROM Transazioni JOIN Pacchetti ON Transazioni.IdPacchetto = Pacchetti.Id WHERE IdProprietario=?" ;
	    		preparedStatement = connection.prepareStatement(QUERY_SQL);
	    		preparedStatement.setLong(1, utente.getId());
	    		ResultSet resultSet = preparedStatement.executeQuery();
	        	PacchettoAzionario pacchetto = null;
	            while (resultSet.next() == true) {
	            	pacchetto = deserializeCurrentRecord(connection, resultSet);
	            	listaPacchetti.add(pacchetto);
	            }
	            resultSet.close();
	            
	        } catch (SQLException e) {
	            throw new Exception("Errore durante la lettura dei titoli nella borsa.", e);
	        } finally {
	        	preparedStatement.close(); // Cannot invoke "java.sql.PreparedStatement.close()" because "preparedStatement" is null
	        }
			
		} */
    	try {
    		final String QUERY_SQL = "SELECT * FROM Pacchetti" ; // mostra tutti i pacchetti
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		ResultSet resultSet = preparedStatement.executeQuery();
        	PacchettoAzionario pacchetto = null;
            while (resultSet.next() == true) {
            	pacchetto = deserializeCurrentRecord(connection, resultSet);
            	listaPacchetti.add(pacchetto);
            }
            resultSet.close();
            
        } catch (SQLException e) {
            throw new Exception("Errore durante la lettura dei titoli nella borsa.", e);
        } finally {
        	preparedStatement.close(); // Cannot invoke "java.sql.PreparedStatement.close()" because "preparedStatement" is null
        }
		
		return listaPacchetti;
    }
    
    private static PacchettoAzionario deserializeCurrentRecord(java.sql.Connection connection, ResultSet rs) throws Exception {
    	PacchettoAzionario pacchetto = new PacchettoAzionario();
        pacchetto.setId(rs.getLong("Id"));
        pacchetto.setFattoreRischio(rs.getFloat("FattoreRischio"));
        pacchetto.setStimaRendimento(rs.getFloat("StimaRendimento"));

        
        java.util.ArrayList<TitoloAzionario> titoliContenuti = readTitoliContenuti(connection, pacchetto.getId());
        pacchetto.setTitoli(titoliContenuti);
        
        return pacchetto;
    }
    
    public static java.util.ArrayList<TitoloAzionario> readTitoliContenuti(java.sql.Connection connection, Long idPacchetto) throws Exception {
    	java.util.ArrayList<TitoloAzionario> listaTitoli = new java.util.ArrayList<TitoloAzionario>();
    	
    	PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "SELECT * FROM ComposizionePacchetti WHERE IdPacchetto=?" ;
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setLong(1, idPacchetto);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
        	TitoloAzionario titolo = null;
            while (resultSet.next() == true) {
            	long idTitolo = resultSet.getLong("IdTitolo");
            	int quantita = resultSet.getInt("NumeroAzioni");
            	titolo = TitoloAzionarioDAO.readTitoloAzionario(connection, idTitolo);
            	for (int i = 0; i<quantita; i++) {
            		
            		listaTitoli.add(titolo);
            	}
            }
            resultSet.close();
            return listaTitoli;
        } catch (SQLException e) {
            throw new Exception("Errore durante la lettura dei titoli contenuti nel pacchetto.", e);
        } finally {
        	preparedStatement.close();
        }
    }
}
