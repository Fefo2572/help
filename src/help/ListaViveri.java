package help;
public class ListaViveri {
	
    private int idCibo;
    private String nomeCibo;
    private int quantita;

    // Costruttore parametrico
    public ListaViveri(int idCibo, String nomeCibo, int quantita) {
        this.idCibo = idCibo;
        this.nomeCibo = nomeCibo;
        this.quantita = quantita;
    }

    // Getter per l'id del cibo
    public int getIdCibo() {
        return idCibo;
    }

    // Setter per l'id del cibo
    public void setIdCibo(int idCibo) {
        this.idCibo = idCibo;
    }

    // Getter per il nome del cibo
    public String getNomeCibo() {
        return nomeCibo;
    }

    // Setter per il nome del cibo
    public void setNomeCibo(String nomeCibo) {
        this.nomeCibo = nomeCibo;
    }

    // Getter per la quantità
    public int getQuantita() {
        return quantita;
    }

    // Setter per la quantità
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    // Metodo per ottenere una rappresentazione testuale dell'oggetto
    @Override
    public String toString() {
        return "ID Cibo: " + idCibo +
                ", Nome Cibo: " + nomeCibo +
                ", Quantità: " + quantita;
    }
}
