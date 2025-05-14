package help;

import java.sql.*;
import javax.swing.ButtonModel;
import javax.swing.ButtonGroup; //jradiobutton group library
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Register extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Nome;
	private JTextField Cognome;
	private JTextField Email;
	private ButtonGroup bg = new ButtonGroup(); // prototipazione metodo jradiobutton group
	private JPasswordField Password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Register() {
		setResizable(false);
		setTitle("Register form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 524);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(49, 182, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Crea account");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 28));
		lblNewLabel.setBounds(10, 11, 314, 46);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 226, 237));
		panel.setBounds(10, 68, 624, 406);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNome.setBounds(20, 11, 58, 28);
		panel.add(lblNome);

		Nome = new JTextField();
		Nome.setBounds(20, 39, 210, 30);
		panel.add(Nome);
		Nome.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Cognome");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(20, 109, 87, 28);
		panel.add(lblNewLabel_1);

		Cognome = new JTextField();
		Cognome.setBounds(20, 136, 210, 30);
		panel.add(Cognome);
		Cognome.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(20, 216, 58, 28);
		panel.add(lblNewLabel_2);

		Email = new JTextField();
		Email.setBounds(20, 243, 210, 30);
		panel.add(Email);
		Email.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(20, 322, 87, 28);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Tipo Account");
		lblNewLabel_4.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(471, 38, 120, 28);
		panel.add(lblNewLabel_4);

		JRadioButton jradioAzienda = new JRadioButton("   Azienda");
		jradioAzienda.setBackground(new Color(173, 226, 237));
		jradioAzienda.setFont(new Font("Arial", Font.BOLD, 16));
		jradioAzienda.setBounds(471, 73, 109, 23);
		panel.add(jradioAzienda);

		JRadioButton JradioDiocesi = new JRadioButton("   Diocesi");
		JradioDiocesi.setBackground(new Color(173, 226, 237));
		JradioDiocesi.setFont(new Font("Arial", Font.BOLD, 16));
		JradioDiocesi.setBounds(471, 112, 109, 23);
		panel.add(JradioDiocesi);

		JRadioButton JradioPolo = new JRadioButton("   Polo");
		JradioPolo.setBackground(new Color(173, 226, 237));
		JradioPolo.setFont(new Font("Arial", Font.BOLD, 16));
		JradioPolo.setBounds(471, 154, 109, 23);
		panel.add(JradioPolo);

		// jradiobutton group
		bg.add(jradioAzienda);
		bg.add(JradioDiocesi);
		bg.add(JradioPolo);

		JButton registratibutton = new JButton("Avanti");
		registratibutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				// button model
				
				//Converto la password in una stringa
                char[] pswrd = Password.getPassword();
                String passwordString = new String(pswrd);
                
				//Controlliamo che tutti i campi siano compilati, successivamente controlliamo se la mail è corretta
				if(Nome.getText().isEmpty()||Cognome.getText().isEmpty()||Email.getText().isEmpty()||passwordString.isEmpty()) {
					//Errore, campi non compilati
					JOptionPane.showMessageDialog(null, "Non hai compilato tutti i campi!", "Avviso", JOptionPane.WARNING_MESSAGE);
				}else if(AccountManager.emailFormatCheck(Email.getText())){
					//Ok, la mail è del formato giusto
					try {
						if(AccountManager.emailChecker(Email.getText())) {
							//Mail già in uso
							JOptionPane.showMessageDialog(null, "Mail in uso!", "Avviso", JOptionPane.WARNING_MESSAGE);
						}else {
							//Ok, la mail non è in uso
							ButtonModel selectedButton = bg.getSelection();
							if (selectedButton != null) {
								//Ok, l'utente ha selezionato un radio button
								//Si inseriscono le informazioni inserite dall'utente in una classe
								//Account newAccount = new Account(Email.getText(),passwordString,Nome.getText(),Cognome.getText(),0,0,0,"act",0);
								AccountManager accMan = new AccountManager(Email.getText(),passwordString,Nome.getText(),Cognome.getText());
								if (selectedButton.equals(jradioAzienda.getModel())) {
									System.out.println("Azienda è selezionato");
									accMan.registraAzienda();
									dispose();
								} else if (selectedButton.equals(JradioDiocesi.getModel())) {
									System.out.println("Diocesi è selezionato");
									accMan.registraDiocesi();
									dispose();
								} else if (selectedButton.equals(JradioPolo.getModel())) {
									System.out.println("Polo è selezionato"); 
									DatabaseManager dbm = new DatabaseManager();
									try {
										dbm.connect();
										int ndiocesi;
										ndiocesi = dbm.getQueryRowCount("SELECT * FROM diocesi");
										if(ndiocesi==0) {
											//Nessuna diocesi
											JOptionPane.showMessageDialog(null, "Attenzione! \nNon esistono diocesi, non è possibile creare un polo!", "Avviso", JOptionPane.WARNING_MESSAGE);
										}else {
											//Diocesi esistenti
											
											accMan.registraPolo();
											
											dispose();
										}
									} catch (SQLException | ClassNotFoundException e1) {
										JOptionPane.showMessageDialog(null, "Si è verificato un errore nel database.", "Errore", JOptionPane.ERROR_MESSAGE);
										e1.printStackTrace();
									}

								}
							} else {
								//Nessun radio button selezionato
								System.out.println("Nessun pulsante selezionato");
								JOptionPane.showMessageDialog(null, "Non hai compilato tutti i campi!", "Avviso", JOptionPane.WARNING_MESSAGE);
							}
						}
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					

				}else {
					//La mail non è del formato corretto
					JOptionPane.showMessageDialog(null, "Inserisci una mail corretta!", "Avviso", JOptionPane.WARNING_MESSAGE);
				}
				

			}
		});
		registratibutton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		registratibutton.setBounds(471, 353, 120, 28);
		panel.add(registratibutton);

		JButton indietroLoginbutton = new JButton("Indietro");
		indietroLoginbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen lg = new LoginScreen();
				lg.setVisible(true);
				dispose();
			}
		});
		indietroLoginbutton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroLoginbutton.setBounds(340, 353, 120, 28);
		panel.add(indietroLoginbutton);

		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Register.class.getResource("/Icon/immagine 2 senza sfondo.png")));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(0, 0, 624, 406);
		panel.add(lblNewLabel_5);

		Password = new JPasswordField();
		Password.setBounds(20, 353, 210, 30);
		panel.add(Password);

	}
}
