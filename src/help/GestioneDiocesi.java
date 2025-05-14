package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GestioneDiocesi extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField idField;
	private JTextField nomeField;
	private JTextField comuneField;
	private DefaultTableModel modell;
	Account account;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestioneDiocesi frame = new GestioneDiocesi();
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
	public GestioneDiocesi(Account account) {
		this.account=account;
		setTitle("Gestione Diocesi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 716, 427);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel listaDiocesiLabel = new JLabel("Lista Diocesi");
		listaDiocesiLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		listaDiocesiLabel.setBounds(10, 21, 172, 32);
		contentPane.add(listaDiocesiLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(290, 28, 400, 350);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Nome", "Comune"
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
		            Integer id = (Integer) table.getValueAt(selectedRow, 0);
		            String nomed = (String) table.getValueAt(selectedRow, 1);
		            String comuned=(String) table.getValueAt(selectedRow, 2);

		            // Inserisci i valori nei campi di testo
		            idField.setText(id.toString());
		            nomeField.setText(nomed);
		            comuneField.setText(comuned);
		           
		        }
		    }
		});
		
		
		//aggiornamento/visualizzazione valori già presenti nel db
		modell = (DefaultTableModel) table.getModel();
		updateTable();
		
		JLabel idLabel = new JLabel("Id");
		idLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		idLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		idLabel.setBounds(32, 86, 45, 22);
		contentPane.add(idLabel);
		
		idField = new JTextField();
		idField.setBounds(87, 88, 168, 22);
		contentPane.add(idField);
		idField.setColumns(10);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		nomeLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		nomeLabel.setBounds(10, 147, 67, 22);
		contentPane.add(nomeLabel);
		
		nomeField = new JTextField();
		nomeField.setBounds(87, 149, 168, 22);
		contentPane.add(nomeField);
		nomeField.setColumns(10);
		
		JLabel comuneLabel = new JLabel("Comune");
		comuneLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		comuneLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		comuneLabel.setBounds(10, 202, 67, 22);
		contentPane.add(comuneLabel);
		
		comuneField = new JTextField();
		comuneField.setBounds(87, 204, 168, 22);
		contentPane.add(comuneField);
		comuneField.setColumns(10);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdmin ma=new MenuAdmin(account);
				ma.setVisible(true);
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 347, 120, 28);
		contentPane.add(indietroButton);
		
		JButton eliminaButton = new JButton("Elimina");
		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//eliminazione di un record dal db e dalla Jtable
				int selectedRow = table.getSelectedRow();
				if(selectedRow!=-1) {
					try {
						int rowIndex = table.getSelectedRow();
						String idDiocesi = (String) table.getValueAt(rowIndex, 0).toString();
						Integer id=Integer.parseInt(idDiocesi);
						PannelloDiControllo.deleteFromDiocesi(id);
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
				idField.setText("");
				nomeField.setText("");
				comuneField.setText("");
			}
		});
		eliminaButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		eliminaButton.setBounds(160, 286, 120, 28);
		contentPane.add(eliminaButton);
		
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  int selectedRow = table.getSelectedRow();
				 if (selectedRow != -1) {
			            Integer id = Integer.parseInt(idField.getText());
			            String nome = nomeField.getText();
			           String comune=comuneField.getText();
			            try {
			                PannelloDiControllo.updateListaDiocesi(id, nome, comune);
			                JOptionPane.showMessageDialog(null, "Record aggiornato correttamente");
			                // Aggiorna i valori nella tabella
			                table.setValueAt(id, selectedRow, 0);
			                table.setValueAt(nome, selectedRow, 1);
			                table.setValueAt(comune, selectedRow, 2);
			            } catch (ClassNotFoundException | SQLException e1) {
			                e1.printStackTrace();
			            }
			        }
				DefaultTableModel modell = (DefaultTableModel) table.getModel();
				idField.setText("");
				nomeField.setText("");
				comuneField.setText("");
			}
		});
		
		modificaButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		modificaButton.setBounds(10, 286, 120, 28);
		contentPane.add(modificaButton);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
	}
	
	//updatetable per visualizzare gli elementi già presenti nel db nella Jtable
			private void updateTable() {
				try {
					ArrayList<Diocesi> diocesi = PannelloDiControllo.getlistaDiocesi(account.getIdAzienda());
					
					for(Diocesi u : diocesi) {
						modell.addRow(new Object[] {u.getId(),u.getNome(),u.getComune()});
					}
					
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
	
	
}
