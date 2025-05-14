package help;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 * Classe contenente le informazioni 
 * 
 * @author Federico Ennio Ambrogi
 * @version 0.3
 */

public class Distributore {
	
	/**
	 * Questa funzione crea un carico partendo da un lotto
	 * @throws InvalidPrimaryKeyException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static int createCaricoFromLotto(int id_lotto, int id_magazzino) throws InvalidPrimaryKeyException, ClassNotFoundException, SQLException {
		//Connessione al database
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		//Valore di ritorno
		int id_carico = 0;
		Boolean exists = dbm.checkExists("lotto", "id = "+id_lotto );
		if(exists) {
			//Esiste il lotto
			exists = dbm.checkExists("magazzino", "id = "+id_magazzino );
			if(exists) {
				//Esiste il magazzino
				
				//Effettuo la query per prelevare le informazioni del lotto
				String query = "SELECT * FROM lotto WHERE id = "+id_lotto;
				ResultSet result = dbm.executeQuery(query);
				Lotto lotto;
				while(result.next()) {
					LocalDate scadenza = DatabaseManager.javaDateFormatter(result.getString("scadenza")); 
					LocalDate produzione = DatabaseManager.javaDateFormatter(result.getString("data_produzione"));					
					lotto = new Lotto(result.getInt("id"),result.getInt("id_tipologia"),result.getInt("id_azienda"), scadenza, result.getInt("quantita"),produzione);
					String col[] = {"quantita", "stato", "data", "id_lotto", "id_magazzino"};
					//Lo stato di default appena si crea il carico è chiaramente in spedizione
					LocalDate dataOdierna = LocalDate.now();
					String dataOdiernaString = DatabaseManager.dataDBMSFormatter(dataOdierna);
					String val[] = { Integer.toString(lotto.getQuantita()), "del", dataOdiernaString, Integer.toString(lotto.getId()) , Integer.toString(id_magazzino)};
					id_carico = dbm.insertAutoIncrementRecord("carico", col, val);
					
				}
			}else {
				//Il magazzino non esiste
				dbm.disconnect();
				throw new InvalidPrimaryKeyException("id_magazzino non esistente");
			}
		}else {
			//Il lotto non esiste
			dbm.disconnect();
			throw new InvalidPrimaryKeyException("id_lotto inesistente");
		}
		dbm.disconnect();
		return id_carico;
	}
	
	public static int createCarico(Carico c) throws SQLException, ClassNotFoundException, InvalidPrimaryKeyException {
		//Connessione al database
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		//Valore di ritorno
		int id_carico = 0;
		Boolean exists = dbm.checkExists("lotto", "id = "+c.getIdLotto() );
		if(exists) {
			//Esiste il lotto
			exists = dbm.checkExists("magazzino", "id = "+c.getIdMagazzino() );
			if(exists) {
				//Esiste il magazzino
				String col[] = {"quantita", "stato", "data", "id_lotto", "id_magazzino"};
				//Lo stato di default appena si crea il carico è chiaramente in spedizione
				String dataString = DatabaseManager.dataDBMSFormatter(c.getData());
				String val[] = { Integer.toString(c.getQuantita()),c.getStato(),dataString,Integer.toString(c.getIdLotto()),Integer.toString(c.getIdMagazzino())};
				id_carico = dbm.insertAutoIncrementRecord("carico", col, val);
				dbm.disconnect();
			}else {
				//Il magazzino non esiste
				dbm.disconnect();
				throw new InvalidPrimaryKeyException("id_magazzino non esistente");
			}
		}else {
			//Il lotto non esiste
			dbm.disconnect();
			throw new InvalidPrimaryKeyException("id_lotto inesistente");
		}
		dbm.disconnect();
		return id_carico;
	}
	
	/**
	 * Questa funzione restituisce ogni utente presente nel sistema
	 * 
	 * @return ArrayList di utenti
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static ArrayList<Utente> getEveryUtente() throws ClassNotFoundException, SQLException{
		ArrayList<Utente> utenti = new ArrayList<>();
		//Connessione al db
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = " SELECT u.*"
				     + " FROM utente u"
				     + " JOIN nucleo_familiare nf ON u.id_nucleo = nf.id"
				     + " JOIN polo p on nf.id_polo = p.id"
				     + " WHERE p.stato='act'";
		ResultSet result = dbm.executeQuery(query);
		while(result.next()) {
			LocalDate data = DatabaseManager.javaDateFormatter( result.getString("data_di_nascita") );
			Utente u = new Utente(result.getString("cf"),result.getString("nome"),result.getString("cognome"), result.getString("sesso"), data, result.getString("residenza"));
			String query2 = "SELECT id_disturbo FROM disturbi_utente WHERE cf = '"+u.getCodiceFiscale()+"'";
			ResultSet result2 = dbm.executeQuery(query2);
			while(result2.next()) {
				if(result2.getInt("id_disturbo") == 1) {
					u.setCeliaco(true);
				}else if(result2.getInt("id_disturbo") == 2) {
					u.setCeliaco(true);
				}
			}
			utenti.add(u);
		}
		dbm.disconnect();
		return utenti;
	}
	
	/**
	 * Restituisce un arraylist con ogni utente che viene da un
	 * dato polo
	 * 
	 * @param id_polo
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ArrayList<Utente> getEveryUtentePolo(Integer id_polo) throws ClassNotFoundException, SQLException{
		ArrayList<Utente> utenti = new ArrayList<>();
		//Connessione al db
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = " SELECT u.*"
				     + " FROM utente u"
				     + " JOIN nucleo_familiare nf ON u.id_nucleo = nf.id"
				     + " JOIN polo p on nf.id_polo = p.id"
				     + " WHERE p.stato='act' AND id_polo = " + id_polo;
		ResultSet result = dbm.executeQuery(query);
		while(result.next()) {
			LocalDate data = DatabaseManager.javaDateFormatter( result.getString("data_di_nascita") );
			Utente u = new Utente(result.getString("cf"),result.getString("nome"),result.getString("cognome"), result.getString("sesso"), data, result.getString("residenza"));
			String query2 = "SELECT id_disturbo FROM disturbi_utente WHERE cf = '"+u.getCodiceFiscale()+"'";
			ResultSet result2 = dbm.executeQuery(query2);
			while(result2.next()) {
				if(result2.getInt("id_disturbo") == 1) {
					u.setCeliaco(true);
				}else if(result2.getInt("id_disturbo") == 2) {
					u.setCeliaco(true);
				}
			}
			
			utenti.add(u);
		}
		dbm.disconnect();
		return utenti;
	}
	
	/**
	 * Restituisce tutte gli utenti
	 * il quale polo (attivo) è iscritto
	 * alla data diocesi in input
	 * 
	 * @param id_diocesi
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ArrayList<Utente> getEveryUtenteDiocesi(Integer id_diocesi) throws ClassNotFoundException, SQLException{
		ArrayList<Utente> utenti = new ArrayList<>();
		//Connessione al db
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = " SELECT u.*"
				     + " FROM utente u"
				     + " JOIN nucleo_familiare nf ON u.id_nucleo = nf.id"
				     + " JOIN polo p on nf.id_polo = p.id"
				     + " JOIN diocesi d on p.id_diocesi = d.id"
				     + " WHERE p.stato='act' AND id_diocesi = " + id_diocesi.toString();
		ResultSet result = dbm.executeQuery(query);
		while(result.next()) {
			LocalDate data = DatabaseManager.javaDateFormatter( result.getString("data_di_nascita") );
			Utente u = new Utente(result.getString("cf"),result.getString("nome"),result.getString("cognome"), result.getString("sesso"), data, result.getString("residenza"));
			String query2 = "SELECT id_disturbo FROM disturbi_utente WHERE cf = '"+u.getCodiceFiscale()+"'";
			ResultSet result2 = dbm.executeQuery(query2);
			while(result2.next()) {
				if(result2.getInt("id_disturbo") == 1) {
					u.setCeliaco(true);
				}else if(result2.getInt("id_disturbo") == 2) {
					u.setCeliaco(true);
				}
			}
			utenti.add(u);
		}
		dbm.disconnect();
		return utenti;
	}
	
	/**
	 * Funzione che calcola il numero di neonati
	 * 
	 * @param utenti
	 * @return numero di neonati
	 */
	public static int getNneonati(ArrayList<Utente> utenti) {
		int contatore = 0;
		LocalDate oggi = LocalDate.now();
		for(Utente u : utenti) {
			int eta = oggi.getYear() - u.getDataNascita().getYear();
			if(eta<4) {
				contatore++;
			}
		}
		return contatore;
	}
	
