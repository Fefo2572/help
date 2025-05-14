package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class VisualizzaDettagli extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modell;
	Account account;
	int id_richiesta;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizzaDettagli frame = new VisualizzaDettagli();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public VisualizzaDettagli(Account account,int id_richiesta) {
		this.account=account;
		this.id_richiesta=id_richiesta;
		setTitle("Visualizza Dettagli");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 747, 484);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaRichieste vr=new VisualizzaRichieste(account);
				vr.setVisible(true);
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 406, 120, 28);
		contentPane.add(indietroButton);
		
		JLabel visualizzaDettagliLabel = new JLabel("Visualizza Dettagli Richiesta");
		visualizzaDettagliLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		visualizzaDettagliLabel.setBounds(10, 11, 374, 28);
		contentPane.add(visualizzaDettagliLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 711, 330);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Nome_Cibo", "Quantit\u00E0"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		
		
		modell = (DefaultTableModel) table.getModel();
		updateTable();
	}
	
	//aggiornamento tabella
	private void updateTable() {
		try {
			ArrayList<ListaViveri> viveri = Azienda.getdettagliRichiesta(account.getIdAzienda(),id_richiesta);
			
			for(ListaViveri u : viveri) {
				modell.addRow(new Object[] {u.getIdCibo(),u.getNomeCibo(),u.getQuantita()});
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
