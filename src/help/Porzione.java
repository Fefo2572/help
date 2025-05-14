package help;

public class Porzione {
    private int idViveri;        // ID dei viveri
    private String nomeCibo;     // Nome del cibo
    private int quantita;        // Quantità della porzione

    // Costruttore parametrico
    public Porzione(int idViveri, String nomeCibo, int quantita) {
        this.idViveri = idViveri;
        this.nomeCibo = nomeCibo;
        this.quantita = quantita;
    }

    // Getter per l'ID dei viveri
    public int getIdViveri() {
        return idViveri;
    }

    // Setter per l'ID dei viveri
    public void setIdViveri(int idViveri) {
        this.idViveri = idViveri;
    }

    // Getter per il nome del cibo
    public String getNomeCibo() {
        return nomeCibo;
    }

    // Setter per il nome del cibo
    public void setNomeCibo(String nomeCibo) {
        this.nomeCibo = nomeCibo;
    }

    // Getter per la quantità della porzione
    public int getQuantita() {
        return quantita;
    }

    // Setter per la quantità della porzione
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    // Metodo toString per rappresentare l'oggetto come stringa
    @Override
    public String toString() {
        return "Porzione{" +
                "idViveri=" + idViveri +
                ", nomeCibo='" + nomeCibo + '\'' +
                ", quantita=" + quantita +
                '}';
    }
}