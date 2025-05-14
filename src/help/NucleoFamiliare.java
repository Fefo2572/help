package help;

public class NucleoFamiliare {
    private int id_nucleo; // Identificatore del nucleo familiare
    private String nome; // Nome del nucleo familiare
    private float Isee; // Valore ISEE del nucleo familiare
    private int id_polo; // Identificatore del polo

    // Costruttore senza parametri
    public NucleoFamiliare() {
    }

    // Costruttore parametrico
    public NucleoFamiliare(int id_nucleo, String nome, float Isee, int id_polo) {
        this.id_nucleo = id_nucleo;
        this.nome = nome;
        this.Isee = Isee;
        this.id_polo = id_polo;
    }

    // Getter e Setter per l'identificatore del nucleo familiare
    public int getIdNucleo() {
        return id_nucleo;
    }

    public void setIdNucleo(int id_nucleo) {
        this.id_nucleo = id_nucleo;
    }

    // Getter e Setter per il nome del nucleo familiare
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter per il valore ISEE del nucleo familiare
    public float getIsee() {
        return Isee;
    }

    public void setIsee(float Isee) {
        this.Isee = Isee;
    }

    // Getter e Setter per l'identificatore del polo
    public int getIdPolo() {
        return id_polo;
    }

    public void setIdPolo(int id_polo) {
        this.id_polo = id_polo;
    }

    // Metodo toString per rappresentazione testuale degli attributi dell'oggetto
    @Override
    public String toString() {
        return "NucleoFamiliare{" +
                "id_nucleo=" + id_nucleo +
                ", nome='" + nome + '\'' +
                ", Isee=" + Isee +
                ", id_polo=" + id_polo +
                '}';
    }
}
