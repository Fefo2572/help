package help;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Carico {
    private int id; // ID del carico
    private int quantita; // Quantità del carico
    private String stato; // Stato del carico
    private LocalDate data; // Data del carico
    private int id_lotto; // ID del lotto associato al carico
    private int id_magazzino; // ID del magazzino in cui è stato caricato il carico
    
    
    
 // Costruttore parametrico per la diocesi
    public Carico(int id, int quantita, String stato, LocalDate data) {
    	 this.id = id;
         this.quantita = quantita;
         this.stato = stato;
         this.data = data;
    }
    

    // Costruttore della classe "Carico"
    public Carico(int id, int quantita, String stato, LocalDate data, int id_lotto, int id_magazzino) {
        this.id = id;
        this.quantita = quantita;
        this.stato = stato;
        this.data = data;
        this.id_lotto = id_lotto;
        this.id_magazzino = id_magazzino;
    }

    // Getter e setter per l'attributo "id"
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter e setter per l'attributo "quantita"
    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    // Getter e setter per l'attributo "stato"
    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    // Getter e setter per l'attributo "data"
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    // Getter e setter per l'attributo "id_lotto"
    public int getIdLotto() {
        return id_lotto;
    }

    public void setIdLotto(int id_lotto) {
        this.id_lotto = id_lotto;
    }

    // Getter e setter per l'attributo "id_magazzino"
    public int getIdMagazzino() {
        return id_magazzino;
    }

    public void setIdMagazzino(int id_magazzino) {
        this.id_magazzino = id_magazzino;
    }
    
}
