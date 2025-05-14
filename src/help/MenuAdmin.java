package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuAdmin extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuAdmin frame = new MenuAdmin();
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
	public MenuAdmin(Account account) {
		String username=account.getNome();
		setTitle("Menu Admin");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 583);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(47, 79, 79));
		panel.setBounds(0, 0, 302, 544);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/logomenuadmin-RR.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 273, 234);
		panel.add(lblNewLabel);
		
		JButton logoutButton = new JButton("LogOut");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen lg=new LoginScreen();
				lg.setVisible(true);
				dispose();
			}
		});
		logoutButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/logout-senzasfondo.png")));
		logoutButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		logoutButton.setBounds(10, 489, 273, 39);
		panel.add(logoutButton);
		
		JButton gestisciDioceciButton = new JButton("Gestisci Diocesi");
		gestisciDioceciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestioneDiocesi gd=new GestioneDiocesi(account);
				gd.setVisible(true);
				dispose();
			}
		});
		gestisciDioceciButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/logodiocesibutton-R.png")));
		gestisciDioceciButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		gestisciDioceciButton.setBounds(10, 389, 273, 39);
		panel.add(gestisciDioceciButton);
		
		JButton gestisciNucleiButton = new JButton("Gestisci Nuclei");
		gestisciNucleiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaNucleo vn=new VisualizzaNucleo(account);
				vn.setVisible(true);
				dispose();
			}
		});
		gestisciNucleiButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/familyiconbutton-logo-removebg-R.png")));
		gestisciNucleiButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		gestisciNucleiButton.setBounds(10, 289, 273, 39);
		panel.add(gestisciNucleiButton);
		
		JButton gestisciViveriButton = new JButton("Gestisci Viveri");
		gestisciViveriButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestioneViveri gv=new GestioneViveri(account);
				gv.setVisible(true);
				dispose();
			}
		});
		gestisciViveriButton.setBounds(10, 439, 273, 39);
		panel.add(gestisciViveriButton);
		gestisciViveriButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/logodistribuzione-senzasfondoprevi.png")));
		gestisciViveriButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		
		JButton gestiscipoloButton = new JButton("Gestisci Poli");
		gestiscipoloButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionePoli gp=new GestionePoli(account);
				gp.setVisible(true);
				dispose();
			}
		});
		gestiscipoloButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/icona_polo-button-removebg-RR.png")));
		gestiscipoloButton.setBounds(10, 339, 273, 39);
		panel.add(gestiscipoloButton);
		gestiscipoloButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		
		JLabel benvenutoLabel = new JLabel("Benvenuto");
		benvenutoLabel.setForeground(new Color(255, 255, 255));
		benvenutoLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		benvenutoLabel.setBounds(312, 27, 101, 28);
		contentPane.add(benvenutoLabel);
		
		JLabel usernameLabel = new JLabel("New label");
		usernameLabel.setForeground(new Color(255, 255, 255));
		usernameLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		usernameLabel.setBounds(423, 30, 183, 23);
		usernameLabel.setText(username);
		contentPane.add(usernameLabel);
		
		JButton visualizzaerroriButton = new JButton("Gestione Errori");
		visualizzaerroriButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/gestioneerroreB-logo-RR.png")));
		visualizzaerroriButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestioneErrori ge=new GestioneErrori(account);
				ge.setVisible(true);
				dispose();
			}
		});
		visualizzaerroriButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		visualizzaerroriButton.setBounds(312, 291, 273, 39);
		contentPane.add(visualizzaerroriButton);
		
		JButton visualizzaReportButton = new JButton("Visualizza Report");
		visualizzaReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaReport vr=new VisualizzaReport(account);
				vr.setVisible(true);
				dispose();
			}
		});
		visualizzaReportButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/visualizzalogo-senzasfondo.png")));
		visualizzaReportButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		visualizzaReportButton.setBounds(312, 341, 273, 39);
		contentPane.add(visualizzaReportButton);
		
		JButton previsioneButton = new JButton("Visualizza Previsione");
		previsioneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaPrevisione vp=new VisualizzaPrevisione(account);
				vp.setVisible(true);
				dispose();
			}
		});
		previsioneButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/previsionelogo-removebg-RR.png")));
		previsioneButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		previsioneButton.setBounds(312, 391, 273, 39);
		contentPane.add(previsioneButton);
		
		JButton visualizzaCarichiButton = new JButton("Visualizza Carichi");
		visualizzaCarichiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaCarichiAdmin vca=new VisualizzaCarichiAdmin(account);
				vca.setVisible(true);
				dispose();
			}
		});
		visualizzaCarichiButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/caricologo-removebg-RR.png")));
		visualizzaCarichiButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		visualizzaCarichiButton.setBounds(312, 441, 273, 39);
		contentPane.add(visualizzaCarichiButton);
		
		JButton ConfermacaricoButton = new JButton("Conferma Carico");
		ConfermacaricoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfermaCarichiAdmin cca=new ConfermaCarichiAdmin(account);
				cca.setVisible(true);
				dispose();
			}
		});
		ConfermacaricoButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/modificalogo-senzasfondo.png")));
		ConfermacaricoButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		ConfermacaricoButton.setBounds(312, 491, 273, 39);
		contentPane.add(ConfermacaricoButton);
		
		JButton inoltracaricoButton = new JButton("Inoltra Carico");
		inoltracaricoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InoltraCaricoAdmin ica=new InoltraCaricoAdmin(account);
				ica.setVisible(true);
				dispose();
			}	
		});
		inoltracaricoButton.setIcon(new ImageIcon(MenuAdmin.class.getResource("/Icon/caricologo-removebg-RR.png")));
		inoltracaricoButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		inoltracaricoButton.setBounds(312, 241, 273, 39);
		contentPane.add(inoltracaricoButton);
	}
}
