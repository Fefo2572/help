package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class VisualizzaCarichiDiocesi extends JFrame {

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
					VisualizzaCarichiDiocesi frame = new VisualizzaCarichiDiocesi();
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
	public VisualizzaCarichiDiocesi(Account account) {
		setTitle("Visualizza Carichi Diocesi");
		setResizable(false);
		this.account=account;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 404);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel visualizzaCarichiLabel = new JLabel("Visualizza Carichi Diocesi");
		visualizzaCarichiLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		visualizzaCarichiLabel.setBounds(10, 11, 327, 45);
		contentPane.add(visualizzaCarichiLabel);
		
		JButton indietroLabel = new JButton("Indietro");
		indietroLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuDiocesi md=new MenuDiocesi(account);
				md.setVisible(true);
				dispose();
			}
		});
		indietroLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroLabel.setBounds(10, 326, 120, 28);
		contentPane.add(indietroLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 56, 664, 243);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Quantit\u00E0", "Stato", "Data"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Integer.class, String.class, Object.class
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
		
		//aggiornameto tabella prendendo i valori dal DB
		modell = (DefaultTableModel) table.getModel();
		updateTable();
				
		
	}
	
	
	//updatetable per visualizzare gli elementi già presenti nel db nella Jtable
			private void updateTable() {
				try {
					int id=Distributore.getIdMagazzino(account);
					ArrayList<Carico> carichi = Distributore.getEveryCarichi(id);
					
					for(Carico u : carichi) {
						modell.addRow(new Object[] {u.getId(),u.getQuantita(),u.getStato(),u.getData()});
					}
					
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}

}
