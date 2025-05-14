package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GestionePoli extends JFrame {

	Account account;
	private JPanel contentPane;
	private JTextField idField;
	private JTextField nomeField;
	private JTextField cittaField;
	private JTextField id_diocesiField;
	private JTextField statoField;
	private JTable table;
	private DefaultTableModel modell;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionePoli frame = new GestionePoli();
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
	public GestionePoli(Account account) {
		setResizable(false);
		this.account=account;
		setTitle("Gestione Poli");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 520);
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
		indietroButton.setBounds(10, 442, 120, 28);
		contentPane.add(indietroButton);
		
		JLabel listapoliLabel = new JLabel("Lista Poli");
		listapoliLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		listapoliLabel.setBounds(10, 11, 141, 35);
		contentPane.add(listapoliLabel);
		
		JLabel idLabel = new JLabel("Id");
		idLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		idLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		idLabel.setBounds(25, 77, 37, 22);
		contentPane.add(idLabel);
		
		idField = new JTextField();
		idField.setFont(new Font("Arial Black", Font.PLAIN, 15));
		idField.setBounds(72, 77, 168, 22);
		contentPane.add(idField);
		idField.setColumns(10);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		nomeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		nomeLabel.setBounds(0, 120, 62, 22);
		contentPane.add(nomeLabel);
		
		nomeField = new JTextField();
		nomeField.setBounds(72, 122, 168, 22);
		contentPane.add(nomeField);
		nomeField.setColumns(10);
		
		JLabel cittalabel = new JLabel("Città");
		cittalabel.setHorizontalAlignment(SwingConstants.TRAILING);
		cittalabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		cittalabel.setBounds(10, 167, 52, 22);
		contentPane.add(cittalabel);
		
		cittaField = new JTextField();
		cittaField.setBounds(72, 169, 168, 22);
		contentPane.add(cittaField);
		cittaField.setColumns(10);
		
		JLabel iddiocesiLabel = new JLabel("Id_Diocesi");
		iddiocesiLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		iddiocesiLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		iddiocesiLabel.setBounds(10, 216, 105, 28);
		contentPane.add(iddiocesiLabel);
		
		id_diocesiField = new JTextField();
		id_diocesiField.setBounds(125, 222, 115, 22);
		contentPane.add(id_diocesiField);
		id_diocesiField.setColumns(10);
		
		JLabel statoLabel = new JLabel("Stato");
		statoLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		statoLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		statoLabel.setBounds(10, 265, 62, 22);
		contentPane.add(statoLabel);
		
		statoField = new JTextField();
		statoField.setBounds(82, 268, 168, 22);
		contentPane.add(statoField);
		statoField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(275, 26, 403, 444);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Nome", "Citt\u00E0", "Id_Diocesi", "Stato"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, Integer.class, String.class
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
		
		//valori presi con il click sulla Jtable
				table.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent e) {
				        int selectedRow = table.getSelectedRow();
				        if (selectedRow != -1) {
				            // Ottieni i valori dalla riga selezionata
				            Integer id = (Integer) table.getValueAt(selectedRow, 0);
				            String nomep = (String) table.getValueAt(selectedRow, 1);
				            String cittap=(String) table.getValueAt(selectedRow, 2);
				            Integer id_diocesi = (Integer) table.getValueAt(selectedRow, 3);
				            String stato=(String) table.getValueAt(selectedRow, 4);

				            // Inserisci i valori nei campi di testo
				            idField.setText(id.toString());
				            nomeField.setText(nomep);
				            cittaField.setText(cittap);
				            id_diocesiField.setText(id_diocesi.toString());
				            statoField.setText(stato);
				        }
				    }
				});
		
		
		//aggiornamento dei valori della tabella presi dal DB
		modell = (DefaultTableModel) table.getModel();
		updateTable();
		
		
		JButton eliminaButton = new JButton("Elimina");
		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//eliminazione di un record dal db e dalla Jtable
				int selectedRow = table.getSelectedRow();
				if(selectedRow!=-1) {
					try {
						int rowIndex = table.getSelectedRow();
						String idPolo = (String) table.getValueAt(rowIndex, 0).toString();
						Integer id=Integer.parseInt(idPolo);
						PannelloDiControllo.deleteFromPolo(id);
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
			}
		});
		eliminaButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		eliminaButton.setBounds(10, 403, 120, 28);
		contentPane.add(eliminaButton);
		
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectedRow = table.getSelectedRow();
				 if (selectedRow != -1) {
			            Integer id = Integer.parseInt(idField.getText());
			            String nome = nomeField.getText();
			           String citta=cittaField.getText();
			           Integer id_dio = Integer.parseInt( id_diocesiField.getText());
			           String stato=statoField.getText();
			            try {
			                PannelloDiControllo.updateListaPolo(id, nome, citta,id_dio,stato);
			                JOptionPane.showMessageDialog(null, "Record aggiornato correttamente");
			                // Aggiorna i valori nella tabella
			                table.setValueAt(id, selectedRow, 0);
			                table.setValueAt(nome, selectedRow, 1);
			                table.setValueAt(citta, selectedRow, 2);
			                table.setValueAt(id_dio, selectedRow, 3);
			                table.setValueAt(stato, selectedRow, 4);
			            } catch (ClassNotFoundException | SQLException e1) {
			                e1.printStackTrace();
			            }
			        }
				 DefaultTableModel modell = (DefaultTableModel) table.getModel();
				 idField.setText("");
				 nomeField.setText("");
				 cittaField.setText("");
				 id_diocesiField.setText("");
				 statoField.setText("");
			}
		});
		modificaButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		modificaButton.setBounds(140, 403, 120, 28);
		contentPane.add(modificaButton);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
	}
	
	//updatetable per visualizzare gli elementi già presenti nel db nella Jtable
	private void updateTable() {
		try {
			ArrayList<Polo> poli = PannelloDiControllo.getlistaPoli();
			
			for(Polo u : poli) {
				modell.addRow(new Object[] {u.getId(),u.getNome(),u.getCitta(),u.getId_Diocesi(),u.getStato()});
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
}