	/**
	 * Funzione che calcola il numero di bambini
	 * 
	 * @param utenti
	 * @return numero di neonati
	 */
	public static int getNbambini(ArrayList<Utente> utenti) {
		int contatore = 0;
		LocalDate oggi = LocalDate.now();
		for(Utente u : utenti) {
			int eta = oggi.getYear() - u.getDataNascita().getYear();
			if(eta>=4||eta<12) {
				contatore++;
			}
		}
		return contatore;
	}
	
	/**
	 * Funzione che calcola il numero di bambini
	 * 
	 * @param utenti
	 * @return numero di neonati
	 */
	public static int getNadulti(ArrayList<Utente> utenti) {
		int contatore = 0;
		LocalDate oggi = LocalDate.now();
		for(Utente u : utenti) {
			int eta = oggi.getYear() - u.getDataNascita().getYear();
			if(eta>=12) {
				contatore++;
			}
		}
		return contatore;
	}
	
	/**
	 * Questa funzione ritorna una lista di
	 * tipologia viveri
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ArrayList<TipologiaViveri> getTipologiaViveri() throws ClassNotFoundException, SQLException{
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ArrayList<TipologiaViveri> tvList = new ArrayList<>();
		String query = "SELECT * FROM tipologia_viveri";
		ResultSet result = dbm.executeQuery(query);
		while(result.next()) {
			TipologiaViveri tv = new TipologiaViveri(result.getInt("id"),result.getString("nome_cibo"),result.getInt("fabbisogno_adulto"),result.getInt("fabbisogno_bambino"),result.getInt("fabbisogno_neonato"));
			String query2 = "SELECT id_disturbi_alimentari FROM relazione_viveri_disturbi WHERE id_tipologia_viveri = "+tv.getId();
			ResultSet result2 = dbm.executeQuery(query2);
			while(result2.next()) {
				if(result2.getInt("id_disturbi_alimentari")==1) {
					tv.setForCeliaci(true);
				}else if(result2.getInt("id_disturbi_alimentari")==2) {
					tv.setForDiabetici(true);
				}
				
			}
			tvList.add(tv);
		}
		dbm.disconnect();
		return tvList;
	}
	
	/**
	 * Questa funzione rilascia una previsione globale di quanto cibo servirebbe in totale
	 * considerando tutte le persone nei nuclei familiari che sono iscritti a dei poli
	 * che risultano attivi
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @return ArrayList di porzioni, ove ogni quantità è riferita al totale per ogni utente
	 */
	public static ArrayList<Porzione> getPrevisioneGlobale() throws ClassNotFoundException, SQLException {
		ArrayList<Porzione> ciboTotale = new ArrayList<>(); 
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		ArrayList<TipologiaViveri> tvList = getTipologiaViveri();
		ArrayList<Utente> utenti = getEveryUtente();
		
		for(TipologiaViveri tv : tvList) {
			int ncibo = 0;

			for(Utente u : utenti) {
				int ciboPersona = 0;
				//Una flag per controllare se il cibo è adatto a quella persona
				boolean ciboAdatto = true;
				
				if(u.isCeliaco() || u.isDiabetico()) {
					//L'utente ha disturbi alimentari
					if(!tv.isForCeliaci() && u.isCeliaco()) {
						ciboAdatto = false;
					}
					if(!tv.isForDiabetici() && u.isDiabetico()) {
						ciboAdatto = false;
					}
				}
				if(ciboAdatto) {
					if(u.getEta()<=3) {
						//Neonato
						ciboPersona = tv.getFabbisognoNeonato();
					}else if(u.getEta()>3&&u.getEta()<12) {
						//Bambino
						ciboPersona = tv.getFabbisognoBambino();
					}else if(u.getEta()>=12) {
						//Adulto
						ciboPersona = tv.getFabbisognoAdulto();
					}
				}else {
					ciboPersona = 0;
				}
				ncibo = ncibo + ciboPersona;
			}
			
			Porzione p = new Porzione(tv.getId(),tv.getNomeCibo(), ncibo);
			
			ciboTotale.add(p);
		}
		dbm.disconnect();
		return ciboTotale;
	}
	
