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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class ConfermaCarichiAdmin extends JFrame {

	private JPanel contentPane;
	Account account;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel modell;
	Integer id_carico=0;
	private JButton confermaButton;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfermaCarichiAdmin frame = new ConfermaCarichiAdmin();
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
	public ConfermaCarichiAdmin(Account account) {
		this.account=account;
		setTitle("Conferma Carichi Admin");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 438);
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
		indietroButton.setBounds(10, 360, 120, 28);
		contentPane.add(indietroButton);
		
		JLabel confermaCarichiAdminLabel = new JLabel("Conferma Carichi Admin");
		confermaCarichiAdminLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		confermaCarichiAdminLabel.setBounds(10, 11, 334, 37);
		contentPane.add(confermaCarichiAdminLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 616, 256);
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
				false, false, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		confermaButton = new JButton("Conferma");
		confermaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Distributore.confermaSpedizione(id_carico);
					JOptionPane.showMessageDialog(null, "Carico Confermato", "Info", JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidPrimaryKeyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		confermaButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		confermaButton.setBounds(501, 360, 120, 28);
		contentPane.add(confermaButton);
		
		// valori presi con il click sulla Jtable
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					// Ottieni i valori dalla riga selezionata
					id_carico = (Integer) table.getValueAt(selectedRow, 0);
				}
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

	// updatetable per visualizzare gli elementi già presenti nel db nella Jtable
	private void updateTable() {
		try {
			int id = Distributore.getIdMagazzino(account);
			ArrayList<Carico> carichi = Distributore.getEveryCarichi(id, "del");

			for (Carico u : carichi) {
				modell.addRow(new Object[] { u.getId(), u.getQuantita(), u.getStato(), u.getData() });
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
