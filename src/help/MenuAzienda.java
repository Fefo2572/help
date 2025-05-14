package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class MenuAzienda extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuAzienda frame = new MenuAzienda();
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
	public MenuAzienda(Account account) {
		String username=account.getNome();
		setTitle("Home Azienda");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 746, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(47, 79, 79));
		panel.setBounds(0, 0, 293, 511);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel pizzalogolabel = new JLabel("");
		pizzalogolabel.setIcon(new ImageIcon(MenuAzienda.class.getResource("/Icon/Azienda-menu-RR.png")));
		pizzalogolabel.setHorizontalAlignment(SwingConstants.CENTER);
		pizzalogolabel.setBounds(10, 11, 273, 211);
		panel.add(pizzalogolabel);
		
		JButton logoutButton = new JButton("LogOut");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen lg=new LoginScreen();
				lg.setVisible(true);
				dispose();
			}
		});
		logoutButton.setIcon(new ImageIcon(MenuAzienda.class.getResource("/Icon/logout-senzasfondo.png")));
		logoutButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		logoutButton.setBounds(10, 460, 273, 39);
		panel.add(logoutButton);
		
		JButton distribuzioneButton = new JButton("Distribuzione");
		distribuzioneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String query = "SELECT tv.id, tv.nome_cibo " +
			               "FROM tipologia_viveri tv " +
			               "JOIN catalogo_azienda c ON tv.id = c.id_tipologia " +
			               "WHERE c.id_azienda = " + account.getIdAzienda();
				
				DatabaseManager dbm=new DatabaseManager();
				
				//Controllo che ci siano elementi nel catalogo
				try {
					dbm.connect();
					int rowCount = dbm.getQueryRowCount(query);
					if(rowCount>0) {
						System.out.println("MenuAzienda: Trovati elementi nel catalogo azienda");
						Donazione d=new Donazione(account);
						dbm.disconnect();
						d.setVisible(true);
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Prima di effettuare una donazione\ninserisci almeno un elemento nel catalogo", "Attenzione", JOptionPane.WARNING_MESSAGE);
						dbm.disconnect();
					}
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				

			}
		});
		distribuzioneButton.setIcon(new ImageIcon(MenuAzienda.class.getResource("/Icon/lotto-icon-removebg-previewRR.png")));
		distribuzioneButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		distribuzioneButton.setBounds(10, 278, 273, 39);
		panel.add(distribuzioneButton);
		
		JButton visualizzacatalogoButton = new JButton("Visualizza Catalogo");
		visualizzacatalogoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaCatalogo vc=new VisualizzaCatalogo(account);
				vc.setVisible(true);
				dispose();
			}
		});
		visualizzacatalogoButton.setIcon(new ImageIcon(MenuAzienda.class.getResource("/Icon/catalogo-logo-removebg-previewRR.png")));
		visualizzacatalogoButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		visualizzacatalogoButton.setBounds(10, 338, 273, 39);
		panel.add(visualizzacatalogoButton);
		
		JButton visualizzarichiesteButton = new JButton("Visualizza Richieste");
		visualizzarichiesteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizzaRichieste vr=new VisualizzaRichieste(account);
				vr.setVisible(true);
				dispose();
			}
		});
		visualizzarichiesteButton.setBounds(10, 398, 273, 39);
		panel.add(visualizzarichiesteButton);
		visualizzarichiesteButton.setIcon(new ImageIcon(MenuAzienda.class.getResource("/Icon/visualizzalogo-senzasfondo.png")));
		visualizzarichiesteButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		
		JLabel benvenutoLabel = new JLabel("Benvenuto");
		benvenutoLabel.setForeground(new Color(255, 255, 255));
		benvenutoLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		benvenutoLabel.setBounds(303, 27, 101, 33);
		contentPane.add(benvenutoLabel);
		
		JLabel usernameLabel = new JLabel("New label");
		usernameLabel.setForeground(new Color(255, 255, 255));
		usernameLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		usernameLabel.setBounds(414, 32, 106, 22);
		usernameLabel.setText(username);
		contentPane.add(usernameLabel);
		
	}
}
