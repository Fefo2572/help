package help;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Errore {

	private Integer id;
	private String descrizione;
	private Integer id_polo;
	
	public Errore() {
		id=0;
		descrizione="";
		id_polo=0;
	}
	
	//costruttore parametrico per visualizzazione errori nella classe Segnala
	
	public Errore(Integer id,String descrizione) {
		this.id=id;
		this.descrizione=descrizione;
		
	}
	
	//costruttore parametrico
	public Errore(Integer id,String descrizione,Integer id_polo) {
		this.id=id;
		this.descrizione=descrizione;
		this.id_polo=id_polo;
	}
	
	//setter
	public void setId(Integer id) {
		this.id=id;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione=descrizione;
	}
	
	public void setIdPolo(Integer id_polo) {
		this.id_polo=id_polo;
	}
	
	//getter
	public Integer getId() {
		return id;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public Integer getId_Polo() {
		return id_polo;
	}
	
	
	
}
