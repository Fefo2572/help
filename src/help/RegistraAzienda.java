package help;

//import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class RegistraAzienda extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nomeAzienda;
	private JTextField nomeCitta;
	private JTextField nomeNazione;

	/**
	 * Launch the application.
	 */
	
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistraAzienda frame = new RegistraAzienda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
     */
	/**
	 * Create the frame.
	 */
	public RegistraAzienda(Account nuovoAccount) {
		setTitle("Registra Azienda");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 605, 467);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(49, 182, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Informazioni Azienda");
		lblNewLabel.setBounds(10, 11, 395, 46);
		contentPane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 28));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 226, 237));
		panel.setBounds(10, 68, 569, 349);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Nome Azienda");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 21, 125, 28);
		panel.add(lblNewLabel_1);
		
		nomeAzienda = new JTextField();
		nomeAzienda.setBounds(10, 50, 220, 30);
		panel.add(nomeAzienda);
		nomeAzienda.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Città");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(10, 113, 58, 28);
		panel.add(lblNewLabel_2);
		
		nomeCitta = new JTextField();
		nomeCitta.setBounds(10, 140, 220, 30);
		panel.add(nomeCitta);
		nomeCitta.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Nazione");
		lblNewLabel_3.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(10, 202, 81, 28);
		panel.add(lblNewLabel_3);
		
		nomeNazione = new JTextField();
		nomeNazione.setBounds(10, 229, 220, 30);
		panel.add(nomeNazione);
		nomeNazione.setColumns(10);
		
		JButton indietrobutton_1 = new JButton("Indietro");
		indietrobutton_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietrobutton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register r=new Register();
				r.setVisible(true);
				dispose();
			}
		});
		indietrobutton_1.setBounds(10, 310, 110, 28);
		panel.add(indietrobutton_1);
		
		JButton registratiAzienda = new JButton("Registrati");
		registratiAzienda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					nuovoAccount.setIdAzienda( PDAManager.addAzienda(nomeAzienda.getText(),nomeCitta.getText(),nomeNazione.getText() )) ;
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
				
			}
		});
		registratiAzienda.setFont(new Font("Arial Black", Font.PLAIN, 15));
		registratiAzienda.setBounds(130, 310, 120, 28);
		panel.add(registratiAzienda);
		
		JLabel piramideAlimentare = new JLabel("");
		piramideAlimentare.setIcon(new ImageIcon(RegistraAzienda.class.getResource("/Icon/piramide-alimentare-senzasfondo-R.png")));
		piramideAlimentare.setHorizontalAlignment(SwingConstants.TRAILING);
		piramideAlimentare.setBounds(0, 0, 569, 349);
		panel.add(piramideAlimentare);
		
		JLabel foodLabel = new JLabel("Food");
		foodLabel.setFont(new Font("Century Schoolbook", Font.BOLD | Font.ITALIC, 18));
		foodLabel.setHorizontalAlignment(SwingConstants.CENTER);
		foodLabel.setBounds(441, 50, 118, 61);
		panel.add(foodLabel);
		
		JLabel lblNewLabel_4 = new JLabel("Pyramid");
		lblNewLabel_4.setFont(new Font("Century Schoolbook", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(441, 88, 118, 28);
		panel.add(lblNewLabel_4);
	}
}
