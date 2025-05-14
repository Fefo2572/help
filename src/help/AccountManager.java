package help;

/**
 * Questa classe permette di gestire ciò che concerne
 * gli account
 * 
 * @version 1.3
 * @author Federico Ennio Ambrogi
 */

import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AccountManager {
	//Attribbuto
	Account account;

	public AccountManager(String email, String password, String nome, String cognome) {
		account = new Account(email,password,nome,cognome,0,0,0,"act",0);
	}
	
	
	public static Account setCredentials(String email, String password)
			throws SQLException, ClassNotFoundException, AccountException {

		Account accountLogin = new Account();
		DatabaseManager dbm = new DatabaseManager();

		// Si effettua l'hash della password
		password = hashPassword(password);
		// Connessione al DB
		dbm.connect();

		// Variabile dove mettere il risultato della query
		ResultSet result;

		System.out.println("Email-> " + email + "Password-> " + password);

		String query = "SELECT * FROM account WHERE email = '" + email + "' AND password = '" + password + "'";

		result = dbm.executeQuery(query);

		int righe = dbm.getQueryRowCount(query);
		// Se la query è nulla, autamaticamente intuiamo che le credenziali sono
		// sbagliate
		if (righe == 0) {
			throw new AccountException("La tua mail o password non sono corrette!");
		} else {
			while (result.next()) {
				System.out.println(result.getString("nome"));
				// Memorizzo il tutto dentro la classe Utente
				String nome = result.getString("nome");
				String cognome = result.getString("cognome");
				int id_azienda = result.getInt("id_azienda");
				int id_polo = result.getInt("id_polo");
				int id_diocesi = result.getInt("id_diocesi");
				String stato = result.getString("stato");
				int id_magazzino = result.getInt("id_magazzino");
				accountLogin = new Account(email, password, nome, cognome, id_azienda, id_polo, id_diocesi, stato,
						id_magazzino);
			}

			if (accountLogin.getIdAzienda() != 0 && accountLogin.getIdPolo() == 0 && accountLogin.getIdDiocesi() == 0
					&& accountLogin.getIdMagazzino() == 0) {
				// id_azienda è diverso da zero e tutte le altre variabili sono zero
				accountLogin.setTipo("azienda");
			} else if (accountLogin.getIdAzienda() == 0 && accountLogin.getIdPolo() != 0
					&& accountLogin.getIdDiocesi() == 0 && accountLogin.getIdMagazzino() == 0) {
				// id_polo è diverso da zero e tutte le altre variabili sono zero
				accountLogin.setTipo("polo");
			} else if (accountLogin.getIdAzienda() == 0 && accountLogin.getIdPolo() == 0
					&& accountLogin.getIdDiocesi() != 0 && accountLogin.getIdMagazzino() == 0) {
				// id_diocesi è diverso da zero e tutte le altre variabili sono zero
				accountLogin.setTipo("diocesi");
			} else if (accountLogin.getIdAzienda() == 0 && accountLogin.getIdPolo() == 0
					&& accountLogin.getIdDiocesi() == 0 && accountLogin.getIdMagazzino() != 0) {
				// id_magazzino è diverso da zero e tutte le altre variabili sono zero
				accountLogin.setTipo("admin");
			} else {
				// Almeno una variabile ha un valore diverso da zero
				throw new AccountException("Account corrotto, contatta un admin!");
			}
		}
		// Disconnessione
		dbm.disconnect();
		return accountLogin;
	}

	public static void resetPassword(String nuovaPassword1, String nuovaPassword2, String email)
			throws SQLException, AccountException, ClassNotFoundException {
		// Controllo se le password inserite sono diverse

		nuovaPassword1 = hashPassword(nuovaPassword1);
		nuovaPassword2 = hashPassword(nuovaPassword2);

		System.out.println(nuovaPassword1 + " e " + nuovaPassword2);
		if (!nuovaPassword1.equals(nuovaPassword2)) {
			// Errore, le password non possono essere uguali
			throw new AccountException("Le password sono diverse!");
		} else {
			// Le password sono diverse
			// Creo un nuovo DatabaseManager
			DatabaseManager dbm = new DatabaseManager();

			// Mi connetto al DB
			dbm.connect();

			// Modifico la password nel DB

			// Elementi che servono per effettuare il modifica record

			String[] colonne = { "password" };
			String[] contenuto = { nuovaPassword1 };

			String condition="email='"+email+"'";

			// La modifica del record
			dbm.editRecord("account", colonne,contenuto, condition);
			System.out.println("AccountManager: Password modificata correttamente");
			dbm.disconnect();
		}
	}

	/*
	 * Questa funzione controlla che la mail sia presente nel DB
	 */

	public static boolean emailChecker(String email) throws ClassNotFoundException, SQLException {

		// Creo la variabile da ritornare
		boolean returnValue = false;

		// Crea un nuovo DbManager
		DatabaseManager dbm = new DatabaseManager();

		// Connessione al db
		dbm.connect();
		// Stringa contenente la query
		String query = "SELECT email FROM account WHERE email = '" + email + "'";
		// Variabili per controllare il numero di righe che risulta la query
		int nrighe = 0;
		nrighe = dbm.getQueryRowCount(query);
		if (nrighe == 1) {
			// La query da risultato -> la mail esiste
			System.out.println("AccountManager.emailChecker(): email trovata");
			returnValue = true;
		} else {
			// La query non da risultato -> la mail non esiste
			returnValue = false;
			System.out.println("AccountManager.emailChecker(): email non trovata");
		}
		// Chiudo la connessione
		dbm.disconnect();
		// Ritorno il valore
		return returnValue;
	}
	
	/**
	 * Controlla che la mail sia effettivamente del formato giusto
	 * @param email Email da controllare
	 * @return true se la mail è effettivamente corretta, false se non è corretta
	 */
    public static boolean emailFormatCheck(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Verifica se l'email contiene un solo carattere '@' e almeno un carattere '.' dopo di esso
        if (email.indexOf('@') <= 0 || email.lastIndexOf('.') <= email.indexOf('@') + 1 || email.lastIndexOf('.') == email.length() - 1) {
            return false;
        }

        return true;
    }

	/**
	 * Questo metodo dovrà permettere la registrazione di un utente generico.
	 * 
	 * 
	 */
	public static void registerAccount(Account nuovoAccount)
			throws AccountException, ClassNotFoundException, SQLException {

		// Una flag utile per capire se l'account è corrotto o meno

		if (nuovoAccount.getIdAzienda() != 0 && nuovoAccount.getIdPolo() == 0 && nuovoAccount.getIdDiocesi() == 0
				&& nuovoAccount.getIdMagazzino() == 0) {
			// id_azienda è diverso da zero e tutte le altre variabili sono zero
			nuovoAccount.setTipo("azienda");
		} else if (nuovoAccount.getIdAzienda() == 0 && nuovoAccount.getIdPolo() != 0 && nuovoAccount.getIdDiocesi() == 0
				&& nuovoAccount.getIdMagazzino() == 0) {
			// id_polo è diverso da zero e tutte le altre variabili sono zero
			nuovoAccount.setTipo("polo");
		} else if (nuovoAccount.getIdAzienda() == 0 && nuovoAccount.getIdPolo() == 0 && nuovoAccount.getIdDiocesi() != 0
				&& nuovoAccount.getIdMagazzino() == 0) {
			// id_diocesi è diverso da zero e tutte le altre variabili sono zero
			nuovoAccount.setTipo("diocesi");
		} else if (nuovoAccount.getIdAzienda() == 0 && nuovoAccount.getIdPolo() == 0 && nuovoAccount.getIdDiocesi() == 0
				&& nuovoAccount.getIdMagazzino() != 0) {
			// id_magazzino è diverso da zero e tutte le altre variabili sono zero
			nuovoAccount.setTipo("admin");
		} else {
			// Almeno una variabile ha un valore diverso da zero
			throw new AccountException("Creazione account fallita!");
		}

		// Si procede con la connessione al DB

		DatabaseManager dbm = new DatabaseManager();
		dbm.connect();
		// Hash della password
		nuovoAccount.setPassword(hashPassword(nuovoAccount.getPassword()));
		String[] colName = { "nome", "cognome", "email", "password", "id_polo", "id_azienda", "id_diocesi",
				"id_magazzino" };
		
		String idpolo;
		if(nuovoAccount.getIdPolo()==0) {
			System.out.println("Polo nullo");
			idpolo = "NULL";
		}else {
			idpolo = Integer.toString( nuovoAccount.getIdPolo() );
		}
		
		String idazienda;
		if(nuovoAccount.getIdAzienda()==0) {
			System.out.println("Azienda nulla");
			idazienda = "NULL";
		}else {
			idazienda = Integer.toString( nuovoAccount.getIdAzienda() );
		}
		
		String iddiocesi;
		if(nuovoAccount.getIdDiocesi()==0) {
			System.out.println("Diocesi nulla");
			iddiocesi = "NULL";
		}else {
			iddiocesi = Integer.toString( nuovoAccount.getIdDiocesi() );
		}
		
		String idmagazzino;
		if(nuovoAccount.getIdMagazzino()==0) {
			System.out.println("Magazzino nullo");
			idmagazzino = "NULL";
		}else {
			idmagazzino = Integer.toString( nuovoAccount.getIdMagazzino() );
		}
		
		
		String[] colValue = { nuovoAccount.getNome(), nuovoAccount.getCognome(), nuovoAccount.getEmail(),
				nuovoAccount.getPassword(), idpolo,
				idazienda, iddiocesi,
				idmagazzino };
		dbm.insertRecord("Account", colName, colValue);
		System.out.println("registerAccount");
	}

	/**
	 * Questo metodo fa l'hash della password che gli viene data.
	 * 
	 * @param password stringa su cui si vuole fare l'hash
	 * 
	 * @return stringa con l'hash SHA-256 applicato
	 */
	public static String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

			StringBuilder hexString = new StringBuilder();
			for (byte b : encodedHash) {
				String hex = String.format("%02x", b);
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void registraAzienda() {
		RegistraAzienda ra=new RegistraAzienda(account);
		ra.setVisible(true);
	}
	
	public void registraPolo() {
		RegistraPolo rp=new RegistraPolo(account);
		rp.setVisible(true);
	}
	
	public void registraDiocesi() {
		RegistraDiocesi rd=new RegistraDiocesi(account);
		rd.setVisible(true);
	}

}