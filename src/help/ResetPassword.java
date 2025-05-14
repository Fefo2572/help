package help;

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
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class ResetPassword extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResetPassword frame = new ResetPassword();
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
	public ResetPassword(String email) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 404);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(49, 182, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 226, 237));
		panel.setBounds(10, 11, 522, 343);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Reset Password");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
		lblNewLabel.setToolTipText("");
		lblNewLabel.setBounds(75, 11, 379, 46);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password*");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(149, 75, 102, 28);
		panel.add(lblNewLabel_1);
		
		textField = new JPasswordField();
		textField.setBounds(149, 101, 210, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Conferma Password*");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(149, 172, 184, 28);
		panel.add(lblNewLabel_2);
		
		textField_1 = new JPasswordField();
		textField_1.setBounds(149, 198, 210, 30);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Reset Password");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Azione quando si clicca sul tasto Reset Password
				try {
					AccountManager.resetPassword(textField.getText(),textField_1.getText(), email);
					JOptionPane.showMessageDialog(null, "Operazione completata.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
					//Torna alla schermata di login
					LoginScreen lg = new LoginScreen();
					lg.setVisible(true);
					dispose();
				}catch (AccountException e1){
					JOptionPane.showMessageDialog(null, e1.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1.toString(), "Errore" , JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.BOLD, 15));
		btnNewButton.setBounds(147, 239, 220, 28);
		panel.add(btnNewButton);
		
		JButton indietroLogin_3 = new JButton("Indietro");
		indietroLogin_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen lg=new LoginScreen();
				lg.setVisible(true);
				dispose();
			}
		});
		indietroLogin_3.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroLogin_3.setBounds(10, 309, 120, 28);
		panel.add(indietroLogin_3);
	}
}
