package help;
import java.time.LocalDate;



public class Richiesta {
    private int id; // Identificatore univoco della richiesta
    private LocalDate data; // Data della richiesta
    private String stato; // Stato corrente della richiesta

    /**
     * Costruttore parametrico per la classe Richiesta.
     *
     * @param id    L'identificatore univoco della richiesta
     * @param data  La data della richiesta
     * @param stato Lo stato corrente della richiesta
     */
    public Richiesta(int id, LocalDate data, String stato) {
        this.id = id;
        this.data = data;
        this.stato = stato;
    }

    /**
     * Restituisce l'identificatore della richiesta.
     *
     * @return L'identificatore della richiesta
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'identificatore della richiesta.
     *
     * @param id L'identificatore della richiesta
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce la data della richiesta.
     *
     * @return La data della richiesta
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Imposta la data della richiesta.
     *
     * @param data La data della richiesta
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Restituisce lo stato corrente della richiesta.
     *
     * @return Lo stato corrente della richiesta
     */
    public String getStato() {
        return stato;
    }

    /**
     * Imposta lo stato corrente della richiesta.
     *
     * @param stato Lo stato corrente della richiesta
     */
    public void setStato(String stato) {
        this.stato = stato;
    }

    /**
     * Restituisce una stringa che rappresenta l'oggetto Richiesta.
     *
     * @return Una stringa rappresentante l'oggetto Richiesta
     */
    @Override
    public String toString() {
        return "Richiesta{" +
                "id=" + id +
                ", data=" + data +
                ", stato='" + stato + '\'' +
                '}';
    }
}