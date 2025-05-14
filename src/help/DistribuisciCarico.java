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

public class DistribuisciCarico extends JFrame {

	private JPanel contentPane;
	Account account;
	private JTable table;
	private DefaultTableModel modell;
	Integer id_carico=0;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DistribuisciCarico frame = new DistribuisciCarico();
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
	public DistribuisciCarico(Account account,Integer id) {
		this.account=account;
		setTitle("Distribuisci Cibo Famiglia");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 713, 399);
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
		indietroButton.setBounds(10, 321, 120, 28);
		contentPane.add(indietroButton);
		
		JLabel distribuiscicaricoLabel = new JLabel("Distribuisci Carico");
		distribuiscicaricoLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		distribuiscicaricoLabel.setBounds(10, 11, 297, 41);
		contentPane.add(distribuiscicaricoLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 52, 677, 235);
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
		
		// valori presi con il click sulla Jtable
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					// Ottieni i valori dalla riga selezionata
					id_carico = (Integer) table.getValueAt(selectedRow, 0);
					System.out.println("diol:" + id_carico);
				}
			}
		});
		
		
		
		JButton distribuisciButton = new JButton("Distribuisci");
		distribuisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Distributore.distribuisciFamiglia(id,id_carico);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidPrimaryKeyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DistribuzioneException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (negativeNumberException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		distribuisciButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		distribuisciButton.setBounds(546, 321, 141, 28);
		contentPane.add(distribuisciButton);
		
	
		
		
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		
		// Aggiorno la tabella
		modell = (DefaultTableModel) table.getModel();
		updateTable(id);
	}
	
	
	// updatetable per visualizzare gli elementi già presenti nel db nella Jtable
	private void updateTable(Integer id) {
		try {
			ArrayList<Carico> carichi = Distributore.getCarichiForFamiglia(id);

			for (Carico u : carichi) {
				modell.addRow(new Object[] { u.getId(), u.getQuantita(), u.getStato(), u.getData() });
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
