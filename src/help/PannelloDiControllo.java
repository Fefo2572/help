/**
 * Classe contenente i metodi del pannello di controllo
 * @version 0
 */

package help;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PannelloDiControllo {
	
	//gestione tipologia_viveri
	//inserimento di una nuova tipologia di cibo nella lista tipologia_viveri
	public static int creaCibo(Integer id,String nome_cibo,Integer f_adulto,Integer f_bambino,Integer f_neonato) throws ClassNotFoundException, SQLException {
		//valore di ritorno
		int idCibo=0;
		//invoco la classe che gestisce il db
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String[] colonne= {"id","nome_cibo","fabbisogno_adulto","fabbisogno_bambino","fabbisogno_neonato"};
		String[] values= {id.toString(),nome_cibo,f_adulto.toString(),f_bambino.toString(),f_neonato.toString()};
		idCibo=dbm.insertRecord("tipologia_viveri", colonne, values);
		return idCibo;
	}
	
	
	//mostra i valori presenti nel DB facendo una SELECT
	public static ArrayList<TipologiaViveri> getlistaViveri(int id_azienda) throws SQLException, ClassNotFoundException {
		ArrayList <TipologiaViveri> lviveri = new ArrayList<>();
		//Scrivo la query che mi da le informazioni che mi servono
		String query = "SELECT * FROM tipologia_viveri";
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
	
	//eliminazione di un record dal db
		public static void deleteFromTipologiaViveri(Integer id_cibo) throws SQLException, ClassNotFoundException {
	        DatabaseManager dbm = new DatabaseManager();
	        dbm.connect();
	        String condition = "id = "+id_cibo;
	        dbm.deleteRecord("tipologia_viveri",condition);
	    }
		
		
	//modifica di un valore nel db
		public static void updateListaViveri(Integer id,String nome_cibo,Integer f_adulto,Integer f_bambino,Integer f_neonato) throws ClassNotFoundException, SQLException{
			 DatabaseManager dbm = new DatabaseManager();
		        dbm.connect();
		        String[] colonne= {"id","nome_cibo","fabbisogno_adulto","fabbisogno_bambino","fabbisogno_neonato"};
		        String[] values= {id.toString(),nome_cibo,f_adulto.toString(),f_bambino.toString(),f_neonato.toString()};
		        String condition = "id = "+id;
		        dbm.editRecord("tipologia_viveri",colonne,values,condition);
		}
		
		
		
		//funzioni gestione diocesi
		//visualizzazione valori nel db
		public static ArrayList<Diocesi> getlistaDiocesi(int id) throws SQLException, ClassNotFoundException {
			ArrayList <Diocesi> ldiocesi = new ArrayList<>();
			//Scrivo la query che mi da le informazioni che mi servono
			String query = "SELECT * FROM diocesi";
			DatabaseManager dbm = new DatabaseManager();
			dbm.connect();
			ResultSet result = dbm.executeQuery(query);
			while(result.next()) {
				//Creo un oggetto tipologia viveri
				Diocesi d = new Diocesi(result.getInt("id"),result.getString("nome"),result.getString("comune"));
				//Aggiungo il nuovo oggetto all'array
				ldiocesi.add(d);
			}
			dbm.disconnect();
			return ldiocesi;
		}
		
		
		//modifica di un valore nel db nella tabella diocesi
				public static void updateListaDiocesi(Integer id,String nome,String comune) throws ClassNotFoundException, SQLException{
					 DatabaseManager dbm = new DatabaseManager();
				        dbm.connect();
				        String[] colonne= {"id","nome","comune"};
				        String[] values= {id.toString(),nome,comune};
				        String condition = "id = "+id;
				        dbm.editRecord("diocesi",colonne,values,condition);
				}
				
		//eliminazione di un record dal db
			public static void deleteFromDiocesi(Integer id) throws SQLException, ClassNotFoundException {
			     DatabaseManager dbm = new DatabaseManager();
			     dbm.connect();
			     String condition = "id = "+id;
			     dbm.deleteRecord("diocesi",condition);
			}
		
			  //funzioni per la gestione dei poli da parte dell'admin 
			  //visualizzazione valori nel db
			  		public static ArrayList<Polo> getlistaPoli() throws SQLException, ClassNotFoundException {
			  			ArrayList <Polo> lpoli = new ArrayList<>();
			  			//Scrivo la query che mi da le informazioni che mi servono
			  			String query = "SELECT * FROM polo";
			  			DatabaseManager dbm = new DatabaseManager();
			  			dbm.connect();
			  			ResultSet result = dbm.executeQuery(query);
			  			while(result.next()) {
			  				//Creo un oggetto tipologia viveri
			  				Polo d = new Polo(result.getInt("id"),result.getString("nome"),result.getString("citta"),result.getInt("id_diocesi"),result.getString("stato"));
			  				//Aggiungo il nuovo oggetto all'array
			  				lpoli.add(d);
			  			}
			  			dbm.disconnect();
			  			return lpoli;
			  		}
			  		
			  	//eliminazione di un record dal db Polo
					public static void deleteFromPolo(Integer id) throws SQLException, ClassNotFoundException {
					     DatabaseManager dbm = new DatabaseManager();
					     dbm.connect();
					     String condition = "id = "+id;
					     dbm.deleteRecord("polo",condition);
					}
			
					//modifica di un valore nel db nella tabella polo
					public static void updateListaPolo(Integer id,String nome,String citta,Integer id_diocesi,String stato) throws ClassNotFoundException, SQLException{
						 DatabaseManager dbm = new DatabaseManager();
					        dbm.connect();
					        String[] colonne= {"id","nome","citta","id_diocesi","stato"};
					        String[] values= {id.toString(),nome,citta,id_diocesi.toString(),stato};
					        String condition = "id = "+id;
					        dbm.editRecord("polo",colonne,values,condition);
					}
					
	//visualizzazione lista degli errori tabella errore nel db
		public static ArrayList<Errore> getlistaErrori() throws SQLException, ClassNotFoundException {
					ArrayList <Errore> lerrori = new ArrayList<>();
					//Scrivo la query che mi da le informazioni che mi servono
					String query = "SELECT * FROM errore";
					DatabaseManager dbm = new DatabaseManager();
					dbm.connect();
					ResultSet result = dbm.executeQuery(query);
					while(result.next()) {
						//Creo un oggetto tipologia viveri
						Errore e = new Errore(result.getInt("id"),result.getString("descrizione"),result.getInt("id_polo"));
						//Aggiungo il nuovo oggetto all'array
						lerrori.add(e);
					}
					dbm.disconnect();
					return lerrori;
			}
	
		public static void creaRichiesta() {
			try {
				DatabaseManager dbm=new DatabaseManager();
				dbm.connect();
				LocalDate oggi = LocalDate.now();
				String dataformattata=DatabaseManager.dataDBMSFormatter(oggi);
				
				String[] col= {"data","stato"};
				String[] val= { dataformattata,"act"};
				Integer id_richiesta=dbm.insertAutoIncrementRecord("richiesta", col, val);
				ArrayList<Porzione> porzioni;
				porzioni=Distributore.getPrevisioneGlobale();
				for(Porzione p: porzioni) {
					String[] col2= {"id_cibo","id_richiesta","quantita"};
					Integer idviveri=p.getIdViveri();
					Integer quantita=p.getQuantita();
					String[] val2= {idviveri.toString(),id_richiesta.toString(),quantita.toString()};
					dbm.insertRecord("lista_viveri", col2, val2);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

}
