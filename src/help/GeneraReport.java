package help;

import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;

public class GeneraReport {
	private Account account;
	private int id_magazzino;

	public GeneraReport(Account account) {
		this.account = account;
		id_magazzino=getIdMagazzino();
	}
	
	public void indietro() {
		if(account.getTipo().equals("admin")) {
			MenuAdmin ma=new MenuAdmin(account);
			ma.setVisible(true);
		}else if(account.getTipo().equals("diocesi")) {
			MenuDiocesi md=new MenuDiocesi(account);
			md.setVisible(true);
		}else {
			MenuPolo mp=new MenuPolo(account);
			mp.setVisible(true);
		}
	}

	public int getIdMagazzino() {
		int id_magazzino = 0;
		DatabaseManager dbm = new DatabaseManager();
		try {
			dbm.connect();
			if (account.getTipo().equals("admin")) {
				id_magazzino = 1;
			} else if (account.getTipo().equals("polo")) {
				String query = "SELECT id_magazzino FROM polo WHERE id=" + account.getIdPolo();
				ResultSet result = dbm.executeQuery(query);
				while (result.next()) {
					id_magazzino = result.getInt("id_magazzino");
				}
			} else {
				String query = "SELECT id_magazzino FROM diocesi WHERE id=" + account.getIdDiocesi();
				ResultSet result = dbm.executeQuery(query);
				while (result.next()) {
					id_magazzino = result.getInt("id_magazzino");
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id_magazzino;
	}

	public void report(int giorni) {
		String nomeFile;
		try {
			ArrayList<Carico> in = Distributore.getEveryCarichi(id_magazzino, "in");
			ArrayList<Carico> out = Distributore.getEveryCarichi(id_magazzino, "out");
			ArrayList<Carico> del = Distributore.getEveryCarichi(id_magazzino, "del");

			ArrayList<Carico> inMensili = new ArrayList<>();
			ArrayList<Carico> outMensili = new ArrayList<>();
			ArrayList<Carico> delMensili = new ArrayList<>();
			LocalDate oggi = LocalDate.now();
			LocalDate meseFa = oggi.minusDays(giorni);

			// inMensili
			for (Carico c : in) {
				if (c.getData().isAfter(meseFa) || c.getData().isEqual(meseFa)) {
					inMensili.add(c);
				}
			}

			// outMensili
			for (Carico c : out) {
				if (c.getData().isAfter(meseFa) || c.getData().isEqual(meseFa)) {
					outMensili.add(c);
				}
			}

			// delMensili
			for (Carico c : del) {
				if (c.getData().isAfter(meseFa) || c.getData().isEqual(meseFa)) {
					delMensili.add(c);
				}
			}
			String report;
			if (giorni == 30) {
				report = "--------------------Report Mensili--------------------\n";
			} else if (giorni == 90) {
				report = "--------------------Report Trimestrale--------------------\n";
			} else {
				report = "--------------------Report Annuale--------------------\n";
			}

			report = "--------------------Report Mensili--------------------\n";
			String reportiIn = "Carichi in Entrata:\n";
			String reportOut = "Carichi in Uscita:\n";
			String reportDel = "Carichi in Spedizione:\n";

			for (Carico c : inMensili) {
				String s = "";
				s += "ID:" + c.getId() + "\n";
				s += "Quantità:" + c.getQuantita() + "\n";
				s += "Data:" + c.getData().toString() + "\n";
				s += "ID_Lotto:" + c.getIdLotto() + "\n";
				s += "ID_Magazzino:" + c.getIdMagazzino() + "\n";
				s += "\n-----------------------------------------------\n\n";
				reportiIn += s;
			}

			for (Carico c : outMensili) {
				String s = "";
				s += "ID:" + c.getId() + "\n";
				s += "Quantità:" + c.getQuantita() + "\n";
				s += "Data:" + c.getData().toString() + "\n";
				s += "ID_Lotto:" + c.getIdLotto() + "\n";
				s += "ID_Magazzino:" + c.getIdMagazzino() + "\n";
				s += "\n-----------------------------------------------\n\n";
				reportOut += s;
			}

			for (Carico c : delMensili) {
				String s = "";
				s += "ID:" + c.getId() + "\n";
				s += "Quantità:" + c.getQuantita() + "\n";
				s += "Data:" + c.getData().toString() + "\n";
				s += "ID_Lotto:" + c.getIdLotto() + "\n";
				s += "ID_Magazzino:" + c.getIdMagazzino() + "\n";
				s += "\n-----------------------------------------------\n\n";
				reportDel += s;
			}

			report += reportiIn + reportOut + reportDel;
			// Ottieni il percorso della cartella del desktop
			String percorsoDesktop = System.getProperty("user.home") + "/Desktop/";

			// Specifica il nome del file
			if (giorni == 30) {
				nomeFile = "Report Mensile.txt";
			} else if (giorni == 90) {
				nomeFile = "Report Trimestrale.txt";
			} else {
				nomeFile = "Report Annuale.txt";
			}

			// Crea il percorso completo del file
			String percorsoFile = percorsoDesktop + nomeFile;

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(percorsoFile))) {
				writer.write(report);
				System.out.println("File creato con successo.");
			} catch (IOException e) {
				System.out.println("Si è verificato un errore durante la creazione del file: " + e.getMessage());
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
