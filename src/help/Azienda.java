package help;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Questa classe si occupa della gestione di ogni funzione legata all`azienda
 * @author Federico Ennio Ambrogi
 * @version 0.1
 */

public class Azienda {
	
	
	/**
	 * Crea un nuovo lotto
	 * 
	 * @param id_tipologia
	 * @param id_azienda
	 * @param scadenza
	 * @param quantita
	 * @param data_produzione
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ScadenzaException
	 * @throws FutureDateException
	 * @throws negativeNumberException
	 */
	
	public static int creaLotto(Integer id_tipologia, Integer id_azienda, LocalDate scadenza, Integer quantita, LocalDate data_produzione) throws ClassNotFoundException, SQLException, ScadenzaException, FutureDateException, negativeNumberException {
		//Valore di ritorno
		int idLotto = 0;
		//Effettuo controlli sulle date
		checkScadenza(scadenza);
		checkFutureDate(data_produzione);
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		//Controllo quantita positiva
		if(quantita>0) {
			String[] col = {"id_tipologia","id_azienda","scadenza","quantita","data_produzione"};
			String dataproduzioneString = DatabaseManager.dataDBMSFormatter(data_produzione);
			String scadenzaString = DatabaseManager.dataDBMSFormatter(scadenza);	
			String[] val = {id_tipologia.toString(),id_azienda.toString(),scadenzaString,quantita.toString(),dataproduzioneString};
			idLotto = dbm.insertAutoIncrementRecord("lotto", col, val);
		}else {
			//Errore
			throw new negativeNumberException();
		}
		return idLotto;
	}
	
	

	
	/**
	 * Una funzione che da tutti i lotti di un azienda
	 * 
	 * @param id_adienza
	 * @return arraylist di lotti
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public ArrayList<Lotto> getLotti(Integer id_azienda) throws SQLException, ClassNotFoundException{
		ArrayList <Lotto> llotti = new ArrayList<>();
		//Connetto al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ResultSet result = dbm.executeQuery("SELECT * FROM lotto WHERE id_azienda = '"+id_azienda+"'");
		while(result.next()) {
			LocalDate scadenza = DatabaseManager.javaDateFormatter(result.getString("scadenza")); 
			LocalDate produzione = DatabaseManager.javaDateFormatter(result.getString("data_produzione"));
			
			Lotto u = new Lotto(result.getInt("id"),result.getInt("id_tipologia"),result.getInt("id_azienda"), scadenza, result.getInt("quantita"),produzione);
			llotti.add(u);
		}
		return llotti;
	}
	
	
	
    /**
     * Verifica se una data di scadenza è entro 30 giorni dalla giornata odierna.
     *
     * @param scadenza la data di scadenza da controllare
     * @throws ScadenzaException se la data di scadenza è scaduta o è entro i prossimi 30 giorni
     */
    public static void checkScadenza(LocalDate scadenza) throws ScadenzaException {
        LocalDate oggi = LocalDate.now();
        LocalDate limite = oggi.plusDays(30);

        if (scadenza.isBefore(limite)) {
            throw new ScadenzaException("La data di scadenza è scaduta o è entro i prossimi 30 giorni!");
        }
    }
    
    /**
     * Verifica se una data è nel futuro.
     *
     * @param data la data da controllare
     * @throws FutureDateException se la data è nel futuro
     */
    public static void checkFutureDate(LocalDate data) throws FutureDateException {
        LocalDate oggi = LocalDate.now();

        if (data.isAfter(oggi)) {
            throw new FutureDateException("La data è nel futuro!");
        }
    }
	
	public static void addToCatalogo() {
		
	}
	/**
	 * Una funzione che restituisce il catalogo di una data azienda
	 * 
	 * @param id_azienda id dell'azienda
	 * @throws SQLException 
	 * @return ArrayList di TipologiaViveri associati all'azienda
	 * @throws ClassNotFoundException 
	 */
	public static ArrayList<TipologiaViveri> getCatalogo(int id_azienda) throws SQLException, ClassNotFoundException {
		ArrayList <TipologiaViveri> lviveri = new ArrayList<>();
		//Scrivo la query che mi da le informazioni che mi servono
		String query = "SELECT tv.* "
				+ " FROM tipologia_viveri tv "
				+ " JOIN catalogo_azienda c ON tv.id = c.id_tipologia "
				+ " WHERE c.id_azienda = "+id_azienda;
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ResultSet result = dbm.executeQuery(query);
		while(result.next()) {
			//Creo un oggetto tipologia viveri
			TipologiaViveri tv = new TipologiaViveri(result.getInt("id"), result.getString("nome_cibo"), result.getInt("fabbisogno_adulto"), result.getInt("fabbisogno_bambino"),result.getInt("fabbisogno_neonato"));
			//Aggiungo il nuovo oggetto all'array
			lviveri.add(tv);
		}
		dbm.disconnect();
		return lviveri;
	}
	
	/**
	 * Funzione che aggiunge elementi al catalogo
	 * 
	 * @param id_azienda
	 * @param id_prodotto
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void addToCatalago(Integer id_azienda, Integer id_tipologia) throws ClassNotFoundException, SQLException {
		//Connessione al db
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		//Stringhe per fare l'inserimento, colonne del db e valori
		String[] col = {"id_azienda","id_tipologia"};
		String[] val = {id_azienda.toString(),id_tipologia.toString()};
		System.out.println(val[0]+" "+val[1]);
		//Inserimento
		dbm.insertRecord("catalogo_azienda", col, val);
		
		//Disconnessione
		dbm.disconnect();
	}
	
	//eliminazione
	public static void deleteFromCatalogo(int id_azienda, int id_cibo) throws SQLException, ClassNotFoundException {
        DatabaseManager dbm = new DatabaseManager();
        dbm.connect();
        String condition = "id_azienda = "+id_azienda+" AND "+"id_tipologia = "+id_cibo;
        dbm.deleteRecord("catalogo_azienda",condition);
    }
	
	
	public static ArrayList<Richiesta> getRichieste(int id_azienda) throws ClassNotFoundException, SQLException{
		ArrayList<Richiesta> lista=new ArrayList<>();
		String query="SELECT * FROM richiesta";
		DatabaseManager dbm = new DatabaseManager();
			dbm.connect();
			ResultSet result = dbm.executeQuery(query);
			while(result.next()) {
				String datastringa=result.getString("data");
				LocalDate data=DatabaseManager.javaDateFormatter(datastringa);
				Richiesta r=new Richiesta(result.getInt("id"),data,result.getString("stato"));
				lista.add(r);
			}
			return lista;
	}
	
	
	public static ArrayList<ListaViveri> getdettagliRichiesta(int id_azienda,int idrichiesta) throws ClassNotFoundException, SQLException{
		ArrayList<ListaViveri> lista=new ArrayList<>();
		String query="SELECT tv.nome_cibo,tv.id,lv.quantita "
				+ " FROM tipologia_viveri tv "
				+ " JOIN lista_viveri lv ON tv.id=lv.id_cibo"
				+ " JOIN  catalogo_azienda ca ON lv.id_cibo=ca.id_tipologia "
				+ " JOIN richiesta r ON r.id=lv.id_richiesta "
				+ " WHERE ca.id_azienda ="+id_azienda+" AND id_richiesta="+idrichiesta;
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ResultSet result = dbm.executeQuery(query);
		while(result.next()) {
			ListaViveri lv=new ListaViveri(result.getInt("id"),result.getString("nome_cibo"),result.getInt("quantita"));
			lista.add(lv);
		}
		return lista;
	}
	

}
