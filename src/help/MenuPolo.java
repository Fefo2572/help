package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuPolo extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPolo frame = new MenuPolo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}+/

	/**
	 * Create the frame.
	 */
	public MenuPolo(Account account) {
		String nome=account.getNome();
		
		setBackground(new Color(47, 79, 79));
		setTitle("Home Polo");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 681, 517);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelHome = new JPanel();
		
		panelHome.setBackground(new Color(47, 79, 79));
		panelHome.setBounds(0, 0, 269, 478);
		contentPane.add(panelHome);
		panelHome.setLayout(null);
		
		JLabel iconaPolo = new JLabel("New label");
		iconaPolo.setIcon(new ImageIcon(MenuPolo.class.getResource("/Icon/logo-polo-senzasfondo-R.png")));
		iconaPolo.setHorizontalAlignment(SwingConstants.CENTER);
		iconaPolo.setBounds(10, 11, 248, 126);
		panelHome.add(iconaPolo);
		
		JButton btnNewButton = new JButton("Visualizza Nucleo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaNucleo vn=new VisualizzaNucleo(account);
				vn.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setIcon(new ImageIcon(MenuPolo.class.getResource("/Icon/visualizzalogo-senzasfondo.png")));
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(10, 281, 248, 39);
		panelHome.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("LogOut");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen lg=new LoginScreen();
				lg.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(MenuPolo.class.getResource("/Icon/logout-senzasfondo.png")));
		btnNewButton_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton_2.setBounds(10, 428, 248, 39);
		panelHome.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Registra Nucleo");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistraNucleoFamiliare nf=new RegistraNucleoFamiliare(account);
				nf.setVisible(true);
				dispose();
			}
		});
		btnNewButton_3.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton_3.setIcon(new ImageIcon(MenuPolo.class.getResource("/Icon/registration logo senzasfondo.png")));
		btnNewButton_3.setBounds(10, 231, 248, 39);
		panelHome.add(btnNewButton_3);
		
		JButton erroreButton = new JButton("Segnala");
		erroreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Segnala s=new Segnala(account);
				s.setVisible(true);
				dispose();
			}
		});
		erroreButton.setIcon(new ImageIcon(MenuPolo.class.getResource("/Icon/gestioneerroreB-logo-RR.png")));
		erroreButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		erroreButton.setBounds(10, 331, 248, 39);
		panelHome.add(erroreButton);
		
		JButton visualizzareport = new JButton("Visualizza Report");
		visualizzareport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaReport vr=new VisualizzaReport(account);
				vr.setVisible(true);
				dispose();
			}
		});
		visualizzareport.setIcon(new ImageIcon(MenuPolo.class.getResource("/Icon/visualizzalogo-senzasfondo.png")));
		visualizzareport.setBounds(10, 381, 248, 39);
		panelHome.add(visualizzareport);
		visualizzareport.setFont(new Font("Arial Black", Font.PLAIN, 15));
		
		JLabel fieldusername = new JLabel("Benvenuto");
		fieldusername.setForeground(new Color(255, 255, 255));
		fieldusername.setFont(new Font("Arial Black", Font.PLAIN, 15));
		fieldusername.setBounds(279, 21, 113, 33);
		contentPane.add(fieldusername);
		
		JLabel username = new JLabel("New label");
		username.setForeground(new Color(255, 255, 255));
		username.setFont(new Font("Arial Black", Font.PLAIN, 15));
		username.setText(nome);
		username.setBounds(380, 21, 113, 33);
		contentPane.add(username);
		
		JButton statoButton = new JButton("");
		//update del valore dello stato del Polo
		try {
			String statoPolo=Polo.getStato(account.getIdPolo());
			if(statoPolo.equals("act")) {
				statoButton.setText("Attivo");
				statoButton.setBackground(Color.GREEN);
			}else {
				statoButton.setText("Disattivo");
				statoButton.setBackground(Color.RED);
			}
		}catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		statoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String stato=Polo.getStato(account.getIdPolo());
					if(stato.equals("act")) {
						statoButton.setText("Disattivo");
						Polo.setStato(account.getIdPolo(),"dis");
						statoButton.setBackground(Color.RED);
					}else {
						statoButton.setText("Attivo");
						Polo.setStato(account.getIdPolo(),"act");
						statoButton.setBackground(Color.GREEN);
					}
				}catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		statoButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		statoButton.setBounds(392, 65, 166, 39);
		contentPane.add(statoButton);
		
		JLabel statoPoloLabel = new JLabel("Stato Polo:");
		statoPoloLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		statoPoloLabel.setForeground(new Color(255, 255, 255));
		statoPoloLabel.setBounds(279, 65, 103, 39);
		contentPane.add(statoPoloLabel);
		
		JButton btnNewButton_1 = new JButton("Distribuzione Cibo");
		btnNewButton_1.setBounds(279, 333, 248, 39);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DistribuisciCibo dc=new DistribuisciCibo(account);
				dc.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(MenuPolo.class.getResource("/Icon/logodistribuzione-senzasfondoprevi.png")));
		btnNewButton_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		
		JButton confermacaricoButton = new JButton("Conferma Carico");
		confermacaricoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfermaCarichiPolo ccp=new ConfermaCarichiPolo(account);
				ccp.setVisible(true);
				dispose();
			}
		});
		confermacaricoButton.setIcon(new ImageIcon(MenuPolo.class.getResource("/Icon/modificalogo-senzasfondo.png")));
		confermacaricoButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		confermacaricoButton.setBounds(279, 233, 248, 39);
		contentPane.add(confermacaricoButton);
		
		JButton visualizzaCarichiButton = new JButton("Visualizza Carichi");
		visualizzaCarichiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaCarichiPolo vcp=new VisualizzaCarichiPolo(account);
				vcp.setVisible(true);
				dispose();
			}
		});
		visualizzaCarichiButton.setIcon(new ImageIcon(MenuPolo.class.getResource("/Icon/caricologo-removebg-RR.png")));
		visualizzaCarichiButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		visualizzaCarichiButton.setBounds(279, 283, 248, 39);
		contentPane.add(visualizzaCarichiButton);
		
	}
}
