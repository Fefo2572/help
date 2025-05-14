package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class DistribuisciCibo extends JFrame {

	private JPanel contentPane;
	Account account;
	private JTable table;
	private DefaultTableModel modell;
	Integer id=0;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DistribuisciCibo frame = new DistribuisciCibo();
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
	public DistribuisciCibo(Account account) {
		this.account=account;
		setTitle("Distribuisci Cibo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 702, 434);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPolo mp=new MenuPolo(account);
				mp.setVisible(true);
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 356, 120, 28);
		contentPane.add(indietroButton);
		
		JLabel distribuisciciboLabel = new JLabel("Distribuisci Cibo a:");
		distribuisciciboLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		distribuisciciboLabel.setBounds(10, 11, 241, 39);
		contentPane.add(distribuisciciboLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 666, 255);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Nome", "ISEE"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Float.class
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
		
		JButton avantiButton = new JButton("Avanti");
		avantiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DistribuisciCarico dcc=new DistribuisciCarico(account,id);
				dcc.setVisible(true);
				dispose();
			}
		});
		avantiButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		avantiButton.setBounds(556, 356, 120, 28);
		contentPane.add(avantiButton);
		
		// valori presi con il click sulla Jtable
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					// Ottieni i valori dalla riga selezionata
					id = (Integer) table.getValueAt(selectedRow, 0);
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		

		//Aggiorno la tabella
		modell = (DefaultTableModel) table.getModel();
		updateTable();
	}
	
	// updatetable per visualizzare gli elementi già presenti nel db nella Jtable
	void updateTable() {
		ArrayList<NucleoFamiliare> lutente;
		
		try {
			if(account.getTipo().equals("admin")) {
				lutente = Polo.getAllNuclei();
			}else {
				lutente = Polo.getNuclei(Integer.toString(account.getIdPolo()));
			}
			for(NucleoFamiliare u : lutente) {
				modell.addRow(new Object[] { u.getIdNucleo(), u.getNome(), u.getIsee() });
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
