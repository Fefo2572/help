package help;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField user;
	private JPasswordField pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen();
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
	public LoginScreen() {
		setTitle("Help's Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 325, 409);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setIcon(
				new ImageIcon("C:\\Users\\39338\\eclipse-workspace\\Help\\src\\Icon\\immagine 2 senza sfondo.png"));
		// lblNewLabel_2.setBounds(, 0, 325, 409);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Help's\r\n System");
		lblNewLabel_3.setForeground(new Color(49, 182, 210));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Artifakt Element Book", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(91, 360, 133, 26);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(LoginScreen.class.getResource("/Icon/immagine 2 senza sfondo.png")));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(0, 0, 325, 409);
		panel.add(lblNewLabel_6);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(49, 182, 210));
		panel_1.setBounds(324, 0, 310, 409);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(LoginScreen.class.getResource("/Icon/pass-logo-Mod.png")));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(223, 215, 38, 35);
		panel_1.add(lblNewLabel_5);

		JLabel lblIconUser = new JLabel("");
		lblIconUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconUser.setIcon(new ImageIcon(LoginScreen.class.getResource("/Icon/logo-userMM.jpg")));
		lblIconUser.setBounds(223, 127, 38, 35);
		panel_1.add(lblIconUser);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 28));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(92, 23, 116, 40);
		panel_1.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Username\r\n");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(52, 101, 105, 22);
		panel_1.add(lblNewLabel_1);

		user = new JTextField();
		user.setBounds(61, 127, 200, 35);
		panel_1.add(user);
		user.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(42, 190, 105, 22);
		panel_1.add(lblNewLabel_1_1);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//Trasformo la password
	                char[] password = pass.getPassword();
	                String passwordString = new String(password);
					Account accountLogin = AccountManager.setCredentials(user.getText(), passwordString);
					if(accountLogin.getTipo().equals("polo")) {
						MenuPolo mp=new MenuPolo(accountLogin);
						mp.setVisible(true);
						dispose();
					}else if(accountLogin.getTipo().equals("azienda")) {
						MenuAzienda ma=new MenuAzienda(accountLogin);
						ma.setVisible(true);
						dispose();
					}else if(accountLogin.getTipo().equals("diocesi")) {
						MenuDiocesi md=new MenuDiocesi(accountLogin);
						md.setVisible(true);
						dispose();
					}else {
						MenuAdmin ma=new MenuAdmin(accountLogin);
						ma.setVisible(true);
						dispose();
					}
					
					System.out.println("Ciao " + accountLogin.getNome());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Si è verificato un errore!", "Errore",
							JOptionPane.ERROR_MESSAGE);
					System.out.println(e);
				}
			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(10, 301, 116, 25);
		panel_1.add(btnNewButton);

		pass = new JPasswordField();
		pass.setBounds(61, 215, 200, 35);
		panel_1.add(pass);

		JButton btnRegister = new JButton("Registrati");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				// collegamento con il secondo jframe Registra un account
				Register reg = new Register();
				reg.setVisible(true);
				dispose();
			}
		});
		btnRegister.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnRegister.setBounds(184, 301, 116, 25);
		panel_1.add(btnRegister);

		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setIcon(new ImageIcon(LoginScreen.class.getResource("/Icon/logo-userMM.jpg")));
		lblNewLabel_4.setBounds(173, 132, 47, 20);
		panel_1.add(lblNewLabel_4);

		JButton recuperaPasswordbutton = new JButton("Recupera Password");
		recuperaPasswordbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecuperaCredenziali creden = new RecuperaCredenziali();
				creden.setVisible(true);
				dispose();
			}
		});
		recuperaPasswordbutton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		recuperaPasswordbutton.setBounds(61, 347, 200, 28);
		panel_1.add(recuperaPasswordbutton);
	}
}
