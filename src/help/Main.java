package help;
import java.time.LocalDate;
public class Main {

	public static void main(String[] args) {
		
		LocalDate oggi = LocalDate.now();
        try {
        	if (oggi.getDayOfMonth() == 25) {
            	String dataformattata=DatabaseManager.dataDBMSFormatter(oggi);
            	DatabaseManager dbm=new DatabaseManager();
            	dbm.connect();
            	String query="SELECT * from richiesta WHERE data='"+dataformattata+"'";
                System.out.println("Oggi è il 25 del mese.");
                int nrighe=dbm.getQueryRowCount(query);
                if(nrighe>0) {
                	System.out.println("Richiesta gia presente\n");
                }else {
                	PannelloDiControllo.creaRichiesta();
                }
            } else {
                System.out.println("Oggi non è il 25 del mese.");
            }	
        }catch(Exception e) {
        	System.out.println(e);
        }
        
      //chiamata al login
      LoginScreen lg=new LoginScreen();
      lg.setVisible(true);


	}

}
