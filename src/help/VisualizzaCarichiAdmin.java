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

public class VisualizzaCarichiAdmin extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modell;
	Account account;
	private JTable table;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizzaCarichiAdmin frame = new VisualizzaCarichiAdmin();
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
	public VisualizzaCarichiAdmin(Account account) {
		this.account=account;
		setTitle("Visualizza Carichi Admin");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 386);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdmin ma=new MenuAdmin(account);
				ma.setVisible(true);
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 308, 120, 28);
		contentPane.add(indietroButton);
		
		JLabel visualizzaCarichiLabelAdminLabel = new JLabel("Visualizza Carichi ");
		visualizzaCarichiLabelAdminLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		visualizzaCarichiLabelAdminLabel.setBounds(10, 11, 327, 41);
		contentPane.add(visualizzaCarichiLabelAdminLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 690, 230);
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
