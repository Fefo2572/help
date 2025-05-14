package help;

import java.time.LocalDate;

public class Lotto {
    private int id;                // Identificatore del lotto
    private int id_tipologia;      // Identificatore della tipologia del lotto
    private int id_azienda;        // Identificatore dell'azienda
    private LocalDate scadenza;    // Data di scadenza del lotto
    private int quantita;          // Quantità del lotto
    private LocalDate data_produzione;  // Data di produzione del lotto
    
    // Costruttore della classe Lotto
    public Lotto(int id, int id_tipologia, int id_azienda, LocalDate scadenza, int quantita, LocalDate data_produzione) {
        this.id = id;
        this.id_tipologia = id_tipologia;
        this.id_azienda = id_azienda;
        this.scadenza = scadenza;
        this.quantita = quantita;
        this.data_produzione = data_produzione;
    }
    
    // Getter per l'ID del lotto
    public int getId() {
        return id;
    }
    
    // Setter per l'ID del lotto
    public void setId(int id) {
        this.id = id;
    }
    
    // Getter per l'ID della tipologia del lotto
    public int getIdTipologia() {
        return id_tipologia;
    }
    
    // Setter per l'ID della tipologia del lotto
    public void setIdTipologia(int id_tipologia) {
        this.id_tipologia = id_tipologia;
    }
    
    // Getter per l'ID dell'azienda del lotto
    public int getIdAzienda() {
        return id_azienda;
    }
    
    // Setter per l'ID dell'azienda del lotto
    public void setIdAzienda(int id_azienda) {
        this.id_azienda = id_azienda;
    }
    
    // Getter per la data di scadenza del lotto
    public LocalDate getScadenza() {
        return scadenza;
    }
    
    // Setter per la data di scadenza del lotto
    public void setScadenza(LocalDate scadenza) {
        this.scadenza = scadenza;
    }
    
    // Getter per la quantità del lotto
    public int getQuantita() {
        return quantita;
    }
    
    // Setter per la quantità del lotto
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    
    // Getter per la data di produzione del lotto
    public LocalDate getDataProduzione() {
        return data_produzione;
    }
    
    // Setter per la data di produzione del lotto
    public void setDataProduzione(LocalDate data_produzione) {
        this.data_produzione = data_produzione;
    }
    
    // Metodo toString per rappresentare l'oggetto Lotto come una stringa
    @Override
    public String toString() {
        return "Lotto{" +
                "id=" + id +
                ", id_tipologia=" + id_tipologia +
                ", id_azienda=" + id_azienda +
                ", scadenza=" + scadenza +
                ", quantita=" + quantita +
                ", data_produzione=" + data_produzione +
                '}';
    }
}
