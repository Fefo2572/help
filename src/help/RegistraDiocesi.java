package help;

import java.awt.EventQueue;

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
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class RegistraDiocesi extends JFrame {

	private JPanel contentPane;
	private JTextField nome_diocesi;
	private JTextField comune;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistraDiocesi frame = new RegistraDiocesi();
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
	public RegistraDiocesi(Account nuovoAccount) {
		setTitle("Registra Diocesi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 418);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(49, 182, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 226, 237));
		panel.setBounds(10, 68, 537, 300);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel nomeDiocesi = new JLabel(" Nome Diocesi");
		nomeDiocesi.setFont(new Font("Arial Black", Font.PLAIN, 15));
		nomeDiocesi.setBounds(10, 33, 125, 28);
		panel.add(nomeDiocesi);
		
		nome_diocesi = new JTextField();
		nome_diocesi.setBounds(10, 63, 220, 30);
		panel.add(nome_diocesi);
		nome_diocesi.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Comune");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 140, 125, 28);
		panel.add(lblNewLabel_1);
		
		comune = new JTextField();
		comune.setBounds(10, 168, 220, 30);
		panel.add(comune);
		comune.setColumns(10);
		
		JButton indietrobutton_2 = new JButton("Indietro");
		indietrobutton_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietrobutton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register r=new Register();
				r.setVisible(true);
				dispose();
			}
		});
		indietrobutton_2.setBounds(10, 261, 110, 28);
		panel.add(indietrobutton_2);
		
		JButton registratiDiocesi = new JButton("Registrati");
		registratiDiocesi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Se viene fatto click qui, parte la registrazione della Diocesi
				
				try {
					nuovoAccount.setIdDiocesi( PDAManager.addDiocesi(nome_diocesi.getText(),comune.getText() ));
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
		registratiDiocesi.setFont(new Font("Arial Black", Font.PLAIN, 15));
		registratiDiocesi.setBounds(126, 261, 117, 28);
		panel.add(registratiDiocesi);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setIcon(new ImageIcon(RegistraDiocesi.class.getResource("/Icon/senza sofndo diocesi-R.png")));
		lblNewLabel_2.setBounds(0, 0, 537, 300);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("Informazioni Diocesi");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 28));
		lblNewLabel.setBounds(10, 11, 395, 46);
		contentPane.add(lblNewLabel);
	}
}
