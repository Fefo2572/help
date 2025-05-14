package help;

public class TipologiaViveri {
    private int id;
    private String nomeCibo;
    private int fabbisognoAdulto;
    private int fabbisognoBambino;
    private int fabbisognoNeonato;
    private boolean forCeliaci;
    private boolean forDiabetici;
    

    // Costruttore della classe
    public TipologiaViveri(int id, String nomeCibo, int fabbisognoAdulto, int fabbisognoBambino, int fabbisognoNeonato) {
        this.id = id;
        this.nomeCibo = nomeCibo;
        this.fabbisognoAdulto = fabbisognoAdulto;
        this.fabbisognoBambino = fabbisognoBambino;
        this.fabbisognoNeonato = fabbisognoNeonato;
    }

    // Metodo getter per l'attributo id
    public int getId() {
        return id;
    }

    // Metodo setter per l'attributo id
    public void setId(int id) {
        this.id = id;
    }

    // Metodo getter per l'attributo nomeCibo
    public String getNomeCibo() {
        return nomeCibo;
    }

    // Metodo setter per l'attributo nomeCibo
    public void setNomeCibo(String nomeCibo) {
        this.nomeCibo = nomeCibo;
    }

    // Metodo getter per l'attributo fabbisognoAdulto
    public int getFabbisognoAdulto() {
        return fabbisognoAdulto;
    }

    // Metodo setter per l'attributo fabbisognoAdulto
    public void setFabbisognoAdulto(int fabbisognoAdulto) {
        this.fabbisognoAdulto = fabbisognoAdulto;
    }

    // Metodo getter per l'attributo fabbisognoBambino
    public int getFabbisognoBambino() {
        return fabbisognoBambino;
    }

    // Metodo setter per l'attributo fabbisognoBambino
    public void setFabbisognoBambino(int fabbisognoBambino) {
        this.fabbisognoBambino = fabbisognoBambino;
    }

    // Metodo getter per l'attributo fabbisognoNeonato
    public int getFabbisognoNeonato() {
        return fabbisognoNeonato;
    }

    // Metodo setter per l'attributo fabbisognoNeonato
    public void setFabbisognoNeonato(int fabbisognoNeonato) {
        this.fabbisognoNeonato = fabbisognoNeonato;
    }
    
    public boolean isForCeliaci() {
    	return forCeliaci;
    }
    
    public boolean isForDiabetici() {
    	return forDiabetici;
    }
    
    public void setForDiabetici(boolean forDiabetici) {
    	this.forDiabetici = forDiabetici;
    }
    
    public void setForCeliaci(boolean forCeliaci) {
    	this.forCeliaci = forCeliaci;
    }
    

    // Metodo toString per rappresentare l'oggetto come stringa
    @Override
    public String toString() {
        return "TipologiaViveri{" +
                "id=" + id +
                ", nomeCibo='" + nomeCibo + '\'' +
                ", fabbisognoAdulto=" + fabbisognoAdulto +
                ", fabbisognoBambino=" + fabbisognoBambino +
                ", fabbisognoNeonato=" + fabbisognoNeonato +
                ", forCeliaci="+forCeliaci+
                ",forDiabetici="+forDiabetici+
                '}';
    }
}
