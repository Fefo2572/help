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

public class VisualizzaPoli extends JFrame {

	private JPanel contentPane;
	Account account;
	private JTable table;
	private DefaultTableModel modell;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizzaPoli frame = new VisualizzaPoli();
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
	public VisualizzaPoli(Account account) {
		this.account=account;
		setTitle("Visualizza Poli");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 771, 477);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton indietroButto = new JButton("Indietro");
		indietroButto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuDiocesi md=new MenuDiocesi(account);
				md.setVisible(true);
				dispose();
			}
		});
		indietroButto.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButto.setBounds(10, 399, 120, 28);
		contentPane.add(indietroButto);
		
		JLabel visualizzaPoliLabel = new JLabel("Lista Poli");
		visualizzaPoliLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		visualizzaPoliLabel.setBounds(10, 11, 153, 39);
		contentPane.add(visualizzaPoliLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 52, 735, 319);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Nome", "Citt\u00E0", "Stato"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		

		//aggiornamento dei valori della tabella presi dal DB
		modell = (DefaultTableModel) table.getModel();
		updateTable();
		
	}
	
	
	//updatetable per visualizzare gli elementi già presenti nel db nella Jtable
		private void updateTable() {
			try {
				ArrayList<Polo> poli = Polo.getlistaPoliFromDiocesi(account.getIdDiocesi());
				
				for(Polo u : poli) {
					modell.addRow(new Object[] {u.getId(),u.getNome(),u.getCitta(),u.getStato()});
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	
	
	
	
	
	
}
