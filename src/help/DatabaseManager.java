/* Questa classe si occupa della gestione del database, permette l'inserimento, modifica e
 * lettura di ciò che si trova all'interno del database, si tratta di una vera e propria
 * interfaccia fra il programma in java ed il database mysql
 * 
 * @version 1.5
 * @author Federico Ennio Ambrogi
 */

package help;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;

public class DatabaseManager {
	private Connection connection;
	private String url;
	private String username;
	private String password;

	/*
	 * Costruttore con parametri, ove si può specificare il tipo di database nella
	 * quale si vuole andare a lavorare.
	 * 
	 * @param url stringa contenente l'url del database
	 * 
	 * @param username username di accesso al database (di base root)
	 * 
	 * @param password password di accesso al database
	 */

	public DatabaseManager(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/*
	 * Costruttore senza parametri, il quale di default metterà le info del database
	 * help in locale
	 */

	public DatabaseManager() {
		url = "jdbc:mysql://localhost:3306/help";
		username = "root";
		password = "";
	}

	/*
	 * Connette il database con le credenziali date al costruttore
	 */

	public void connect() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(url, username, password);
		System.out.println("Connessione al database stabilita.");
	}

	/*
	 * Disconnette dal database
	 */

	public void disconnect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
			System.out.println("Connessione al database chiusa.");
		}
	}

	/*
	 * Esegue una query data in input
	 * 
	 * Tutorial di utilizzo:
	 * 
	 * String selectQuery = "SELECT * FROM nomitabella"; ResultSet resultSet =
	 * manager.executeQuery(selectQuery);
	 * 
	 * // Elaborazione dei risultati while (resultSet.next()) { // Estrarre i valori
	 * delle colonne dal ResultSet int id = resultSet.getInt("id"); String nome =
	 * resultSet.getString("nome"); int eta = resultSet.getInt("eta");
	 * 
	 * // Utilizzare i valori estratti come necessario System.out.println("ID: " +
	 * id + ", Nome: " + nome + ", Età: " + eta); }
	 * 
	 * @param query la query da far eseguire
	 * 
	 * @return una lista contenente tutti i record risultati dalla query
	 * 
	 */

	public ResultSet executeQuery(String query) throws SQLException {
		Statement statement = connection.createStatement();
		return statement.executeQuery(query);
	}

	/*
	 * @param query
	 * 
	 * @return numero di righe generato dalla suddetta query
	 */

	public int getQueryRowCount(String query) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);

		int rowCount = 0;
		while (resultSet.next()) {
			rowCount++;
		}

		resultSet.close();
		statement.close();

		return rowCount;
	}

	/*
	 * Inserisce un record all'interno della tabella
	 * 
	 * @param tableName il nome della tabella
	 * 
	 * @param columns una stringa contenente le colonne interessate all'inserimento
	 * tipo {"nome", "cognome"}
	 * 
	 * @param valuies i valori da inserire nell'ordine nella quale si sono scritte
	 * le colonne, per esempio {"Gabriele", "Puccio"}
	 * 
	 * @return numero di righe che sono state inserite
	 */

	public int insertRecord(String tableName, String[] columns, String[] values) throws SQLException {
	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("INSERT INTO ").append(tableName);
	    queryBuilder.append(" (").append(String.join(", ", columns)).append(")");
	    queryBuilder.append(" VALUES (").append(getFormattedValues(values)).append(")");

	    Statement statement = connection.createStatement();
	    return statement.executeUpdate(queryBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
	}

	/*
	 * Metodo privato che serve al metodo insertRecord
	 */

	private String getFormattedValues(String[] values) {
	    StringBuilder formattedValues = new StringBuilder();
	    for (int i = 0; i < values.length; i++) {
	        if (values[i].equalsIgnoreCase("NULL")) {
	            formattedValues.append("NULL");
	        } else {
	            formattedValues.append("'").append(values[i]).append("'");
	        }
	        formattedValues.append(", ");
	    }
	    formattedValues.delete(formattedValues.length() - 2, formattedValues.length()); // Rimuove l'ultima virgola e spazio
	    return formattedValues.toString();
	}

	/*
	 * Metodo per creare un update di uno specifico record Funziona in maniera
	 * simile al insertRecord, con la differenza che in questo caso si deve
	 * specificare una condizione utile a trovare il record che si vuole modificare
	 * 
	 * @param tableName il nome della tabella
	 * 
	 * @param columns una stringa contenente le colonne interessate alla modifica
	 * tipo {"nome", "cognome"}
	 * 
	 * @param valuies i valori da inserire nell'ordine nella quale si sono scritte
	 * le colonne, per esempio {"Federico Ennio", "Ambrogi"}
	 * 
	 * @return numero di righe che sono state modificate
	 * 
	 * @param conditionColumns indica le colonne che sono affette dalla condizione,
	 * per esempio {"id"}
	 * 
	 * @param conditionValues indica il valore che si vuole assegnare alla
	 * condizione, per esempio {"1"}
	 * 
	 */
	public int editRecord(String tableName, String[] columns, String[] values, String condition) throws SQLException {
	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("UPDATE ").append(tableName);
	    queryBuilder.append(" SET ").append(getFormattedUpdateValues(columns, values));
	    queryBuilder.append(" WHERE ").append(condition);

	    Statement statement = connection.createStatement();
	    return statement.executeUpdate(queryBuilder.toString());
	}

	private String getFormattedUpdateValues(String[] columns, String[] values) {
		StringBuilder formattedValues = new StringBuilder();
		for (int i = 0; i < columns.length; i++) {
			formattedValues.append(columns[i]).append(" = '").append(values[i]).append("', ");
		}
		formattedValues.delete(formattedValues.length() - 2, formattedValues.length()); // Rimuove l'ultima virgola e
																						// spazio
		return formattedValues.toString();
	}

	private String getFormattedCondition(String[] conditionColumns, String[] conditionValues) {
		StringBuilder formattedCondition = new StringBuilder();
		for (int i = 0; i < conditionColumns.length; i++) {
			formattedCondition.append(conditionColumns[i]).append(" = '").append(conditionValues[i]).append("' AND ");
		}
		formattedCondition.delete(formattedCondition.length() - 5, formattedCondition.length()); // Rimuove l'ultima "
																									// AND "
		return formattedCondition.toString();
	}

	/**
	 * Elimina un record dalla tabella specificata.
	 *
	 * @param tableName il nome della tabella
	 * @param condition la condizione per individuare il record da eliminare
	 * @return il numero di righe eliminate
	 * @throws SQLException se si verifica un errore durante l'esecuzione della
	 *                      query
	 */
	public int deleteRecord(String tableName, String condition) throws SQLException {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("DELETE FROM ").append(tableName);
		queryBuilder.append(" WHERE ").append(condition);

		Statement statement = connection.createStatement();
		return statement.executeUpdate(queryBuilder.toString());
	}

	/**
	 * Inserisce un nuovo record nella tabella specificata con valori forniti e
	 * restituisce il valore auto-generato.
	 * 
	 * @param tableName il nome della tabella
	 * @param columns   gli array delle colonne del record da inserire
	 * @param values    gli array dei valori delle colonne del record da inserire
	 * @return il valore auto-generato dell'ID del record inserito
	 * @throws SQLException se si verifiAca un errore durante l'esecuzione della
	 *                      query
	 */
	public int insertAutoIncrementRecord(String tableName, String[] columns, String[] values) throws SQLException {
	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("INSERT INTO ").append(tableName);
	    queryBuilder.append(" (").append(String.join(", ", columns)).append(")");
	    queryBuilder.append(" VALUES (").append(getFormattedValues(values)).append(")");

	    PreparedStatement statement = connection.prepareStatement(queryBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
	    statement.executeUpdate();

	    int generatedId = -1;
	    ResultSet generatedKeys = statement.getGeneratedKeys();
	    if (generatedKeys.next()) {
	        generatedId = generatedKeys.getInt(1);
	    }

	    generatedKeys.close();
	    statement.close();

	    return generatedId;
	}
	
    /**
     * Formatta una data in formato stringa leggibile per un DBMS.
     *
     * @param data la data da formattare
     * @return la data formattata come stringa nel formato leggibile dal DBMS
     */
    public static String dataDBMSFormatter(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return data.format(formatter);
    }
    
    /**
     * Converte una stringa rappresentante una data nel formato SQL (yyyy-MM-dd) in un oggetto LocalDate.
     *
     * @param dataString la stringa rappresentante la data nel formato SQL
     * @return l'oggetto LocalDate corrispondente alla data fornita
     */
    public static LocalDate javaDateFormatter(String dataString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dataString, formatter);
    }
    
    /**
     * Verifica se esiste almeno un campo nella tabella specificata che soddisfa la condizione specificata.
     *
     * @param tableName  il nome della tabella
     * @param condition  la condizione di ricerca
     * @return true se esiste almeno un campo che soddisfa la condizione, false altrimenti
     * @throws SQLException se si verifica un errore durante l'esecuzione della query
     */
    public boolean checkExists(String tableName, String condition) throws SQLException {
        String query = "SELECT EXISTS (SELECT 1 FROM " + tableName + " WHERE " + condition + ")";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        boolean exists = false;
        if (resultSet.next()) {
            exists = resultSet.getBoolean(1);
        }

        resultSet.close();
        statement.close();

        return exists;
    }
	
}