	/**
	 * Funzione che fa la previsione per i lotti
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ArrayList<Porzione> getPrevisionePolo(Integer id_polo) throws ClassNotFoundException, SQLException {
		ArrayList<Porzione> ciboTotale = new ArrayList<>(); 
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		ArrayList<TipologiaViveri> tvList = getTipologiaViveri();
		ArrayList<Utente> utenti = getEveryUtentePolo(id_polo);
		
		for(TipologiaViveri tv : tvList) {
			int ncibo = 0;

			for(Utente u : utenti) {
				int ciboPersona = 0;
				//Una flag per controllare se il cibo è adatto a quella persona
				boolean ciboAdatto = true;
				
				if(u.isCeliaco() || u.isDiabetico()) {
					//L'utente ha disturbi alimentari
					if(!tv.isForCeliaci() && u.isCeliaco()) {
						ciboAdatto = false;
					}
					if(!tv.isForDiabetici() && u.isDiabetico()) {
						ciboAdatto = false;
					}
				}
				if(ciboAdatto) {
					if(u.getEta()<=3) {
						//Neonato
						ciboPersona = tv.getFabbisognoNeonato();
					}else if(u.getEta()>3&&u.getEta()<12) {
						//Bambino
						ciboPersona = tv.getFabbisognoBambino();
					}else if(u.getEta()>=12) {
						//Adulto
						ciboPersona = tv.getFabbisognoAdulto();
					}
				}else {
					ciboPersona = 0;
				}
				ncibo = ncibo + ciboPersona;
			}
			
			Porzione p = new Porzione(tv.getId(),tv.getNomeCibo(), ncibo);
			
			ciboTotale.add(p);
		}
		dbm.disconnect();
		return ciboTotale;
	}
	
	
	/**
	 * Funzione che restituisce i carichi correnti
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static ArrayList<Carico> getEveryCarichi(Integer id_magazzino) throws ClassNotFoundException, SQLException{
		ArrayList<Carico> carichi = new ArrayList<>();
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = "SELECT * FROM carico WHERE id_magazzino = "+id_magazzino;
		ResultSet result = dbm.executeQuery(query);
		
		//Inserisco tutti i carichi
		while(result.next()) {
			LocalDate data = DatabaseManager.javaDateFormatter(result.getString("data"));
			Carico c = new Carico(result.getInt("id"), result.getInt("quantita"), result.getString("stato"),data, result.getInt("id_lotto"), result.getInt("id_magazzino"));
			carichi.add(c);
		}
		dbm.disconnect();
		return carichi;
	}
	
	/**
	 * Funzione che restituisce i carichi correnti
	 * ma puoi specificare lo stato
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static ArrayList<Carico> getEveryCarichi(Integer id_magazzino, String stato) throws ClassNotFoundException, SQLException{
		ArrayList<Carico> carichi = new ArrayList<>();
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = "SELECT * FROM carico WHERE id_magazzino = "+id_magazzino + " AND stato = '"+stato+"'";
		ResultSet result = dbm.executeQuery(query);
		//Inserisco tutti i carichi
		while(result.next()) {
			LocalDate data = DatabaseManager.javaDateFormatter(result.getString("data"));
			Carico c = new Carico(result.getInt("id"), result.getInt("quantita"), result.getString("stato"),data, result.getInt("id_lotto"), result.getInt("id_magazzino"));
			carichi.add(c);
		}
		dbm.disconnect();
		return carichi;
	}
	
	
	/**
	 * Questa funzione restituisce un arraylist di carichi attualmente
	 * presenti nel magazzino
	 * 
	 * @param carichi
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static ArrayList<Carico> getCarichi(Integer id_magazzino) throws ClassNotFoundException, SQLException{
		ArrayList<Carico> carichiPieni = new ArrayList<>();
		
		ArrayList<Carico> carichiIn = getEveryCarichi(id_magazzino, "in");
		ArrayList<Carico> carichiOut = getEveryCarichi(id_magazzino, "out");
		
 		//Un bel forEach
		for(Carico in : carichiIn) {
			int quantita = 0;
			quantita = in.getQuantita();
			for(Carico out : carichiOut) {
				//Una volta trovato il carico in ingresso
				if(out.getIdLotto()==in.getIdLotto()) {
					//Se i carichi appartengono allo stesso lotto
					quantita = quantita - out.getQuantita();
				}
			}
			Carico c = new Carico(in.getId(),quantita,"in", in.getData(), in.getIdLotto() , in.getIdMagazzino());
			carichiPieni.add(c);
		}

		return carichiPieni;
	}
	
	/**
	 * Metodo che permette di mandare un carico ad un altro magazzino
	 * 
	 * @param id_carico id del carico in entrata 
	 * @param quantita
	 * @param id_magazzino_destinazione
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws DistribuzioneException
	 * @throws InvalidPrimaryKeyException
	 * @throws negativeNumberException 
	 */
	public static void sendCarico(Integer id_carico, Integer quantita, Integer id_magazzino_destinazione) throws ClassNotFoundException, SQLException, DistribuzioneException, InvalidPrimaryKeyException, negativeNumberException {
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		Boolean exists;
		//Verifico che il magazzino di destinazione esiste
		exists = dbm.checkExists("magazzino", "id ="+id_magazzino_destinazione);
		
		if(exists) {
			//Il magazzino esiste
			
			//Verifico che il carico esiste e che sia in in
			exists = dbm.checkExists("carico", "id ="+id_carico + " AND stato='in' ");
			if(exists) {
				//Il carico esiste procediamo
				//Query per prelevare le informazione del carico
				String query = "SELECT * FROM carico WHERE id = "+id_carico;
				ResultSet result = dbm.executeQuery(query);
				while(result.next()) {
					//Controllo che la quantità da mandare sia inferiore o uguale a quella rimanente
					if(quantita>getCaricoRimanente(id_carico)) {
						//Errore
						throw new negativeNumberException();
					}else {
						LocalDate dataOdierna = LocalDate.now();
						Carico out = new Carico(0,quantita,"out",dataOdierna,result.getInt("id_lotto"),result.getInt("id_magazzino"));
						Carico in = new Carico(0,quantita,"del",dataOdierna,result.getInt("id_lotto"),id_magazzino_destinazione);
						createCarico(out);
						createCarico(in);
					}					
				}
			}else {
				//Il carico non esiste
				dbm.disconnect();
				throw new DistribuzioneException();
			}
		}else {
			//Il magazzino non esiste ERRORE
			dbm.disconnect();
			throw new DistribuzioneException();
		}
		dbm.disconnect();
	}

