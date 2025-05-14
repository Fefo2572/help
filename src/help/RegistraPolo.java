package help;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import java.util.TreeMap;
import java.sql.*;
import java.awt.EventQueue;
import javax.swing.JComboBox; // menu a tendina
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class RegistraPolo extends JFrame {

	private JPanel contentPane;
	private JTextField poloField;
	private JTextField cittaField;
	private int id2=0;
	
	private TreeMap<Integer,String> listadiocesi=new TreeMap<>();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistraPolo frame = new RegistraPolo();
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
	public RegistraPolo(Account nuovoAccount) {
		setResizable(false);
		setTitle("Registra Polo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 442);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(49, 182, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(" Informazioni Polo Territoriale");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 28));
		lblNewLabel.setBounds(10, 11, 499, 46);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 226, 237));
		panel.setBounds(10, 68, 542, 324);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Nome Polo");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 37, 125, 28);
		panel.add(lblNewLabel_1);
		
		poloField = new JTextField();
		poloField.setBounds(10, 65, 220, 30);
		panel.add(poloField);
		poloField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Città");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(10, 144, 62, 28);
		panel.add(lblNewLabel_2);
		
		cittaField = new JTextField();
		cittaField.setBounds(10, 173, 220, 30);
		panel.add(cittaField);
		cittaField.setColumns(10);
		
		JButton indietrobutton_3 = new JButton("Indietro");
		indietrobutton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register r=new Register();
				r.setVisible(true);
				dispose();
			}
		});
		indietrobutton_3.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietrobutton_3.setBounds(10, 285, 120, 28);
		panel.add(indietrobutton_3);
		
		//combobox menu a tendina
		
		
		String query="SELECT id,nome FROM diocesi";
		DatabaseManager dbm=new DatabaseManager();
		
		
		try {
			dbm.connect();
			ResultSet result=dbm.executeQuery(query);
			while(result.next()){
				int id= result.getInt("id");
				String nome=result.getString("nome");
				listadiocesi.put(id, nome);
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		Integer[] options=listadiocesi.keySet().toArray(new Integer[0]);
		DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>(listadiocesi.keySet().toArray(new Integer[0]));
		JComboBox<Integer> menuTendina = new JComboBox<>(model);
		menuTendina.setBackground(new Color(255, 255, 255));
		menuTendina.setBounds(366, 88, 150, 22);
		panel.add(menuTendina);
		
		
		
		//visualizzazione menu a tendina id-nome delle diocesi
		menuTendina.setRenderer(new DefaultListCellRenderer() {
		    @Override
		    public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		        
		        // Ottieni l'ID e il nome corrispondenti all'opzione
		        int id_recuperato = (Integer) value;
		        String nome = listadiocesi.get( id_recuperato);
		        id2=id_recuperato;
		        
		        
		        // Imposta il testo del renderer con l'id e il nome
		        setText( id_recuperato + " - " + nome);
		        
		        return this;
		    }
		});
		
		
		JButton btnNewButton = new JButton("Registrati");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					nuovoAccount.setIdPolo( PDAManager.addPolo(poloField.getText(),cittaField.getText(), id2 ));
					AccountManager.registerAccount(nuovoAccount);
					JOptionPane.showMessageDialog(null, "Account registrato correttamente!", "Informazione", JOptionPane.INFORMATION_MESSAGE);
					//Torna alla schermata di login
					LoginScreen lg = new LoginScreen();
					lg.setVisible(true);
					dispose();
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Si è verificato un errore nel database.", "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (AccountException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Si è verificato un errore!", "Avviso", JOptionPane.WARNING_MESSAGE);
				}
				
				System.out.println("hai selezionato: "+id2);
			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(412, 285, 120, 28);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("Diocesi");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(377, 28, 125, 28);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("di appartenenza");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(366, 46, 150, 28);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(RegistraPolo.class.getResource("/Icon/logo-polo-senzasfondo-R.png")));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(0, 0, 542, 324);
		panel.add(lblNewLabel_5);
	}
}
