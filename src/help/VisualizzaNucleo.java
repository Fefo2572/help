package help;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VisualizzaNucleo extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private int id;
	private int id_polo;
	private String nome;
	private float isee;
	private String stringaid;
	private JButton indietroButton;
	private JButton avantiButton;
	
	private Account account;
	DefaultTableModel model;
	private JTextField nomeNucleoField;
	private JTextField iseeField;
	private DefaultTableModel modell;
	private JScrollPane scrollPane;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizzaNucleo frame = new VisualizzaNucleo();
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
	public VisualizzaNucleo(Account account) {
		this.account = account;
		setTitle("Visualizza Nucleo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 926, 572);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setBounds(10, 45, 46, -33);
		contentPane.add(table);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 890, 298);
		contentPane.add(scrollPane);
		
		JLabel nomeNucleoLabel = new JLabel("Nome Nucleo");
		nomeNucleoLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		nomeNucleoLabel.setBounds(10, 335, 120, 22);
		contentPane.add(nomeNucleoLabel);
		
		nomeNucleoField = new JTextField();
		nomeNucleoField.setBounds(132, 337, 168, 22);
		contentPane.add(nomeNucleoField);
		nomeNucleoField.setColumns(10);
		
		JLabel iseeLabel = new JLabel("ISEE");
		iseeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		iseeLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		iseeLabel.setBounds(49, 368, 72, 22);
		contentPane.add(iseeLabel);
		
		iseeField = new JTextField();
		iseeField.setBounds(132, 370, 168, 22);
		contentPane.add(iseeField);
		iseeField.setColumns(10);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Id_Polo", "Nome", "ISEE"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Integer.class, String.class, Float.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			
			 @Override
	         public boolean isCellEditable(int row, int column) {
	             return false; // Disabilita l'editing delle celle
	         }
		});
		
		
		//valori presi con il click sulla Jtable
				table_1.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent e) {
				        int selectedRow = table_1.getSelectedRow();
				        if (selectedRow != -1) {
				            // Ottieni i valori dalla riga selezionata
				            Integer id = (Integer) table_1.getValueAt(selectedRow, 0);
				            String nome = (String) table_1.getValueAt(selectedRow, 2);
				            Float isee=(Float) table_1.getValueAt(selectedRow, 3);  
				            
				            // Inserisci i valori nei campi di testo
				            nomeNucleoField.setText(nome);
				            iseeField.setText(isee.toString());
				        }
				    }
				});
				
		
		//aggiornameto tabella prendendo i valori dal DB
		modell = (DefaultTableModel) table_1.getModel();
		updateTable();
		
		indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(account.getTipo().equals("polo")) {
					MenuPolo mp=new MenuPolo(account);
					mp.setVisible(true);
					dispose();
				}else {
					MenuAdmin ma=new MenuAdmin(account);
					ma.setVisible(true);
					dispose();
				}
				
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 494, 120, 28);
		contentPane.add(indietroButton);
		
		
		avantiButton = new JButton("Avanti");
		avantiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table_1.getSelectedRow();
				Integer idNucleoInteger = (Integer) table_1.getValueAt(rowIndex, 0);
				String idNucleoStringa = idNucleoInteger.toString();
				
				RegistraUtente ru=new RegistraUtente(account, idNucleoStringa);
				ru.setVisible(true);
				dispose();
			}
		});
		avantiButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		avantiButton.setBounds(780, 494, 120, 28);
		contentPane.add(avantiButton);
		
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_1.getSelectedRow();
				 if (selectedRow != -1) {
					 	String id =  table_1.getValueAt(selectedRow, 0).toString();
			            String nome = nomeNucleoField.getText();
			            String isee=iseeField.getText();
			            Float iseep=Float.parseFloat(isee);
			            try {
			                Polo.updateNucleoFamiliare(Integer.parseInt(id), nome, iseep);
			                JOptionPane.showMessageDialog(null, "Record aggiornato correttamente");
			                // Aggiorna i valori nella tabella
			    
			                table_1.setValueAt(nome, selectedRow, 2);
			                table_1.setValueAt(iseep, selectedRow, 3);
			            } catch (ClassNotFoundException | SQLException e1) {
			                e1.printStackTrace();
			            }
			        }
				modell = (DefaultTableModel) table_1.getModel();
				nomeNucleoField.setText("");
				iseeField.setText("");
			}
		});
		modificaButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		modificaButton.setBounds(10, 418, 120, 28);
		contentPane.add(modificaButton);
		
		JButton eliminaButton = new JButton("Elimina");
		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_1.getSelectedRow();
				if(selectedRow!=-1) {
					try {
						int rowIndex = table_1.getSelectedRow();
						String idPolo = (String) table_1.getValueAt(rowIndex, 0).toString();
						Integer id=Integer.parseInt(idPolo);
						Polo.deleteFromPolo(id);
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
				modell = (DefaultTableModel) table_1.getModel();
				nomeNucleoField.setText("");
				iseeField.setText("");
			}
		});
		eliminaButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		eliminaButton.setBounds(151, 418, 120, 28);
		contentPane.add(eliminaButton);
		
		
		
		/*
		try {
			String query="SELECT * FROM nucleo_familiare";
			DatabaseManager dbm=new DatabaseManager();
			dbm.connect();
			ResultSet result=dbm.executeQuery(query);
			while(result.next()) {
				id=result.getInt("id");
				id_polo=result.getInt("id_Polo");
				nome=result.getString("nome");
				isee=result.getFloat("isee");
			}
		}catch(ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		DefaultTableModel model = (DefaultTableModel) table_1.getModel();
		model.addRow(new Object[] { id, id_polo, nome, isee});
		*/
		
		
		//riga cliccabile
		table_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Doppio clic sulla riga
                    int selectedRow = table_1.getSelectedRow();
                    // Puoi ottenere i valori delle celle selezionate con:
                    // Object idValue = table_1.getValueAt(selectedRow, 0);
                    // Object idPoloValue = table_1.getValueAt(selectedRow, 1);
                    // Object nomeValue = table_1.getValueAt(selectedRow, 2);
                    // Object iseeValue = table_1.getValueAt(selectedRow, 3);
                    // Esegui l'azione desiderata con i valori ottenuti
                }
            }
        });
		//
		//stringaid=String.valueOf(id_polo);
		
		
	}
	
	void updateTable() {
		ArrayList<NucleoFamiliare> lutente;
		
		try {
			if(account.getTipo().equals("admin")) {
				lutente = Polo.getAllNuclei();
			}else {
				lutente = Polo.getNuclei(Integer.toString(account.getIdPolo()));
			}
			for(NucleoFamiliare u : lutente) {
				modell.addRow(new Object[] { u.getIdNucleo(), u.getIdPolo(), u.getNome(), u.getIsee() });
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
