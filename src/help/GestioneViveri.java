package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GestioneViveri extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField idciboField;
	private JTextField nomeciboField;
	private JTextField F_adultoField;
	private JTextField F_bambinoField;
	private JTextField F_neonatoField;
	private DefaultTableModel modell;
	Account account;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestioneViveri frame = new GestioneViveri();
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
	public GestioneViveri(Account account) {
		this.account=account;
		setTitle("Gestione Viveri");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 773, 514);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel viveriapprovatiLabel = new JLabel("Lista Viveri Approvati");
		viveriapprovatiLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		viveriapprovatiLabel.setBounds(10, 11, 273, 42);
		contentPane.add(viveriapprovatiLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(304, 22, 443, 442);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id_Cibo", "Nome_Cibo", "F_Adulto", "F_Bambino", "F_Neonato"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class, Integer.class, Integer.class
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
		
		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            // Ottieni i valori dalla riga selezionata
		            Integer idCibo = (Integer) table.getValueAt(selectedRow, 0);
		            String nomeCibo = (String) table.getValueAt(selectedRow, 1);
		            Integer fAdulto = (Integer) table.getValueAt(selectedRow, 2);
		            Integer fBambino = (Integer) table.getValueAt(selectedRow, 3);
		            Integer fNeonato = (Integer) table.getValueAt(selectedRow, 4);

		            // Inserisci i valori nei campi di testo
		            idciboField.setText(idCibo.toString());
		            nomeciboField.setText(nomeCibo);
		            F_adultoField.setText(fAdulto.toString());
		            F_bambinoField.setText(fBambino.toString());
		            F_neonatoField.setText(fNeonato.toString());
		        }
		    }
		});
		
		
		//aggiornamento/visualizzazione valori già presenti nel db
		modell = (DefaultTableModel) table.getModel();
		updateTable();
		
		JLabel idciboLabel = new JLabel("Id_Cibo");
		idciboLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		idciboLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		idciboLabel.setBounds(32, 76, 73, 22);
		contentPane.add(idciboLabel);
		
		idciboField = new JTextField();
		idciboField.setBounds(115, 78, 168, 22);
		contentPane.add(idciboField);
		idciboField.setColumns(10);
		
		JLabel nomeciboLabel = new JLabel("Nome_Cibo");
		nomeciboLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		nomeciboLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		nomeciboLabel.setBounds(10, 122, 93, 22);
		contentPane.add(nomeciboLabel);
		
		nomeciboField = new JTextField();
		nomeciboField.setBounds(115, 124, 168, 22);
		contentPane.add(nomeciboField);
		nomeciboField.setColumns(10);
		
		JLabel F_ALabel = new JLabel("F_Adulto");
		F_ALabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		F_ALabel.setHorizontalAlignment(SwingConstants.TRAILING);
		F_ALabel.setBounds(10, 170, 95, 22);
		contentPane.add(F_ALabel);
		
		F_adultoField = new JTextField();
		F_adultoField.setBounds(115, 172, 168, 22);
		contentPane.add(F_adultoField);
		F_adultoField.setColumns(10);
		
		JLabel F_BLabel = new JLabel("F_Bambino");
		F_BLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		F_BLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		F_BLabel.setBounds(10, 218, 95, 22);
		contentPane.add(F_BLabel);
		
		F_bambinoField = new JTextField();
		F_bambinoField.setBounds(115, 220, 168, 22);
		contentPane.add(F_bambinoField);
		F_bambinoField.setColumns(10);
		
		JLabel F_NLabel = new JLabel("F_Neonato");
		F_NLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		F_NLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		F_NLabel.setBounds(10, 267, 95, 22);
		contentPane.add(F_NLabel);
		
		F_neonatoField = new JTextField();
		F_neonatoField.setBounds(115, 269, 168, 22);
		contentPane.add(F_neonatoField);
		F_neonatoField.setColumns(10);
		
		JButton aggiungiButton = new JButton("Aggiungi");
		aggiungiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//aggiunta di una nuova tipologia di cibo nella lista
				PannelloDiControllo pa=new PannelloDiControllo();
				String idcibo=idciboField.getText();
				String nomecibo=nomeciboField.getText();
				String f_adulto=F_adultoField.getText();
				String f_bambino=F_bambinoField.getText();
				String f_neonato=F_neonatoField.getText();
				try {
					int ritorno=pa.creaCibo(Integer.parseInt(idcibo),nomecibo,Integer.parseInt( f_adulto),Integer.parseInt(f_bambino),Integer.parseInt(f_neonato));
					JOptionPane.showMessageDialog(null,"Record aggiunto corretamente");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				modell = (DefaultTableModel) table.getModel();
				modell.addRow(new Object[] { Integer.parseInt(idcibo),nomecibo,Integer.parseInt( f_adulto),Integer.parseInt(f_bambino),Integer.parseInt(f_neonato)});
				DefaultTableModel modell = (DefaultTableModel) table.getModel();
				//modell.setRowCount(0);
				idciboField.setText("");
				nomeciboField.setText("");
				F_adultoField.setText("");
				F_bambinoField.setText("");
				F_neonatoField.setText("");
			}
		});
		aggiungiButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		aggiungiButton.setBounds(10, 397, 120, 28);
		contentPane.add(aggiungiButton);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdmin ma=new MenuAdmin(account);
				ma.setVisible(true);
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 436, 120, 28);
		contentPane.add(indietroButton);
		
		JButton eliminaButton = new JButton("Elimina");
		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//eliminazione di un record dal db e dalla Jtable
				int selectedRow = table.getSelectedRow();
				if(selectedRow!=-1) {
					try {
						int rowIndex = table.getSelectedRow();
						String idcibo = (String) table.getValueAt(rowIndex, 0).toString();
						Integer id_c=Integer.parseInt(idcibo);
						PannelloDiControllo.deleteFromTipologiaViveri(id_c);
						modell.removeRow(selectedRow);
						JOptionPane.showMessageDialog(null,"Record eliminato corretamente");
						
						DefaultTableModel modell = (DefaultTableModel) table.getModel();
						//modell.setRowCount(0);
						idciboField.setText("");
						nomeciboField.setText("");
						F_adultoField.setText("");
						F_bambinoField.setText("");
						F_neonatoField.setText("");
						
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
		eliminaButton.setBounds(163, 393, 120, 28);
		contentPane.add(eliminaButton);
		
		//modifica
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            Integer idCibo = Integer.parseInt(idciboField.getText());
		            String nomeCibo = nomeciboField.getText();
		            Integer fAdulto = Integer.parseInt(F_adultoField.getText());
		            Integer fBambino = Integer.parseInt(F_bambinoField.getText());
		            Integer fNeonato = Integer.parseInt(F_neonatoField.getText());

		            try {
		                PannelloDiControllo.updateListaViveri(idCibo, nomeCibo, fAdulto, fBambino, fNeonato);
		                JOptionPane.showMessageDialog(null, "Record aggiornato correttamente");
		                // Aggiorna i valori nella tabella
		                table.setValueAt(idCibo, selectedRow, 0);
		                table.setValueAt(nomeCibo, selectedRow, 1);
		                table.setValueAt(fAdulto, selectedRow, 2);
		                table.setValueAt(fBambino, selectedRow, 3);
		                table.setValueAt(fNeonato, selectedRow, 4);
		                
		                DefaultTableModel modell = (DefaultTableModel) table.getModel();
						//modell.setRowCount(0);
						idciboField.setText("");
						nomeciboField.setText("");
						F_adultoField.setText("");
						F_bambinoField.setText("");
						F_neonatoField.setText("");
		                
		            } catch (ClassNotFoundException | SQLException e1) {
		                e1.printStackTrace();
		            }
		        }
		    }
		});
		
		modificaButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		modificaButton.setBounds(163, 436, 120, 28);
		contentPane.add(modificaButton);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
	}
	
	//updatetable per visualizzare gli elementi già presenti nel db nella Jtable
		private void updateTable() {
			try {
				ArrayList<TipologiaViveri> viveri = PannelloDiControllo.getlistaViveri(account.getIdAzienda());
				
				for(TipologiaViveri u : viveri) {
					modell.addRow(new Object[] {u.getId(),u.getNomeCibo(),u.getFabbisognoAdulto(),u.getFabbisognoBambino(),u.getFabbisognoNeonato()});
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
}

/*
 	modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//modifica di un record nel db e nella JTable
				int selectedRow = table.getSelectedRow();
				if(selectedRow!=-1) {
					//int rowIndex = table.getSelectedRow();
					Integer idCibo = (Integer)table.getValueAt(selectedRow, 0);
	                String nomeCibo = (String)table.getValueAt(selectedRow, 1);
	                Integer fAdulto = (Integer)table.getValueAt(selectedRow, 2);
	                Integer fBambino = (Integer)table.getValueAt(selectedRow, 3);
	                Integer fNeonato = (Integer)table.getValueAt(selectedRow, 4);
	                
	                //inserisco i valori di nuovo nei field
	                idciboField.setText(idCibo.toString());
	                nomeciboField.setText(nomeCibo.toString());
	                F_adultoField.setText(fAdulto.toString());
	                F_bambinoField.setText(fBambino.toString());
	                F_neonatoField.setText(fNeonato.toString());
	                
	                
	                try {
	                	PannelloDiControllo.updateListaViveri(idCibo,nomeCibo,fAdulto,fBambino,fNeonato);
						JOptionPane.showMessageDialog(null,"Record aggiunto corretamente");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					modell = (DefaultTableModel) table.getModel();
					modell.addRow(new Object[] { idCibo,nomeCibo,fAdulto,fBambino,fNeonato});  
				}
			}
		});
 */


