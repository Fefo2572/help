package help;

import java.time.ZoneId;
import java.util.Date;
import org.jdatepicker.UtilDateModel;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.sql.*;
import java.awt.EventQueue;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox; // menu a tendina
import java.time.format.DateTimeFormatter;
import org.jdatepicker.JDatePicker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.table.DefaultTableModel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class Donazione extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JDatePicker scadenzaField;
	private JLabel quantitaLabel;
	private JTextField quantitaField;
	private JLabel DataPLabel;
	private JDatePicker dataPField;
	private int id_t;
	private String nomecibo;
	private TreeMap<Integer,String> listaviveri=new TreeMap<>();
	private JComboBox menuTendina;
	private int id2=0;
	private JLabel lblNewLabel_1;
	private JScrollPane scrollPane;
	private DefaultTableModel modell;
	int id_recuperato;
	String nomec;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Distribuzione frame = new Distribuzione();
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
	public Donazione(Account account) {
		setResizable(false);
		setTitle("Distribuzione");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 789, 424);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nuovo Lotto\r\n");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		lblNewLabel.setBounds(10, 11, 162, 32);
		contentPane.add(lblNewLabel);
		
		table = new JTable();
		table.setBounds(791, 228, -246, -202);
		contentPane.add(table);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(295, 11, 468, 367);
		contentPane.add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id_Tipologia", "Tipologia", "Scadenza", "Quantit\u00E0", "Data_Produzione"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.getColumnModel().getColumn(0).setResizable(false);
		table_1.getColumnModel().getColumn(1).setResizable(false);
		table_1.getColumnModel().getColumn(2).setResizable(false);
		table_1.getColumnModel().getColumn(3).setResizable(false);
		table_1.getColumnModel().getColumn(4).setResizable(false);
		
		JLabel scadenzaLabel = new JLabel("Scadenza");
		scadenzaLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		scadenzaLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		scadenzaLabel.setBounds(188, 155, 79, 20);
		contentPane.add(scadenzaLabel);
		
		scadenzaField = new JDatePicker();
		scadenzaField.setBounds(162, 176, 123, 22);
		contentPane.add(scadenzaField);
		
		quantitaLabel = new JLabel("Quantità");
		quantitaLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		quantitaLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		quantitaLabel.setBounds(10, 243, 79, 22);
		contentPane.add(quantitaLabel);
		
		quantitaField = new JTextField();
		quantitaField.setBounds(99, 245, 168, 22);
		contentPane.add(quantitaField);
		quantitaField.setColumns(10);
		
		DataPLabel = new JLabel("Data Produzione");
		DataPLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));
		DataPLabel.setBounds(10, 154, 123, 22);
		contentPane.add(DataPLabel);
		
		dataPField = new JDatePicker();
		dataPField.setBounds(10, 177, 123, 22);
		contentPane.add(dataPField);
		
		
		
		String query = "SELECT tv.id, tv.nome_cibo " +
	               "FROM tipologia_viveri tv " +
	               "JOIN catalogo_azienda c ON tv.id = c.id_tipologia " +
	               "WHERE c.id_azienda = " + account.getIdAzienda();
		
		DatabaseManager dbm=new DatabaseManager();
		
		//Controllo che ci siano elementi nel catalogo
		try {
			dbm.connect();
			int rowCount = dbm.getQueryRowCount(query);
			if(rowCount>0) {
				System.out.println("Distribuzione: Trovati elementi nel catalogo azienda");
			}else {
				JOptionPane.showMessageDialog(null, "Errore Fatale", "Attenzione", JOptionPane.ERROR_MESSAGE);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		try {
			dbm.connect();
			ResultSet result=dbm.executeQuery(query);
			while(result.next()) {
				System.out.println("info Lotto idtipo prima:"+id_t);
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
		menuTendina.setBounds(20, 92, 224, 22);
		contentPane.add(menuTendina);
		
		//visualizzazione menu a tendina id-nome delle diocesi
		menuTendina.setRenderer(new DefaultListCellRenderer() {
		    @Override
		    public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		        
		        // Ottieni l'ID e il nome corrispondenti all'opzione
		       
		       id_recuperato = (Integer) value;
		       //System.out.println("id recuperato"+id_recuperato);
		        nomec = listaviveri.get( id_recuperato);
		        id2=id_recuperato;
		        
		        
		        // Imposta il testo del renderer con l'id e il nome
		        setText( id_recuperato + " - " + nomec);
		        
		        return this;
		    }
		});
		
		
		lblNewLabel_1 = new JLabel("Tipologia Cibo");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(20, 65, 224, 28);
		contentPane.add(lblNewLabel_1);
		
		//inserimento lotto nella tabella
		JButton aggiungiButton = new JButton("Aggiungi");
		aggiungiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Azienda a=new Azienda();
					int idAzienda=account.getIdAzienda();
					int quantita = Integer.parseInt(quantitaField.getText());
					java.util.Date date = ((java.util.GregorianCalendar) scadenzaField.getModel().getValue()).getTime();
					LocalDate scadenza = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					java.util.Date date_1 = ((java.util.GregorianCalendar) dataPField.getModel().getValue()).getTime();
					LocalDate  dataPro= date_1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					try {
						System.out.println("info Lotto idtipo:"+id_t);
						int ritorno=a.creaLotto(id_recuperato,idAzienda,scadenza,quantita,dataPro);
						int ritorno2=Distributore.createCaricoFromLotto(ritorno,1);
					} catch (ClassNotFoundException | ScadenzaException | FutureDateException
							| negativeNumberException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidPrimaryKeyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					modell = (DefaultTableModel) table_1.getModel();
					modell.addRow(new Object[] { id_recuperato,nomec,scadenza,quantitaField.getText(),dataPro });
					
					JOptionPane.showMessageDialog(null, "Lotto inserito correttamente!\n", "Info", JOptionPane.INFORMATION_MESSAGE);
				}catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Si è verificato un errore nel DB", "Avviso", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
				quantitaField.setText("");
				scadenzaField.getModel().setValue(null);
				dataPField.getModel().setValue(null);
				
				
			}
		});
		aggiungiButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		aggiungiButton.setBounds(147, 350, 120, 28);
		contentPane.add(aggiungiButton);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAzienda ma=new MenuAzienda(account);
				ma.setVisible(true);
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 350, 120, 28);
		contentPane.add(indietroButton);
		
	}
}
/* versione grezza
 	java.util.Date date = ((java.util.GregorianCalendar) scadenzaField.getModel().getValue()).getTime();
	LocalDate scadenza = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String datas=scadenza.format(formatter);
					
	java.util.Date date_1 = ((java.util.GregorianCalendar) dataPField.getModel().getValue()).getTime();
	LocalDate  dataPro= date_1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	String dataP=dataPro.format(formatter);
	int idAzienda=account.getIdAzienda();
	String azienda=String.valueOf(idAzienda);
	String quantita=quantitaLabel.getText();
	String tipologia=String.valueOf(id_t);
	String[] colums= {"id","id_tipologia","id_azienda","scadenza","quantita","data_produzione"};
	String[] values= {"",tipologia,azienda,datas,quantita,dataP};
	dbm.insertAutoIncrementRecord("lotto",colums,values);
 
 */



