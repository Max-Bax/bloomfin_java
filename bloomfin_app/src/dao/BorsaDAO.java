package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Borsa;
import entity.TitoloAzionario;

public class BorsaDAO {
	/* 	    CREATE TABLE Borse (
	        Nome VARCHAR(255) NOT NULL PRIMARY KEY,
	        StatoAppartenenza VARCHAR(255),
	        OrarioApertura TIME,
	        OrarioChiusura TIME );
	*/
	public static void createBorsa(java.sql.Connection connection, Borsa borsa) throws Exception {

		
		PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "INSERT INTO Borse (Nome, StatoAppartenenza, OrarioApertura, OrarioChiusura) VALUES (?, ?, ?, ?)" ;
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1, borsa.getNome()); 
    		preparedStatement.setString(2, borsa.getStatoAppartenenza());
    		preparedStatement.setTime(3, borsa.getOrarioApertura());
    		preparedStatement.setTime(4, borsa.getOrarioChiusura());
    		
    		preparedStatement.executeUpdate();
            //ResultSet resultSet = preparedStatement.getGeneratedKeys();
            /*if (resultSet.next() == true) {
            	long ID = resultSet.getLong("SCOPE_IDENTITY()");
                if (resultSet.wasNull() == false) {
                    broker.setId(ID);
                }
            }*/
            //resultSet.close();
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento nel database.");
        } finally {
        	preparedStatement.close();
        }
	}
	
    public static Borsa readBorsa(java.sql.Connection connection, String nome) throws Exception {
    	Borsa borsa = null;
    	PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "SELECT * FROM Borse WHERE Nome=?" ;
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1, nome);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next() == true) {
                borsa = deserializeCurrentRecord(connection, resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new Exception("Errore durante la lettura da database.", e);
        } finally {
        	preparedStatement.close();
        }
        
        return borsa;
    }
    
    private static Borsa deserializeCurrentRecord(java.sql.Connection connection, ResultSet rs) throws Exception {
        Borsa borsa = new Borsa();
        borsa.setNome(rs.getString("Nome"));
        borsa.setStatoAppartenenza(rs.getString("StatoAppartenenza"));
        borsa.setOrarioApertura(rs.getTime("OrarioApertura"));
        borsa.setOrarioChiusura(rs.getTime("OrarioChiusura"));
        
        java.util.ArrayList<TitoloAzionario> titoli = readTitoli(connection, borsa.getNome());
        borsa.setListaTitoli(titoli);
        
        return borsa;
    }
    
    public static java.util.ArrayList<TitoloAzionario> readTitoli(java.sql.Connection connection, String nomeBorsa) throws Exception {
    	java.util.ArrayList<TitoloAzionario> listaTitoli = new java.util.ArrayList<TitoloAzionario>();
    	
    	PreparedStatement preparedStatement = null;
    	try {
    		final String QUERY_SQL = "SELECT * FROM AppartenenzaTitoli WHERE NomeBorsa=?" ; // TODO
    		preparedStatement = connection.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1, nomeBorsa);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
        	TitoloAzionario titolo = null;
            while (resultSet.next() == true) {
            	long idTitolo = resultSet.getLong("IdTitolo");
            	titolo = TitoloAzionarioDAO.readTitoloAzionario(connection, idTitolo);
            	listaTitoli.add(titolo);
            }
            resultSet.close();
            return listaTitoli;
        } catch (SQLException e) {
            throw new Exception("Errore durante la lettura dei titoli nella borsa.", e);
        } finally {
        	preparedStatement.close();
        }
    }

}
