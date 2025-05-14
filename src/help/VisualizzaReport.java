package help;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class VisualizzaReport extends JFrame {

	private JPanel contentPane;
	Account account;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizzaReport frame = new VisualizzaReport();
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
	public VisualizzaReport(Account account) {
		GeneraReport gr=new GeneraReport(account);
		this.account=account;
		setTitle("Visualizza Report");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 386);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gr.indietro();
				dispose();
			}
		});
		indietroButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		indietroButton.setBounds(10, 308, 120, 28);
		contentPane.add(indietroButton);
		
		JLabel reportLabel = new JLabel("Sezione Report ");
		reportLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		reportLabel.setBounds(10, 11, 204, 35);
		contentPane.add(reportLabel);
		
		JLabel generareportLabel = new JLabel("Genera Report");
		generareportLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		generareportLabel.setBounds(10, 67, 143, 22);
		contentPane.add(generareportLabel);
		
		JLabel mensileLabel = new JLabel("Mensile");
		mensileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mensileLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		mensileLabel.setBounds(10, 112, 120, 28);
		contentPane.add(mensileLabel);
		
		JButton mensileButton = new JButton("Genera");
		mensileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gr.report(30);
				JOptionPane.showMessageDialog(null, "Report creato sul Desktop", "Info",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mensileButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		mensileButton.setBounds(163, 112, 120, 28);
		contentPane.add(mensileButton);
		
		JLabel trimestraleLabel = new JLabel("Trimestrale");
		trimestraleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trimestraleLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		trimestraleLabel.setBounds(0, 177, 134, 28);
		contentPane.add(trimestraleLabel);
		
		JButton trimestraleButton = new JButton("Genera");
		trimestraleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gr.report(90);
				JOptionPane.showMessageDialog(null, "Report creato sul Desktop", "Info",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		trimestraleButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		trimestraleButton.setBounds(163, 177, 120, 28);
		contentPane.add(trimestraleButton);
		
		JLabel annualeLabel = new JLabel("Annuale");
		annualeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		annualeLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		annualeLabel.setBounds(10, 243, 120, 22);
		contentPane.add(annualeLabel);
		
		JButton annualeButton = new JButton("Genera");
		annualeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gr.report(365);
				JOptionPane.showMessageDialog(null, "Report creato sul Desktop", "Info",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		annualeButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		annualeButton.setBounds(163, 240, 120, 28);
		contentPane.add(annualeButton);
	}
}
