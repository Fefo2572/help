package help;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Diocesi {
	private int id;
    private String nome;
    private String comune;
    
    /**
     * Costruttore parametrico per la classe Diocesi.
     * 
     * @param id     l'ID della diocesi
     * @param nome   il nome della diocesi
     * @param comune il comune della diocesi
     */
    public Diocesi(int id, String nome, String comune) {
        this.id = id;
        this.nome = nome;
        this.comune = comune;
    }
    
    /**
     * Restituisce l'ID della diocesi.
     * 
     * @return l'ID della diocesi
     */
    public int getId() {
        return id;
    }
    
    /**
     * Imposta l'ID della diocesi.
     * 
     * @param id l'ID da impostare
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Restituisce il nome della diocesi.
     * 
     * @return il nome della diocesi
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Imposta il nome della diocesi.
     * 
     * @param nome il nome da impostare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Restituisce il comune della diocesi.
     * 
     * @return il comune della diocesi
     */
    public String getComune() {
        return comune;
    }
    
    /**
     * Imposta il comune della diocesi.
     * 
     * @param comune il comune da impostare
     */
    public void setComune(String comune) {
        this.comune = comune;
    }
 
}
	
