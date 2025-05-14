package help;

/*
 * Questa classe contiene le informazioni degli account e dei semplici metodi di get e set
 * @version 1.0
 * @author Federico Ennio Ambrogi
 */

public class Account {

	private String email;
	private String password;
	private String nome;
	private String cognome;
	private int id_azienda;
	private int id_polo;
	private int id_diocesi;
	private String stato;
	private int id_magazzino;
	private String tipo;

	public Account(String email, String password, String nome, String cognome, int id_azienda, int id_polo,
			int id_diocesi, String stato, int id_magazzino) {
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.id_azienda = id_azienda;
		this.id_polo = id_polo;
		this.id_diocesi = id_diocesi;
		this.stato = stato;
		this.id_magazzino = id_magazzino;
	}
	
	public Account() {
		
	}

	//Getter e Setter per l'attributo "tipo"
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo=tipo;
	}
	
	// Getter e Setter per l'attributo "email"
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// Getter e Setter per l'attributo "password"
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Getter e Setter per l'attributo "nome"
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// Getter e Setter per l'attributo "cognome"
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	// Getter e Setter per l'attributo "id_azienda"
	public int getIdAzienda() {
		return id_azienda;
	}

	public void setIdAzienda(int id_azienda) {
		this.id_azienda = id_azienda;
	}

	// Getter e Setter per l'attributo "id_polo"
	public int getIdPolo() {
		return id_polo;
	}

	public void setIdPolo(int id_polo) {
		this.id_polo = id_polo;
	}

	// Getter e Setter per l'attributo "id_diocesi"
	public int getIdDiocesi() {
		return id_diocesi;
	}

	public void setIdDiocesi(int id_diocesi) {
		this.id_diocesi = id_diocesi;
	}

	// Getter e Setter per l'attributo "stato"
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	// Getter e Setter per l'attributo "id_magazzino"
	public int getIdMagazzino() {
		return id_magazzino;
	}

	public void setIdMagazzino(int id_magazzino) {
		this.id_magazzino = id_magazzino;
	}

	@Override
	public String toString() {
		return "User{" + "email='" + email + '\'' + ", password='" + password + '\'' + ", nome='" + nome + '\''
				+ ", cognome='" + cognome + '\'' + ", id_azienda=" + id_azienda + ", id_polo=" + id_polo
				+ ", id_diocesi=" + id_diocesi + ", stato='" + stato + '\'' + ", id_magazzino=" + id_magazzino + '}';
	}

}
