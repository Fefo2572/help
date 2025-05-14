package help;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Polo {
	private Integer id;
	private String nome;
	private String citta;
	private Integer id_diocesi;
	private String stato;
	
	//costruttore parametrico per diocesi
		public Polo(Integer id,String nome,String citta,String stato) {
			this.id=id;
			this.nome=nome;
			this.citta=citta;
			this.stato=stato;
		}
	
	//costruttore parametrico per admin
	public Polo(Integer id,String nome,String citta,Integer id_diocesi,String stato) {
		this.id=id;
		this.nome=nome;
		this.citta=citta;
		this.id_diocesi=id_diocesi;
		this.stato=stato;
	}
	
	//setter
	public void setId(Integer id) {
		this.id=id;
	}
	
	public void setNome(String nome) {
		this.nome=nome;
	}
	
	public void setCitta(String citta) {
		this.citta=citta;
	}
	
	public void setId_Diocesi(Integer id_diocesi) {
		this.id_diocesi=id_diocesi;
	}
	
	public void setStato(String stato) {
		this.stato=stato;
	}

	//getter
	public Integer getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCitta() {
		return citta;
	}
	
	public Integer getId_Diocesi() {
		return id_diocesi;
	}
	       
	public String getStato() {
		return stato;
	}
	
	public Polo() {
		// Costruttore di polo
		//id = null;
	}

	// Costruttore parametrico
	public Polo(int id) {
		//this.id = id;
	}
	

	public static int addPolo(String nome, String citta, int id_diocesi) throws SQLException, ClassNotFoundException {
		// Apro la connessione al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();

		// Creo la variabile id che dovrà essere restituita
		int id = 0;

		// Vettore di stringhe con le colonne da inserire
		String[] col = { "nome", "citta", "id_diocesi", "id_magazzino" };

		// Vettore di stringhe con i valori da inserire
		String[] val = { nome, citta, Integer.toString(id_diocesi), Integer.toString(PDAManager.addMagazzino(100)) };

		id = dbm.insertAutoIncrementRecord("polo", col, val);

		return id;
	}

	/**
	 * Questo metodo si occupa della rimozione di un polo dato in input.
	 * 
	 * @param id id del polo da rimuovere
	 */
	public static void removePolo(int id) throws ClassNotFoundException, SQLException {
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ResultSet result = dbm.executeQuery("SELECT id_magazzino FROM polo WHERE id=" + id);
		String id_magazzino = Integer.toString(result.getInt("id_magazzino"));
		dbm.deleteRecord("magazzino", id_magazzino);
	}

	/**
	 * Crea un nuovo nucleo familiare associato al polo.
	 * 
	 * @param nome il nome del nucleo familiare.
	 * @param isee l'ISEE del nucleo familiare.
	 * @return l'id del nucleo familiare creato.
	 * @throws ClassNotFoundException se la classe del driver del database non viene
	 *                                trovata.
	 * @throws SQLException           se si verifica un errore SQL.
	 */
	public static int createNFamiliare(String nome, String isee, Integer id_polo) throws ClassNotFoundException, SQLException {
		int idNucleo = 0;
		// Connessione al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		try {
			// Inserisci i dati del nucleo familiare nella tabella "nucleo_familiare"
			String[] col = { "nome", "isee" , "id_polo"};
			String[] val = { nome, isee , id_polo.toString()};
			idNucleo = dbm.insertAutoIncrementRecord("nucleo_familiare", col, val);
		} finally {
			dbm.disconnect();
		}
		return idNucleo;
	}

	/**
	 * Crea un nuovo utente associato al polo.
	 * 
	 * @param nome          il nome dell'utente.
	 * @param cognome       il cognome dell'utente.
	 * @param dataNascita   la data di nascita dell'utente.
	 * @param codiceFiscale il codice fiscale dell'utente.
	 * @param idNucleo      l'id del nucleo familiare a cui l'utente appartiene.
	 * @throws ClassNotFoundException se la classe del driver del database non viene trovata.
	 * @throws SQLException se si verifica un errore SQL.
	 * @throws PoloException se si inserisce un utente già esistente
	 * @throws FutureDateException 
	 */
	public static Utente createUtente(String codiceFiscale, String nome, String cognome, String sesso, String residenza,
			LocalDate dataNascita, String idNucleo) throws ClassNotFoundException, SQLException, PoloException, FutureDateException {
		//Variabile di ritorno
		Utente utente = null;
		checkFutureDate(dataNascita);
		// Connessione al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();

		// Query di controllo, se esiste già l'utente da errore
		String query = "SELECT * FROM utente WHERE cf = '" + codiceFiscale + "'";
		int rowCount = dbm.getQueryRowCount(query);
		if (rowCount > 0) {
			// Errore
			System.out.println("Polo.createUtente: Errore, utente già registrato");
			dbm.disconnect();
			throw new PoloException("Utente già esistente");
		}else {
			//Tutto ok, il polo esiste
			try {
				// Inserisci i dati dell'utente nella tabella "utente"
				String[] col = { "nome", "cognome", "data_di_nascita", "cf", "id_nucleo", "residenza", "sesso"};
				// data formattata nel vettore val
				String data = DatabaseManager.dataDBMSFormatter(dataNascita);
				String[] val = { nome, cognome, data,codiceFiscale,String.valueOf(idNucleo),residenza,sesso};
				dbm.insertRecord("utente", col, val);
				utente = new Utente(codiceFiscale,nome,cognome,sesso,dataNascita,residenza);
			} finally {
				dbm.disconnect();
			}
		}
		return utente;
	}
	
	
	//update utente
	//modifica di un valore nel db nella tabella polo
	public static Utente updateUtente(String cf,String nome,String cognome,String sesso,LocalDate data,String residenza,String id_nucleo) throws ClassNotFoundException, SQLException, FutureDateException{
		 	DatabaseManager dbm = new DatabaseManager();
		 	Utente utente = null;
		 	checkFutureDate(data);
		 	dbm.connect();
		 	
		 	Utente u=new Utente(cf,nome,cognome,sesso,data,residenza);
	       
	        String[] colonne= {"cf","nome","cognome","sesso","data_di_nascita","residenza","id_nucleo"};
	        String[] values= {cf,nome,cognome,sesso,data.toString(),residenza,id_nucleo};
	        String condition = "cf = '"+cf+"'";
	        dbm.editRecord("utente",colonne,values,condition);
	        dbm.disconnect();
	       
	        return utente;
	}
	
	
	
	
	
	
	
	public static void deleteUtente(String cf) throws ClassNotFoundException, SQLException, PoloException {
		//Connessione al db
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query = "SELECT * FROM utente WHERE cf = '" + cf + "'";
		int rowCount = dbm.getQueryRowCount(query);
		if(rowCount>0) {
			//Si procede con l'eliminazione
			dbm.deleteRecord("utente", "cf = '"+cf+"'");
			dbm.disconnect();
		}else {
			//Utente inesistente
			dbm.disconnect();
			throw new PoloException("CF inesistente");
		}	
	}
	
	public static ArrayList<Utente> getUtenti(String id_nucleo) throws ClassNotFoundException, SQLException {
		ArrayList <Utente> lutenti = new ArrayList<>();
		//Connetto al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ResultSet result = dbm.executeQuery("SELECT * FROM utente WHERE id_nucleo = '"+id_nucleo+"'");
		while(result.next()) {
			LocalDate data = DatabaseManager.javaDateFormatter( result.getString("data_di_nascita") );
			Utente u = new Utente(result.getString("cf"),result.getString("nome"),result.getString("cognome"), result.getString("sesso"), data, result.getString("residenza"));
			lutenti.add(u);
		}
		return lutenti;
	}
	
	public static ArrayList<NucleoFamiliare> getNuclei(String id_polo) throws ClassNotFoundException, SQLException {
		ArrayList <NucleoFamiliare> lnuclei = new ArrayList<>();
		//Connetto al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ResultSet result = dbm.executeQuery("SELECT * FROM nucleo_familiare WHERE id_polo = '"+id_polo+"'");
		while(result.next()) {
			NucleoFamiliare u = new NucleoFamiliare(result.getInt("id"),result.getString("nome"),result.getFloat("isee"), result.getInt("id_polo"));
			lnuclei.add(u);
		}
		return lnuclei;
	}
	
	//modifica nucleo familiare in visualizzaNucleo
	//eliminazione di un record dal db Polo
	public static void deleteFromNucleo(Integer id) throws SQLException, ClassNotFoundException {
	     DatabaseManager dbm = new DatabaseManager();
	     dbm.connect();
	     String condition = "id = "+id;
	     dbm.deleteRecord("nucleo",condition);
	}
	
	//modifica di un valore nel db nella tabella nucleo_familiare
	public static void updateNucleoFamiliare(Integer id,String nome,Float isee) throws ClassNotFoundException, SQLException{
		 DatabaseManager dbm = new DatabaseManager();
	        dbm.connect();
	        String[] colonne= {"nome","isee"};
	        String[] values= {nome,isee.toString()};
	        String condition = "id = "+id;
	        dbm.editRecord("nucleo_familiare",colonne,values,condition);
	}
	
	//eliminazione di un record dal db nucleo_familiare
	public static void deleteFromPolo(Integer id) throws SQLException, ClassNotFoundException {
	     DatabaseManager dbm = new DatabaseManager();
	     dbm.connect();
	     String condition = "id = "+id;
	     dbm.deleteRecord("nucleo_familiare",condition);
	}
	
	
	
	
	
	
	
	/**
	 * Classe che inserisce disturbi
	 * @param cf Codice Fiscale dell'utente con il disturbo
	 * @param id_disturbo
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws PoloException 
	 */
	public static void addDisturbo(String cf, String id_disturbo) throws ClassNotFoundException, SQLException, PoloException {
		//Controlli extra per esser sicuri
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		int queryRowCount = dbm.getQueryRowCount("SELECT * FROM utente WHERE cf = '"+cf+"'");
		if(queryRowCount>0) {
			//Ok
			queryRowCount = 0;
			//Controlliamo se esiste il disturbo
			queryRowCount = dbm.getQueryRowCount("SELECT * FROM disturbi_alimentari WHERE id = '"+id_disturbo+"'");
			if(queryRowCount>0) {
				//Si procede con un insert
				
				String[] col = {"cf","id_disturbo"};
				String[] val = {cf,id_disturbo};
 
				dbm.insertRecord("disturbi_utente",col,val);
							
			}else {
				//TODO Errore
				dbm.disconnect();
				throw new PoloException("Disturbo inesistente");
			}
			
		}else {
			//TODO errore
			dbm.disconnect();
			throw new PoloException("Utente inesistente");
		}
		dbm.disconnect();
	}
	
	/**
	 * Funzione per prelevare i disturbi
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws PoloException 
	 * 
	 */
	
	public static String getDisturbiString(String cf) throws ClassNotFoundException, SQLException, PoloException {
		String ritorno = "";
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		//Verifica esistenza
		int queryRowCount = dbm.getQueryRowCount("SELECT * FROM utente WHERE cf = '"+cf+"'");
		if(queryRowCount>0) {
			//Ok
			String query = "SELECT id_disturbo FROM disturbi_utente WHERE cf = '"+cf+"'";
			queryRowCount = dbm.getQueryRowCount(query);
			if(queryRowCount>0) {
				//Ci sono dei disturbi
				System.out.println("Polo.getDisturbiString: Disturbi trovati per "+cf);
				ResultSet result = dbm.executeQuery(query);
				while(result.next()) {
					if (result.getInt("id_disturbo")==1) {
						ritorno += "C ";
					}else if(result.getInt("id_disturbo")==2) {
						ritorno += "D ";
					}
				}
			}else {
				System.out.println("Polo.getDisturbiString: Nessun disturbo trovato per "+cf);
			}
			
		}else {
			//Errore
			dbm.disconnect();
			throw new PoloException("Utente inesistente!");
		}
		
	
		dbm.disconnect();
		return ritorno;
	}
	
	//funzione per gestione nuclei familiari in menu Admin
	public static ArrayList<NucleoFamiliare> getAllNuclei() throws ClassNotFoundException, SQLException {
		ArrayList <NucleoFamiliare> lnuclei = new ArrayList<>();
		//Connetto al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ResultSet result = dbm.executeQuery("SELECT * FROM nucleo_familiare ");
		while(result.next()) {
			NucleoFamiliare u = new NucleoFamiliare(result.getInt("id"),result.getString("nome"),result.getFloat("isee"), result.getInt("id_polo"));
			lnuclei.add(u);
		}
		return lnuclei;
	}
	
    /**
     * Verifica se una data è nel futuro.
     *
     * @param data la data da controllare
     * @throws FutureDateException se la data è nel futuro
     */
    private static void checkFutureDate(LocalDate data) throws FutureDateException {
        LocalDate oggi = LocalDate.now();

        if (data.isAfter(oggi)) {
            throw new FutureDateException("La data è nel futuro!");
        }
    }
    
    //gestione tabella errori classe GestioneErrori
    //creazione di un nuovo errore nel db
    public static int createErrore(String descrizione,Integer idpolo) throws ClassNotFoundException, SQLException {
		int id=0;
		// Connessione al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		try {
			//inserimento di un nuovo errore nella tabella errore
			String[] col = {"descrizione","id_polo"};
			String[] val = {descrizione,idpolo.toString()};
			id= dbm.insertAutoIncrementRecord("errore", col, val);
		} finally {
			dbm.disconnect();
		}
		return id;
	}
    
    //visualizzazione dei valori presenti nel db 
    public static ArrayList<Errore> getErrori(Integer id_polo) throws ClassNotFoundException, SQLException {
    	ArrayList<Errore> lerrori = new ArrayList<>();
		//Connetto al DB
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		ResultSet result = dbm.executeQuery("SELECT * FROM errore WHERE id_polo = '"+id_polo.toString()+"'");
		while(result.next()) {
			Errore u = new Errore(result.getInt("id"),result.getString("descrizione"));
			lerrori.add(u);
		}
		return lerrori;
	}
    
  //eliminazione di un record dal db
	public static void deleteFromErrore(Integer id) throws SQLException, ClassNotFoundException {
	     DatabaseManager dbm = new DatabaseManager();
	     dbm.connect();
	     String condition = "id = "+id;
	     dbm.deleteRecord("errore",condition);
	}
	
	public static String getStato(Integer id_polo)throws SQLException, ClassNotFoundException  {
		String ritorno="";
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String query="SELECT stato FROM polo WHERE id="+id_polo;
		ResultSet result=dbm.executeQuery(query);
		while(result.next()) {
			ritorno=result.getString("stato");
		}
		return ritorno;
	}
    
	public static void setStato(Integer id_polo,String stato)throws SQLException, ClassNotFoundException  {
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		String[] col = {"stato"};
		String[] val = {stato};
		String condition="id="+id_polo;
		dbm.editRecord("polo", col, val,condition);
	}
	
	
	  //visualizzazione valori nel db
		public static ArrayList<Polo> getlistaPoliFromDiocesi(int id_diocesi) throws SQLException, ClassNotFoundException {
			ArrayList <Polo> lpoli = new ArrayList<>();
			//Scrivo la query che mi da le informazioni che mi servono
			String query = "SELECT * FROM polo WHERE id_diocesi="+id_diocesi;
			DatabaseManager dbm = new DatabaseManager();
			dbm.connect();
			ResultSet result = dbm.executeQuery(query);
			while(result.next()) {
				//Creo un oggetto tipologia viveri
				Polo d = new Polo(result.getInt("id"),result.getString("nome"),result.getString("citta"),result.getString("stato"));
				//Aggiungo il nuovo oggetto all'array
				lpoli.add(d);
			}
			dbm.disconnect();
			return lpoli;
		}
    
    
}
