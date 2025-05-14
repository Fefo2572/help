package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuDiocesi extends JFrame {

	private JPanel contentPane;
	Account account;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuDiocesi frame = new MenuDiocesi();
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
	public MenuDiocesi(Account account) {
		this.account=account;
		setResizable(false);
		setTitle("Menu Diocesi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 576);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(47, 79, 79));
		panel.setBounds(0, 0, 291, 537);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MenuDiocesi.class.getResource("/Icon/diocesiMenu-logo-RR.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 260, 189);
		panel.add(lblNewLabel);
		
		JButton logOutButton = new JButton("LogOut");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen lg=new LoginScreen();
				lg.setVisible(true);
				dispose();
			}
		});
		logOutButton.setIcon(new ImageIcon(MenuDiocesi.class.getResource("/Icon/logout-senzasfondo.png")));
		logOutButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		logOutButton.setBounds(10, 487, 273, 39);
		panel.add(logOutButton);
		
		JButton btnNewButton = new JButton("Visualizza Report");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaReport vr=new VisualizzaReport(account);
				vr.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setIcon(new ImageIcon(MenuDiocesi.class.getResource("/Icon/visualizzalogo-senzasfondo.png")));
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(10, 437, 273, 39);
		panel.add(btnNewButton);
		
		JButton visualizzaPoliButton = new JButton("Visualizza Poli");
		visualizzaPoliButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaPoli vp=new VisualizzaPoli(account);
				vp.setVisible(true);
				dispose();
			}
		});
		visualizzaPoliButton.setIcon(new ImageIcon(MenuDiocesi.class.getResource("/Icon/familyiconbutton-logo-removebg-R.png")));
		visualizzaPoliButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		visualizzaPoliButton.setBounds(10, 237, 273, 39);
		panel.add(visualizzaPoliButton);
		
		JButton inoltracaricoButton = new JButton("Inoltra carico");
		inoltracaricoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InoltraCaricoDiocesi cd=new InoltraCaricoDiocesi(account);
				cd.setVisible(true);
				dispose();
			}
		});
		inoltracaricoButton.setIcon(new ImageIcon(MenuDiocesi.class.getResource("/Icon/caricologo-removebg-RR.png")));
		inoltracaricoButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		inoltracaricoButton.setBounds(10, 287, 273, 39);
		panel.add(inoltracaricoButton);
		
		JButton visuallizzacarichiButton = new JButton("Visualizza Carichi");
		visuallizzacarichiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaCarichiDiocesi vcd=new VisualizzaCarichiDiocesi(account);
				vcd.setVisible(true);
				dispose();
			}
		});
		visuallizzacarichiButton.setIcon(new ImageIcon(MenuDiocesi.class.getResource("/Icon/caricologo-removebg-RR.png")));
		visuallizzacarichiButton.setBounds(10, 337, 273, 39);
		panel.add(visuallizzacarichiButton);
		visuallizzacarichiButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		
		JButton confermaCaricoButton = new JButton("Conferma Carichi");
		confermaCaricoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfermaCarichiDiocesi ccd=new ConfermaCarichiDiocesi(account);
				ccd.setVisible(true);
				dispose();
			}
		});
		confermaCaricoButton.setIcon(new ImageIcon(MenuDiocesi.class.getResource("/Icon/modificalogo-senzasfondo.png")));
		confermaCaricoButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		confermaCaricoButton.setBounds(10, 387, 273, 39);
		panel.add(confermaCaricoButton);
		
		JLabel benvenutoLabel = new JLabel("Benvenuto");
		benvenutoLabel.setForeground(new Color(255, 255, 255));
		benvenutoLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		benvenutoLabel.setBounds(301, 66, 112, 31);
		contentPane.add(benvenutoLabel);
		
		JLabel usernameLabel = new JLabel("New label");
		usernameLabel.setForeground(new Color(255, 255, 255));
		usernameLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		usernameLabel.setBounds(408, 66, 140, 31);
		String username=account.getNome();
		usernameLabel.setText(username);
		contentPane.add(usernameLabel);
		
	}
	
	
	
	
}
