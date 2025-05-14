package help;

import java.sql.*;
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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecuperaCredenziali extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Email_recupero;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecuperaCredenziali frame = new RecuperaCredenziali();
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
	public RecuperaCredenziali() {
		setTitle("Credential Recovery");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 404);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(49, 182, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 226, 237));
		panel.setBounds(10, 68, 544, 286);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Password dimenticata..");
		lblNewLabel.setBounds(95, 11, 379, 46);
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 24));

		JLabel lblNewLabel_2 = new JLabel("Email*");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(160, 94, 58, 28);
		panel.add(lblNewLabel_2);

		Email_recupero = new JTextField();
		Email_recupero.setBounds(160, 125, 220, 30);
		panel.add(Email_recupero);
		Email_recupero.setColumns(10);

		JButton indietroLogin_2 = new JButton("Indietro");
		indietroLogin_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroLogin_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen lg = new LoginScreen();
				lg.setVisible(true);
				dispose();
			}
		});
		indietroLogin_2.setBounds(10, 252, 120, 28);
		panel.add(indietroLogin_2);

		JButton btnNewButton = new JButton("Invia");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO Trasformare questo codice in funzione su AccountManager
				/*
				 * try { String query="SeLECT email FROM account WHERE email=?";
				 * Class.forName("com.mysql.jdbc.Driver"); Connection
				 * conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/help","root",""
				 * ); PreparedStatement statement=(PreparedStatement)
				 * conn.prepareStatement(query);
				 * statement.setString(1,Email_recupero.getText()); ResultSet
				 * result=statement.executeQuery(); if(result.next()){
				 * JOptionPane.showMessageDialog(null,"Email Esistente!");
				 * 
				 * }else { JOptionPane.showMessageDialog(null,"Email non trovata...");
				 * conn.close(); } }catch(Exception e2) { System.out.println(e2); }
				 */

				try {

					if(AccountManager.emailChecker(Email_recupero.getText())) {
						//Mail trovata
						JOptionPane.showMessageDialog(null,
								"Ti abbiamo mandato un messaggio nella tua casella di posta elettronica!");
						ResetPassword rp = new ResetPassword(Email_recupero.getText());
						rp.setVisible(true);
						dispose();
					}else {
						//Mail non trovata
						JOptionPane.showMessageDialog(null, "Attenzione! La mail inserita non esiste", "Avviso",
								JOptionPane.WARNING_MESSAGE);
					}					

				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Si è verificato un errore.", "Errore",
							JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Si è verificato un errore.", "Errore",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(160, 166, 220, 28);
		panel.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Recupero Credenziali");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD, 28));
		lblNewLabel_1.setBounds(10, 11, 395, 46);
		contentPane.add(lblNewLabel_1);
	}
}
