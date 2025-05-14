package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox; // menu a tendina
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class VisualizzaCatalogo extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JComboBox menuTendina;
	private TreeMap<Integer,String> listaviveri=new TreeMap<>();
	int id_recuperato;
	String nomec;
	private int id2=0;
	private int id_t;
	private String nomecibo;
	private DefaultTableModel modell;
	Account account;
	int id_a;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizzaCatalogo frame = new VisualizzaCatalogo();
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
	public VisualizzaCatalogo(Account account) {
		this.account=account;
		//System.out.println(account.getIdAzienda()+" 1");
		setTitle("Visualizza Catalogo");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 697, 436);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAzienda ma=new MenuAzienda(account);
				ma.setVisible(true);
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(76, 358, 120, 28);
		contentPane.add(indietroButton);
		
		JLabel aggiungiProdottoLabel = new JLabel("Aggiungi Prodotto");
		aggiungiProdottoLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		aggiungiProdottoLabel.setBounds(10, 11, 228, 32);
		contentPane.add(aggiungiProdottoLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(270, 53, 401, 333);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id_Cibo", "Nome_Cibo"
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
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		
		//valori gia esistenti nel db
		modell = (DefaultTableModel) table.getModel();
		updateTable();
		
		//lista tipologia viveri
				String query="SELECT id,nome_cibo FROM tipologia_viveri";
				DatabaseManager dbm=new DatabaseManager();
				try {
					//System.out.println(account.getIdAzienda()+" 2");
					dbm.connect();
					ResultSet result=dbm.executeQuery(query);
					while(result.next()) {
						 id_t=result.getInt("id");
						nomecibo=result.getString("nome_cibo");
						listaviveri.put(id_t,nomecibo);
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				Integer[] options=listaviveri.keySet().toArray(new Integer[0]);
				DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>(listaviveri.keySet().toArray(new Integer[0]));
				JComboBox<Integer> menuTendina = new JComboBox<>(model);
				menuTendina.setBounds(14, 88, 224, 22);
				contentPane.add(menuTendina);
				
				//visualizzazione menu a tendina id-nome delle diocesi
				menuTendina.setRenderer(new DefaultListCellRenderer() {
				    @Override
				    public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				        
				        // Ottieni l'ID e il nome corrispondenti all'opzione
				       id_recuperato = (Integer) value;
				        nomec = listaviveri.get( id_recuperato);
				        id2=id_recuperato;
				        
				        
				        // Imposta il testo del renderer con l'id e il nome
				        setText( id_recuperato + " - " + nomec);
				        
				        return this;
				    }
				});
				//
		
			//System.out.println(account.getIdAzienda()+" 3");
		
			id_a=account.getIdAzienda();
		//aggiunto del prodotto selezionato al catalogo
		JButton btnNewButton = new JButton("Aggiungi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					Azienda.addToCatalago(id_a,id_recuperato);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				modell = (DefaultTableModel) table.getModel();
				modell.addRow(new Object[] { id_recuperato,nomec});
			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(10, 319, 120, 28);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Lista Prodotti");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		lblNewLabel.setBounds(277, 20, 394, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tipologia Cibo");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 64, 228, 23);
		contentPane.add(lblNewLabel_1);
		
		JButton eliminaButton = new JButton("Elimina");
		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if(selectedRow!=-1) {
					try {
						//System.out.println(account.getIdAzienda()+" 4");
						int rowIndex = table.getSelectedRow();
						String idcibo = (String) table.getValueAt(rowIndex, 0).toString();
						Integer id_c=Integer.parseInt(idcibo);
						System.out.println(idcibo);
						System.out.println();
						Azienda.deleteFromCatalogo(id_a,id_c);
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
		eliminaButton.setBounds(140, 319, 120, 28);
		contentPane.add(eliminaButton);
		
		
	}
	
	/*
	 * Funzione che aggiorna la lista con le cose già presenti
	 */
	private void updateTable() {
		try {
			ArrayList<TipologiaViveri> viveri = Azienda.getCatalogo(account.getIdAzienda());
			
			for(TipologiaViveri u : viveri) {
				modell.addRow(new Object[] {u.getId(),u.getNomeCibo()});
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}




