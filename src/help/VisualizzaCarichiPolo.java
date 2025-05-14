package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class VisualizzaCarichiPolo extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Account account;
	private DefaultTableModel modell;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizzaCarichiPolo frame = new VisualizzaCarichiPolo();
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
	public VisualizzaCarichiPolo(Account account) {
		this.account=account;
		setTitle("Visualizza Carichi Polo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 405);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel visualizzaCarichiLabel = new JLabel("Visualizza Carichi");
		visualizzaCarichiLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		visualizzaCarichiLabel.setBounds(10, 11, 232, 32);
		contentPane.add(visualizzaCarichiLabel);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPolo mp=new MenuPolo(account);
				mp.setVisible(true);
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 327, 120, 28);
		contentPane.add(indietroButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 54, 621, 235);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Id_Lotto", "Quantit\u00E0", "Stato", "Data"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Integer.class, Integer.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		
		modell = (DefaultTableModel) table.getModel();
		updateTable();
	}
	
	
	//updatetable per visualizzare gli elementi già presenti nel db nella Jtable
		private void updateTable() {
			try {
				int id=Distributore.getIdMagazzino(account);
				ArrayList<Carico> carichi = Distributore.getEveryCarichi(id);
				
				for(Carico u : carichi) {
					modell.addRow(new Object[] {u.getId(),u.getIdLotto(),u.getQuantita(),u.getStato(),u.getData()});
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	

}