	/**
	 * Questa funzione calcola quanto rimane all'interno di un carico di
	 * tipo "in"
	 * 
	 * @param id_carico
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InvalidPrimaryKeyException
	 */
	public static int getCaricoRimanente(Integer id_carico) throws ClassNotFoundException, SQLException, InvalidPrimaryKeyException{
		int quantita = -1;
		
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		boolean exists = dbm.checkExists("carico", "id = "+id_carico+" AND stato='in'");
		
		if(exists) {
			//Ok, esiste
			String query = "SELECT * FROM carico WHERE id = "+id_carico;
			ResultSet result = dbm.executeQuery(query);
			while(result.next()) {
				int id_magazzino = result.getInt("id_magazzino");
				int id_lotto = result.getInt("id_lotto");
				quantita = result.getInt("quantita");
				String query2 = "SELECT * FROM carico WHERE id_magazzino = " + id_magazzino + " AND id_lotto = "+id_lotto+" AND stato='out'";
				ResultSet result2 = dbm.executeQuery(query2);
				while(result2.next()) {
					quantita = quantita - result2.getInt("quantita");
				}
			}
		}else {
			dbm.disconnect();
			throw new InvalidPrimaryKeyException();
		}
		dbm.disconnect();
		return quantita;
	}
	
	/**
	 * Funzione che serve per confermare l'arrivo di una spezione
	 * 
	 * @param id_carico
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws InvalidPrimaryKeyException 
	 */
	public static void confermaSpedizione(int id_carico) throws ClassNotFoundException, SQLException, InvalidPrimaryKeyException {
		//Connessione al db
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		//Controlliamo l'esistenza del carico
		boolean exists = dbm.checkExists("carico", "id = "+id_carico+" AND stato='del'");
		if(exists) {
			//Esiste
			
			//Modifichiamo lo stato del carico
			String[] col = {"stato"}; 
			String[] val = {"in"};
			String condition = "id ="+id_carico;
			//Ok, modifichiamo ora
			dbm.editRecord("carico", col, val, condition);
		}else {
			//Non esiste
			dbm.disconnect();
			throw new InvalidPrimaryKeyException();
		}
		dbm.disconnect();
		
	}
	
	
	public static int getIdMagazzino(Account account) {
        int id_magazzino = 0;
        DatabaseManager dbm = new DatabaseManager();
        try {
            dbm.connect();
            if (account.getTipo().equals("admin")) {
                id_magazzino = 1;
            } else if (account.getTipo().equals("polo")) {
                String query = "SELECT id_magazzino FROM polo WHERE id=" + account.getIdPolo();
                ResultSet result = dbm.executeQuery(query);
                while (result.next()) {
                    id_magazzino = result.getInt("id_magazzino");
                }
            } else {
                String query = "SELECT id_magazzino FROM diocesi WHERE id=" + account.getIdDiocesi();
                ResultSet result = dbm.executeQuery(query);
                while (result.next()) {
                    id_magazzino = result.getInt("id_magazzino");
                }
                dbm.disconnect();
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
        	
            e.printStackTrace();
        }
        
        return id_magazzino;
    }
	
	/**
	 * funzione che ricerca la tipologia di cibo
	 * @param id_tipologia
	 * @return ciboritorno valore di ritorno
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static TipologiaViveri findTipologiaViveri(int id_tipologia) throws ClassNotFoundException, SQLException {
		TipologiaViveri ciboritorno=null;
		ArrayList<TipologiaViveri> viveri=new ArrayList<>() ;
		viveri=getTipologiaViveri();
		for(TipologiaViveri v:viveri) {
			if(v.getId()==id_tipologia) {
				ciboritorno=v;
			}
		}
		
		return ciboritorno;
	}
	
	/**
	 * Funzione che ritorna un arraylist di
	 * persone le quali sono adatte a mangiare
	 * quel determinato cibo nella diocesi
	 * 
	 * @param id_diocesi
	 * @param id_carico
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InvalidPrimaryKeyException
	 */
	public static ArrayList<Utente> getPersoneAdatteDiocesi(int id_diocesi, int id_tipologia_viveri) throws ClassNotFoundException, SQLException, InvalidPrimaryKeyException{
		ArrayList<Utente> listaUtenti = getEveryUtenteDiocesi(id_diocesi);
		ArrayList<Utente> utentiAdatti = new ArrayList<>();
		
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		boolean exists = dbm.checkExists("tipologia_viveri","id = "+id_tipologia_viveri);
		if(exists) {
			//tipologia esiste
			exists = dbm.checkExists("diocesi", "id = "+id_diocesi);
			if(exists) {
				//Sia tipologia, che diocesi esistono
				
				for(Utente u : listaUtenti) {
					//Verifico se il cibo è adatto per quella persona
					
					TipologiaViveri tv = findTipologiaViveri(id_tipologia_viveri);
					
					boolean ciboAdatto = true;
					
					if(u.isCeliaco() || u.isDiabetico()) {
						//L'utente ha disturbi alimentari
						if(!tv.isForCeliaci() && u.isCeliaco()) {
							ciboAdatto = false;
						}
						if(!tv.isForDiabetici() && u.isDiabetico()) {
							ciboAdatto = false;
						}
					}
					if(ciboAdatto) {
						//Se il cibo è adatto per questa persona, aggiungo la persona dentro l'arrayList
						utentiAdatti.add(u);
					}
				}
			}else {
				//diocesi non esiste
				throw new InvalidPrimaryKeyException("La diocesi non è valida");
			}
		}else {
			//tipologia non esistente
			throw new InvalidPrimaryKeyException("La tipologia non è valida");
		}
		dbm.disconnect();
		return utentiAdatti;
	}
	

	
	/**
	 * Funzione che ritorna un arraylist di
	 * persone le quali sono adatte a mangiare
	 * quel determinato cibo nella diocesi
	 * 
	 * @param id_diocesi
	 * @param id_carico
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InvalidPrimaryKeyException
	 */
	public static ArrayList<Utente> getPersoneAdatte(ArrayList<Utente> listaUtenti, int id_tipologia_viveri) throws ClassNotFoundException, SQLException, InvalidPrimaryKeyException{
		ArrayList<Utente> utentiAdatti = new ArrayList<>();
		
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		boolean exists = dbm.checkExists("tipologia_viveri","id = "+id_tipologia_viveri);
		if(exists) {
			//tipologia esiste
				for(Utente u : listaUtenti) {
					//Verifico se il cibo è adatto per quella persona
					
					TipologiaViveri tv = findTipologiaViveri(id_tipologia_viveri);
					
					boolean ciboAdatto = true;
					
					if(u.isCeliaco() || u.isDiabetico()) {
						//L'utente ha disturbi alimentari
						if(!tv.isForCeliaci() && u.isCeliaco()) {
							ciboAdatto = false;
						}
						if(!tv.isForDiabetici() && u.isDiabetico()) {
							ciboAdatto = false;
						}
					}
					if(ciboAdatto) {
						//Se il cibo è adatto per questa persona, aggiungo la persona dentro l'arrayList
						utentiAdatti.add(u);
					}
				}
		}else {
			//tipologia non esistente
			throw new InvalidPrimaryKeyException("La tipologia non è valida");
		}
		dbm.disconnect();
		return utentiAdatti;
	}
	
	public static int getQuantitaPerPersona(ArrayList<Utente> listaUtenti, int quantitaDaDistribuire) {
		int quantitaRitorno;
		quantitaRitorno = quantitaDaDistribuire/listaUtenti.size();
		return quantitaRitorno;
	}
	
	/**
	 * funzione che verifica se esistono carichi provenienti da quell'id lotto presenti in un dato magazzino
	 * 
	 * @param id_magazzino
	 * @param id_lotto
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean doesMagazzinoHave(int id_magazzino, int id_lotto)
			throws ClassNotFoundException, SQLException {
		int ritorno = 0;
		boolean check = false;
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = "SELECT * FROM carico WHERE id_magazzino=" + id_magazzino + " AND id_lotto= " + id_lotto;
		ritorno = dbm.getQueryRowCount(query);
		if (ritorno > 0) {
			check = true;
		}
		dbm.disconnect();
		return check;
	}
	
	public static int getTipologiaCarico(int id_carico) throws ClassNotFoundException, SQLException {
		int idTipologia = 0;
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = " SELECT id_tipologia "
					+  " FROM lotto l "
					+  " JOIN carico c on c.id_lotto = l.id "
					+  " WHERE c.id = "+id_carico;
		ResultSet result = dbm.executeQuery(query);
		while(result.next()) {
			idTipologia = result.getInt("id_tipologia");
		}
		dbm.disconnect();
		return idTipologia; 
	}
	
	public static int getIdLottoFromCarico(int id_carico) throws ClassNotFoundException, SQLException {
		int idLotto = 0;
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = " SELECT id_lotto "
					+  " FROM carico c "
					+  " WHERE c.id = "+id_carico;
		ResultSet result = dbm.executeQuery(query);
		while(result.next()) {
			idLotto = result.getInt("id_lotto");
		}
		dbm.disconnect();
		return idLotto; 
	}
	
	public static int getIdMagazzinoDiocesi(int id_diocesi) throws SQLException, ClassNotFoundException {
		//Connessione al Database
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		int id_magazzino=0;
        String query = "SELECT id_magazzino FROM diocesi WHERE id=" + id_diocesi;
        ResultSet resultIdMagazzino = dbm.executeQuery(query);
        while (resultIdMagazzino.next()) {
            id_magazzino = resultIdMagazzino.getInt("id_magazzino");
        }
        dbm.disconnect();
        return id_magazzino;
	}
	
	public static int getIdMagazzinoPolo(int id_polo) throws ClassNotFoundException, SQLException {
		//Connessione al Database
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		int id_magazzino=0;
        String query = "SELECT id_magazzino FROM polo WHERE id=" + id_polo;
        ResultSet resultIdMagazzino = dbm.executeQuery(query);
        while (resultIdMagazzino.next()) {
            id_magazzino = resultIdMagazzino.getInt("id_magazzino");
        }
        dbm.disconnect();
        return id_magazzino;
	}
	
	public static void distribuisciDiocesi(int id_diocesi, int id_carico) throws ClassNotFoundException, SQLException, InvalidPrimaryKeyException, DistribuzioneException, negativeNumberException {
		//TODO Verifico che questa diocesi non abbia già ricevuto questo carico
		
		ArrayList<Utente> utentiThisDiocesi = getEveryUtenteDiocesi(id_diocesi);
		int tipologiaCarico = getTipologiaCarico(id_carico);
		utentiThisDiocesi = getPersoneAdatte(utentiThisDiocesi,tipologiaCarico);
		
		//Prendo id magazzino di questa diocesi
		int idMagazzinoThisDiocesi = getIdMagazzinoDiocesi(id_diocesi);
		
		//Prendo id lotto del carico
		int idLotto = getIdLottoFromCarico(id_carico);
		
		//Prelevo la quantità disponibile da distribuire nel carico
		int quantitaRimasta = getCaricoRimanente(id_carico);
		
		if(quantitaRimasta==0) {
			throw new DistribuzioneException("Carico vuoto!");
		}
		
		//Connessione al Database
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		//Seleziono tutte le diocesi
		String queryDiocesi = "SELECT * FROM diocesi";
		ResultSet resultDiocesi = dbm.executeQuery(queryDiocesi);
		
		//Lista di tutte le persone
		ArrayList<Utente> listaPersone = new ArrayList<>();
		
		//Scorro tutte le diocesi per comporre la lista di persone
		while(resultDiocesi.next()) {
			int idMagazzino = getIdMagazzinoDiocesi(resultDiocesi.getInt("id"));
			//Verifico che il magazzino non contenga già quello specifico lotto
			if(!doesMagazzinoHave(idMagazzino, idLotto)) {
				//Prelevo tutti gli utenti di una diocesi
				ArrayList<Utente> utentiDiocesi = getEveryUtenteDiocesi(resultDiocesi.getInt("id"));
				//Seleziono di quella diocesi solo quelli adatti
				ArrayList<Utente> utentiDiocesiAdatti = getPersoneAdatte(utentiDiocesi,tipologiaCarico);
				//Lista totali di persone adatte
				listaPersone.addAll(utentiDiocesiAdatti);
			}
		}
		dbm.disconnect();
		//Quantità di cibo da dare per persona
		int quantitaPersona = getQuantitaPerPersona(listaPersone,quantitaRimasta);
		System.out.println("quantita Persona"+quantitaPersona);
		
		int quantitaDaMandare = quantitaPersona*utentiThisDiocesi.size();
		
		sendCarico(id_carico,quantitaDaMandare,idMagazzinoThisDiocesi);
	
	}
	
	/**
	 * Questa ritorna una lsita di carichi i quali non sono ancora stati inviati alla
	 * diocesi in questione. 
	 * 
	 * @param id_diocesi
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ArrayList<Carico> getCarichiForDiocesi(int id_diocesi) throws ClassNotFoundException, SQLException {
		//Prelevo la lista dei carichi presenti dentro il magazzino UE
		ArrayList<Carico> carichiList = getCarichi(1);
		
		//Un arraylist di carichi ancora da mandare
		ArrayList<Carico> carichiNonInviati = new ArrayList<>();
		
		//Prendo id magazzino del diocesi
		int idMagDio = getIdMagazzinoDiocesi(id_diocesi);
		
		//Scorro tutit i carichi, e scarto quelli che sono stati già mandati
		for(Carico c : carichiList) {
			if(doesMagazzinoHave(idMagDio,c.getIdLotto())) {
				System.out.println("Distributore:getCarichiForDiocesi> Il magazzino "+ idMagDio + " possiede già il carico!");
			}else {
				carichiNonInviati.add(c);
			}
		}
		return carichiNonInviati;
	}
	
	public static void distribuisciPolo(int id_polo, int id_carico) throws ClassNotFoundException, SQLException, InvalidPrimaryKeyException, DistribuzioneException, negativeNumberException {
		//TODO Verifico che questa diocesi non abbia già ricevuto questo carico
		
		ArrayList<Utente> utentiThisPolo = getEveryUtentePolo(id_polo);
		int tipologiaCarico = getTipologiaCarico(id_carico);
		utentiThisPolo = getPersoneAdatte(utentiThisPolo,tipologiaCarico);
		
		//Prendo id magazzino di questa diocesi
		int idMagazzinoThisDiocesi = getIdMagazzinoPolo(id_polo);
		
		//Prendo id lotto del carico
		int idLotto = getIdLottoFromCarico(id_carico);
		
		//Prelevo la quantità disponibile da distribuire nel carico
		int quantitaRimasta = getCaricoRimanente(id_carico);
		System.out.println("quantita Rimasta"+quantitaRimasta);
		
		if(quantitaRimasta==0) {
			throw new DistribuzioneException("Carico vuoto!");
		}
		
		//Connessione al Database
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		//Seleziono tutte le diocesi
		String queryDiocesi="SELECT id_diocesi FROM polo WHERE id="+id_polo;
		
		
		ResultSet resultDiocesi = dbm.executeQuery(queryDiocesi);
		int id_diocesi=0;
		while(resultDiocesi.next()) {
			id_diocesi=resultDiocesi.getInt("id_diocesi");
		}
		
		String queryPolo = "SELECT * FROM polo WHERE id_diocesi="+id_diocesi;
		
		ResultSet resultPolo = dbm.executeQuery(queryPolo);
		
		//Lista di tutte le persone
		ArrayList<Utente> listaPersone = new ArrayList<>();
		
		//Scorro tutte le diocesi per comporre la lista di persone
		while(resultPolo.next()) {
			int idMagazzino = getIdMagazzinoPolo(resultPolo.getInt("id"));
			//Verifico che il magazzino non contenga già quello specifico lotto
			if(!doesMagazzinoHave(idMagazzino, idLotto)) {
				//Prelevo tutti gli utenti di una diocesi
				ArrayList<Utente> utentiPolo = getEveryUtentePolo(resultPolo.getInt("id"));
				
				//Seleziono di quella diocesi solo quelli adatti
				ArrayList<Utente> utentiPoloAdatti = getPersoneAdatte(utentiPolo,tipologiaCarico);
				//Lista totali di persone adatte
				listaPersone.addAll(utentiPoloAdatti);
				System.out.println("Utenti polo"+utentiPolo);
				System.out.println("utenti Polo Adatti"+utentiPoloAdatti);
			}
		}
		System.out.println("lista persone size:"+listaPersone.size());
		System.out.println("lista persone:"+listaPersone);
		dbm.disconnect();
		//Quantità di cibo da dare per persona
		int quantitaPersona = getQuantitaPerPersona(listaPersone,quantitaRimasta);
		
		int quantitaDaMandare = quantitaPersona*utentiThisPolo.size();
		
		sendCarico(id_carico,quantitaDaMandare,idMagazzinoThisDiocesi);
	
	}
	
	public static ArrayList<Carico> getCarichiForPoli(int id_polo) throws ClassNotFoundException, SQLException {
		// Prelevo la lista dei carichi presenti dentro il magazzino diocesi
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = "SELECT id_diocesi FROM polo where id = " + id_polo;
		ResultSet result = dbm.executeQuery(query);
		int idDiocesi = 0;
		while (result.next()) {
			idDiocesi = result.getInt("id_diocesi");
		}

		int idMagDio = getIdMagazzinoDiocesi(idDiocesi);

		ArrayList<Carico> carichiList = getCarichi(idMagDio);

		// Un arraylist di carichi ancora da mandare
		ArrayList<Carico> carichiNonInviati = new ArrayList<>();

		// Prendo id magazzino del diocesi
		int idMagPo = getIdMagazzinoPolo(id_polo);

		// Scorro tutit i carichi, e scarto quelli che sono stati già mandati
		for (Carico c : carichiList) {
			if (doesMagazzinoHave(idMagPo, c.getIdLotto())) {
				System.out.println(
						"Distributore:getCarichiForDiocesi> Il magazzino " + idMagPo + " possiede già il carico!");
			} else {
				carichiNonInviati.add(c);
			}
		}
		return carichiNonInviati;
	}
	
	/**
	 * Funzione che distribuisce alle famiglie
	 * 
	 * @param id_polo
	 * @param id_carico
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InvalidPrimaryKeyException
	 * @throws DistribuzioneException
	 * @throws negativeNumberException
	 */
	public static void distribuisciFamiglia(Integer id_nucleo, int id_carico) throws ClassNotFoundException, SQLException, InvalidPrimaryKeyException, DistribuzioneException, negativeNumberException {
		//TODO Verificare che la famiglia non abbia già ricevuto roba
		
		//Prelevo tutti i membri della famiglia
		ArrayList<Utente> utentiThisFamiglia = getEveryUtenteFamiglia(id_nucleo);
		
		//Prelevo la tipologia di carico che mi sta arrivando
		int tipologiaCarico = getTipologiaCarico(id_carico);
		//Seleziono solo le persone adatte alla consegna in questa famiglia
		utentiThisFamiglia = getPersoneAdatte(utentiThisFamiglia,tipologiaCarico);
		
		//Prendo id lotto del carico
		Integer idLotto = getIdLottoFromCarico(id_carico);
		
		//Prelevo la quantità disponibile da distribuire nel carico
		int quantitaRimasta = getCaricoRimanente(id_carico);
		System.out.println("Distributore.distribuisciFamiglia> Quantità rimasta:"+quantitaRimasta);
		
		if(quantitaRimasta<=0) {
			throw new DistribuzioneException("Carico vuoto!");
		}
		
		//Connessione al Database
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		
		//Seleziono il nome del polo
		String queryPolo="SELECT id_polo FROM nucleo_familiare WHERE id="+id_nucleo;
		ResultSet resultPolo = dbm.executeQuery(queryPolo);
		
		int id_polo=0;
		while(resultPolo.next()) {
			id_polo=resultPolo.getInt("id_polo");
		}
		
		String queryFamiglia = "SELECT * FROM nucleo_familiare WHERE id_POLO="+id_polo;
		
		ResultSet resultFamiglia = dbm.executeQuery(queryFamiglia);
		
		//Lista di tutte le persone
		ArrayList<Utente> listaPersone = new ArrayList<>();
		
		//Prelevo l'id del magazzino del polo
		Integer idMagPo = getIdMagazzinoPolo(id_polo);
		
		
		//Scorro tutte le famiglie per comporre la lista di persone
		while(resultFamiglia.next()) {
			int id_famiglia=resultFamiglia.getInt("id");
			//Verifico che quella famiglia non abbia già prelevato del cibo
			String queryCheckFamiglia = "SELECT * FROM carico WHERE id_lotto = "+ idLotto +" AND id_famiglia =" + id_famiglia ;
			int queryCount = dbm.getQueryRowCount(queryCheckFamiglia);
			if(queryCount == 0) {
				//La famiglia non ha ancora preso il lotto
				
				//Prelevo tutti i membri della famiglia
				ArrayList<Utente> utentiFamiglia = getEveryUtenteFamiglia(resultFamiglia.getInt("id"));
				
				//Seleziono di tutti i membri, solo quelli adatti
				ArrayList<Utente> utentiFamigliaAdatti = getPersoneAdatte(utentiFamiglia,tipologiaCarico);
				
				System.out.println("Distributore.distribuisciFamiglia> Utenti famiglia adatti:"+utentiFamigliaAdatti);
				
				listaPersone.addAll(utentiFamigliaAdatti);
			}
			
		}
		System.out.println("Distributore.distribuisciFamiglia> lista persone size:"+listaPersone.size());
		System.out.println("Distributore.distribuisciFamiglia> lista persone:"+listaPersone);
		
		//Quantità di cibo da dare per persona
		int quantitaPersona = getQuantitaPerPersona(listaPersone,quantitaRimasta);
		
		Integer quantitaDaMandare = quantitaPersona*utentiThisFamiglia.size();
		
		if(quantitaDaMandare > quantitaRimasta) {
			throw new negativeNumberException("Qualcosa è andato storto");
		}
		
		//Creo la data di oggi
		LocalDate oggi = LocalDate.now();
		String dateString = DatabaseManager.dataDBMSFormatter(oggi);
		
		//Genero il carico in uscita
		String[] col = {"id_lotto","quantita","id_magazzino","stato","data","id_famiglia"};
		String[] val = {idLotto.toString(),quantitaDaMandare.toString(),idMagPo.toString(),"out",dateString, id_nucleo.toString()};
		
		dbm.insertAutoIncrementRecord("carico", col, val);
		
		dbm.disconnect();
	}
	
	/**
	 * Funzione che restituisce tutti i membri di una famiglia
	 * 
	 * @param id_nucleo
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<Utente> getEveryUtenteFamiglia(int id_nucleo) throws SQLException, ClassNotFoundException {
		ArrayList<Utente> utenti = new ArrayList<>();
		//Connessione al db
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = " SELECT u.*"
				     + " FROM utente u"
				     + " JOIN nucleo_familiare nf ON u.id_nucleo = nf.id"
				     + " WHERE nf.id = " + id_nucleo;
		ResultSet result = dbm.executeQuery(query);
		while(result.next()) {
			LocalDate data = DatabaseManager.javaDateFormatter( result.getString("data_di_nascita") );
			Utente u = new Utente(result.getString("cf"),result.getString("nome"),result.getString("cognome"), result.getString("sesso"), data, result.getString("residenza"));
			String query2 = "SELECT id_disturbo FROM disturbi_utente WHERE cf = '"+u.getCodiceFiscale()+"'";
			ResultSet result2 = dbm.executeQuery(query2);
			while(result2.next()) {
				if(result2.getInt("id_disturbo") == 1) {
					u.setCeliaco(true);
				}else if(result2.getInt("id_disturbo") == 2) {
					u.setCeliaco(true);
				}
			}
			
			utenti.add(u);
		}
		dbm.disconnect();
		return utenti;
		
	}
	
	
	public static ArrayList<Carico> getCarichiForFamiglia(int id_famiglia) throws ClassNotFoundException, SQLException {
		// Prelevo la lista dei carichi presenti dentro il magazzino diocesi
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = "SELECT id_polo FROM nucleo_familiare where id = " + id_famiglia;
		ResultSet result = dbm.executeQuery(query);
		int idPolo = 0;
		while (result.next()) {
			idPolo = result.getInt("id_polo");
		}

		int idMagPo = getIdMagazzinoPolo(idPolo);

		ArrayList<Carico> carichiList = getCarichi(idMagPo);

		// Un arraylist di carichi ancora da mandare
		ArrayList<Carico> carichiNonInviati = new ArrayList<>();

		// Scorro tutit i carichi, e scarto quelli che sono stati già mandati
		for (Carico c : carichiList) {
			//Effettuo la query
			String queryCheckFamiglia = "SELECT * FROM carico WHERE id_lotto = "+ c.getIdLotto() +" AND id_famiglia =" + id_famiglia ;
			int queryCount = dbm.getQueryRowCount(queryCheckFamiglia);
			
			if(queryCount>0) {
				System.out.println("Distributore.getCarichiForFamiglia> La famiglia possiede già quel cibo!");
			}else {
				carichiNonInviati.add(c);
			}
			
		}
		return carichiNonInviati;
	}
	
	
	
}
