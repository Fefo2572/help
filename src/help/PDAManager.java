package help;

import java.sql.SQLException;
import java.sql.*;


/*
 * PDAManager -> Polo-Diocesi-Azienda Manager
 * Questa classe gestisce ciò che concerne le aziende, i poli e le diocesi:
 * quindi permette di aggiungerne di nuove e l'eliminazione
 * di quelle esistenti.
 * @version 1.0
 * @author Federico Ennio Ambrogi
 */

public class PDAManager {
	
	
	//Gestione Magazzino
	
	public static int addMagazzino(int capienza_massima) throws ClassNotFoundException, SQLException {
		
		//Apro la connessione al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		//Creo la variabile id che dovrà essere restituita
		int id = 0;
		
		//Vettore di stringhe con le colonne da inserire
		String[] col = {"capienza_massima"};
				
		//Vettore di stringhe con i valori da inserire
		String[] val = {Integer.toString(capienza_massima)};
		
		id = dbm.insertAutoIncrementRecord("magazzino",col,val);
		
		return id;
	}
	
	//Gestione Azienda
	
	public static int addAzienda(String nome, String citta, String nazione) throws ClassNotFoundException, SQLException {
		
		//Apro la connessione al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		//Creo la variabile id che dovrà essere restituita
		int id = 0;
		
		//Vettore di stringhe con le colonne da inserire
		String[] col = {"nome","citta","nazione"};
				
		//Vettore di stringhe con i valori da inserire
		String[] val = {nome,citta,nazione};
		
		id = dbm.insertAutoIncrementRecord("azienda",col,val);
		
		return id;
	}
	
	public static void removeAzienda(int id) throws ClassNotFoundException, SQLException {
		//TODO Codice per rimuovere azienda
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		dbm.deleteRecord("azienda", "id="+id);
	}
	
	//Gestione Polo
	
	public static int addPolo(String nome, String citta, int id_diocesi) throws SQLException, ClassNotFoundException {
		//Apro la connessione al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		//Creo la variabile id che dovrà essere restituita
		int id = 0;
		
		//Vettore di stringhe con le colonne da inserire
		String[] col = {"nome","citta", "id_diocesi", "id_magazzino", "stato"};
				
		//Vettore di stringhe con i valori da inserire
		String[] val = {nome,citta,Integer.toString(id_diocesi), Integer.toString( addMagazzino(100) ) , "act"};
		
		id = dbm.insertAutoIncrementRecord("polo",col,val);
		
		return id;
	}
	
	public static void removePolo(int id) throws ClassNotFoundException, SQLException {
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ResultSet result = dbm.executeQuery("SELECT id_magazzino FROM polo WHERE id="+id);
		String id_magazzino = Integer.toString( result.getInt("id_magazzino") ); 
		dbm.deleteRecord("magazzino", id_magazzino);
		//dbm.deleteRecord("polo", "id="+id);
	}
	
	//Gestione Diocesi
	
	public static int addDiocesi(String nome,String comune) throws SQLException, ClassNotFoundException {
		//Apro la connessione al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		//Creo la variabile id che dovrà essere restituita
		int id = 0;
		
		//Vettore di stringhe con le colonne da inserire
		String[] col = {"nome","comune", "id_magazzino"};
				
		//Vettore di stringhe con i valori da inserire
		String[] val = {nome,comune, Integer.toString( addMagazzino(100) )};
		
		id = dbm.insertAutoIncrementRecord("diocesi",col,val);
		
		return id;
	}
	
	public static void removeDiocesi(int id) throws ClassNotFoundException, SQLException {
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ResultSet result = dbm.executeQuery("SELECT id_magazzino FROM diocesi WHERE id="+id);
		String id_magazzino = Integer.toString( result.getInt("id_magazzino") ); 
		dbm.deleteRecord("magazzino", id_magazzino);
		//dbm.deleteRecord("azienda", "id="+id);
	}

}
