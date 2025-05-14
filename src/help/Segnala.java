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
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class Segnala extends JFrame {

	private JPanel contentPane;
	Account account;
	private JTable table;
	private JTextField descrizioneField;
	private DefaultTableModel modell;
	int idritorno=0;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Segnala frame = new Segnala();
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
	public Segnala(Account account) {
		setResizable(false);
		setTitle("Segnala Errori");
		this.account=account;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 628, 440);
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
		indietroButton.setBounds(10, 362, 120, 28);
		contentPane.add(indietroButton);
		
		JLabel creaErroriLabel = new JLabel("Segnala Errori");
		creaErroriLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		creaErroriLabel.setBounds(10, 11, 192, 40);
		contentPane.add(creaErroriLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(266, 29, 336, 361);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Descrizione"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		//aggiornamento/visualizzazione valori già presenti nel db
		modell = (DefaultTableModel) table.getModel();
		updateTable();
		
		//valori presi con il click sulla Jtable
				table.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent e) {
				        int selectedRow = table.getSelectedRow();
				        if (selectedRow != -1) {
				            // Ottieni i valori dalla riga selezionata
				            Integer id = (Integer) table.getValueAt(selectedRow, 0);
				            String descrizione = (String) table.getValueAt(selectedRow, 1);

				            // Inserisci i valori nei campi di testo
				            //idField.setText(id.toString());
				            descrizioneField.setText(descrizione);
				        }
				    }
				});
		
		
		
		JLabel descrizioneLabel = new JLabel("Descrizione");
		descrizioneLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		descrizioneLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		descrizioneLabel.setBounds(10, 72, 106, 28);
		contentPane.add(descrizioneLabel);
		
		descrizioneField = new JTextField();
		descrizioneField.setBounds(10, 97, 239, 105);
		contentPane.add(descrizioneField);
		descrizioneField.setColumns(10);
		
		JButton aggiungiButton = new JButton("Aggiungi");
		aggiungiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String id= idField.getText();
				String descrizione=descrizioneField.getText();
				try{
					idritorno=Polo.createErrore(descrizione,account.getIdPolo());
					JOptionPane.showMessageDialog(null, "Segnalazione Registrata correttamente\n");
				}catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				modell = (DefaultTableModel) table.getModel();
				modell.addRow(new Object[] { idritorno,descrizione });
				
				//idField.setText("");
				descrizioneField.setText("");
			}
		});
		aggiungiButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		aggiungiButton.setBounds(10, 258, 120, 28);
		contentPane.add(aggiungiButton);
		
		JButton btnNewButton = new JButton("Elimina");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//eliminazione di un record dal db e dalla Jtable
				int selectedRow = table.getSelectedRow();
				if(selectedRow!=-1) {
					try {
						int rowIndex = table.getSelectedRow();
						String idErrore = (String) table.getValueAt(rowIndex, 0).toString();
						Integer id=Integer.parseInt(idErrore);
						Polo.deleteFromErrore(id);
						modell.removeRow(selectedRow);
						JOptionPane.showMessageDialog(null,"Record eliminato corretamente");
					}catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				DefaultTableModel modell = (DefaultTableModel) table.getModel();
				descrizioneField.setText("");
			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(140, 258, 120, 28);
		contentPane.add(btnNewButton);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
	}
	
	
	//updatetable per visualizzare gli elementi già presenti nel db nella Jtable
	private void updateTable() {
		try {
			ArrayList<Errore> errori = Polo.getErrori(account.getIdPolo());
			
			for(Errore u : errori) {
				modell.addRow(new Object[] {u.getId(),u.getDescrizione()});
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	

}
