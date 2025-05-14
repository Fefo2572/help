package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegistraNucleoFamiliare extends JFrame {

	private JPanel contentPane;
	private JTextField nucleoField;
	private JTextField iseeField;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistraNucleoFamiliare frame = new RegistraNucleoFamiliare();
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
	public RegistraNucleoFamiliare(Account account) {
		String nome=account.getNome();
		setTitle("Registra Nucleo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 349);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Nucleolabel = new JLabel("Nome Nucleo Familiare");
		Nucleolabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		Nucleolabel.setBounds(10, 64, 201, 30);
		contentPane.add(Nucleolabel);
		
		nucleoField = new JTextField();
		nucleoField.setBounds(10, 93, 228, 30);
		contentPane.add(nucleoField);
		nucleoField.setColumns(10);
		
		JLabel Iseelabel = new JLabel("ISEE");
		Iseelabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		Iseelabel.setBounds(10, 169, 58, 30);
		contentPane.add(Iseelabel);
		
		iseeField = new JTextField();
		iseeField.setBounds(10, 197, 228, 30);
		contentPane.add(iseeField);
		iseeField.setColumns(10);
		
		JButton indietrobutton = new JButton("Indietro");
		indietrobutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPolo mp=new MenuPolo(account);
				mp.setVisible(true);
				dispose();
			}
		});
		indietrobutton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietrobutton.setBounds(10, 276, 120, 28);
		contentPane.add(indietrobutton);
		
		JButton avantibutton = new JButton("Avanti");
		avantibutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Registrazione nucleo
				try {
					String idPolo;
					Integer idPoloNumerico;
					idPoloNumerico = Polo.createNFamiliare(nucleoField.getText(), iseeField.getText(), account.getIdPolo());
					idPolo = idPoloNumerico.toString();
					RegistraUtente ru=new RegistraUtente(account,idPolo);
					ru.setVisible(true);
					dispose();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			}
		});
		avantibutton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		avantibutton.setBounds(419, 276, 120, 28);
		contentPane.add(avantibutton);
		
		JLabel titololabel = new JLabel("Informazioni Nucleo Familiare");
		titololabel.setFont(new Font("Arial Black", Font.BOLD, 28));
		titololabel.setBounds(10, 11, 529, 30);
		contentPane.add(titololabel);
	}
}
