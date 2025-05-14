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
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class VisualizzaRichieste extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modell;
	Account account;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizzaRichieste frame = new VisualizzaRichieste();
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
	public VisualizzaRichieste(Account account) {
		this.account=account;
		setTitle("Visualizza Richieste");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 458);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAzienda ma=new MenuAzienda(account);
				ma.setVisible(true);
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 380, 120, 28);
		contentPane.add(indietroButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 53, 644, 308);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Data", "Stato"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Object.class, Integer.class
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
		
		JLabel richiesteLabel = new JLabel("Lista Richieste");
		richiesteLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		richiesteLabel.setBounds(10, 11, 201, 42);
		contentPane.add(richiesteLabel);
		
		JButton visualizzaDettagliButton = new JButton("Visualizza Dettagli");
		visualizzaDettagliButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if(selectedRow!=-1) {
					int rowIndex = table.getSelectedRow();
					String id = (String) table.getValueAt(rowIndex, 0).toString();
					Integer id_r=Integer.parseInt(id);
					VisualizzaDettagli vd=new VisualizzaDettagli(account,id_r);
					vd.setVisible(true);
					dispose();
				}
				
			}
		});
		visualizzaDettagliButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		visualizzaDettagliButton.setBounds(453, 380, 201, 28);
		contentPane.add(visualizzaDettagliButton);
		
		modell = (DefaultTableModel) table.getModel();
		updateTable();
	}
	
	//richieste presenti
	private void updateTable() {
		try {
			ArrayList<Richiesta> richieste = Azienda.getRichieste(account.getIdAzienda());
			
			for(Richiesta u : richieste) {
				modell.addRow(new Object[] {u.getId(),u.getData(),u.getStato()});
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}



