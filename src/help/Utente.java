package help;
import java.time.LocalDate;

public class Utente {
	        private String codiceFiscale;
	        private String nome;
	        private String cognome;
	        private String sesso;
	        private LocalDate dataNascita;
	        private String residenza;
	        private boolean celiaco;
	        private boolean diabetico;
	       // private String richieste;

	        public Utente() {
	        	codiceFiscale="";
	        	nome="";
	        	cognome="";
	        	sesso="";
	        	residenza="";
	            celiaco = false;
	            diabetico = false;
	        	//richieste="";
	        }
	        
	        public Utente(String codiceFiscale, String nome, String cognome, String sesso, LocalDate dataNascita, String residenza) {
	            this.codiceFiscale = codiceFiscale;
	            this.nome = nome;
	            this.cognome = cognome;
	            this.sesso = sesso;
	            this.dataNascita = dataNascita;
	            this.residenza = residenza;
	            celiaco = false;
	            diabetico = false;
	            
	            //this.richieste=richieste;
	        }

	       //setter
	        public void setCodiceFiscale(String codiceFiscale) {
	        	this.codiceFiscale = codiceFiscale;
	        }
	        
	        public void setNome(String nome) {
	        	 this.nome = nome;
	        }
	        
	        public void setCognome(String cognome) {
	        	  this.cognome = cognome;
	        }
	        
	        public void setSesso(String sesso) {
	        	 this.sesso = sesso;
	        }
	        
	        public void setDatanascita(LocalDate dataNascita) {
	        	this.dataNascita = dataNascita;
	        }
	        
	        public void setResidenza(String residenza) {
	        	 this.residenza = residenza;
	        }
	        
	        /*public void setRichieste(String richieste) {
	        	 this.richieste=richieste;
	        }*/
	        
	        //getter
	        public String getCodiceFiscale() {
	            return codiceFiscale;
	        }

	        public String getNome() {
	            return nome;
	        }

	        public String getCognome() {
	            return cognome;
	        }

	        public String getSesso() {
	            return sesso;
	        }

	        public LocalDate getDataNascita() {
	            return dataNascita;
	        }

	        public String getResidenza() {
	            return residenza;
	        }
	        
	        public boolean isDiabetico() {
	        	return diabetico;
	        }
	        
	        public boolean isCeliaco() {
	        	return celiaco;
	        }
	        
	        public void setDiabetico(boolean diabetico) {
	        	this.diabetico = diabetico;
	        }
	        
	        public void setCeliaco(boolean celiaco) {
	        	this.celiaco=celiaco;
	        }
	        
	        public int getEta() {
	        	LocalDate oggi = LocalDate.now();
	        	int eta = oggi.getYear() - dataNascita.getYear();
	        	return eta;
	        }
	        
	       /* public String getRichieste() {
	        	return richieste;
	        }*/

	        @Override
	        public String toString() {
	            return "Utente:"+"CF:"+getCodiceFiscale()+"\n"+"Nome:"+getNome()+"\n"+"Cognome:"+"\n"+"sesso:"+getSesso()+"\n"+"data:"+getDataNascita()+"\n"+"residenza:"+getResidenza()+"\n";
	        }
}
