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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class InoltraCaricoDiocesi extends JFrame {

	private JPanel contentPane;
	Account account;
	private DefaultTableModel modell;
	private JTable table;
	Integer id=0;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InoltraCaricoDiocesi frame = new InoltraCaricoDiocesi();
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
	public InoltraCaricoDiocesi(Account account) {
		this.account=account;
		setTitle("Inoltra Carico");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 749, 460);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel inoltracaricoLabel = new JLabel("Inoltra Carico");
		inoltracaricoLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		inoltracaricoLabel.setBounds(10, 11, 248, 51);
		contentPane.add(inoltracaricoLabel);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuDiocesi md=new MenuDiocesi(account);
				md.setVisible(true);
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 382, 120, 28);
		contentPane.add(indietroButton);
		
		JButton avantiButton = new JButton("Avanti");
		avantiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CarichiDiocesi cd=new CarichiDiocesi(account,id);
				cd.setVisible(true);
				dispose();
			}
		});
		avantiButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		avantiButton.setBounds(603, 382, 120, 28);
		contentPane.add(avantiButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 58, 713, 271);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Nome", "Citt\u00E0"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class
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
		
		//valori presi con il click sulla Jtable
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
	
	
	//updatetable per visualizzare gli elementi già presenti nel db nella Jtable
			private void updateTable() {
				try {
					ArrayList<Polo> poli = Polo.getlistaPoliFromDiocesi(account.getIdDiocesi());
					
					for(Polo u : poli) {
						modell.addRow(new Object[] {u.getId(),u.getNome(),u.getCitta()});
					}
					
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
}
