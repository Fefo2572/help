package help;


import org.jdatepicker.UtilDateModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.jdatepicker.JDatePicker;
import java.time.LocalDate;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class RegistraUtente extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel labelNome;
	private JTextField textFieldCognome;
	private JLabel labelCognome;
	private JTextField textFieldNome;
	private JLabel lblNewLabel_1;
	private JTextField textFieldCF;
	private JLabel labelSesso;
	private JLabel labelData;
	private JLabel labelResidenza;
	private JDatePicker FieldData;
	private JTextField textFieldResidenza;
	private JLabel labelRichieste;
	private ButtonGroup bg = new ButtonGroup();
	//private ArrayList<Utente> persone = new ArrayList<>();
	private JButton aggiungiButton;
	private JButton eliminaButton;
	
	DefaultTableModel model;
	
	private Account account;
	private String id_nucleo;
	

	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistraUtente frame = new RegistraUtente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public RegistraUtente(Account acc,String id_nucl) {
		account = acc;
		id_nucleo = id_nucl;
		
		setResizable(false);
		setTitle("Registra Famiglia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 972, 547);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Informazioni Familiari");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
		lblNewLabel.setBounds(10, 11, 279, 40);
		contentPane.add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(332, 22, 614, 480);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "CF", "Nome", "Cognome", "Sesso",
				"Data_Nascita", "Residenza", "Richieste" }));
		
		
		// Aggiorno la tabella
		model = (DefaultTableModel) table.getModel();
		updateTable();
		
		
		JRadioButton jradiouomo = new JRadioButton("Uomo");
		jradiouomo.setBackground(new Color(0, 128, 128));
		jradiouomo.setHorizontalAlignment(SwingConstants.CENTER);
		jradiouomo.setFont(new Font("Arial Black", Font.BOLD, 14));
		jradiouomo.setBounds(89, 202, 107, 23);
		contentPane.add(jradiouomo);

		JRadioButton jradiodonna = new JRadioButton("Donna");
		jradiodonna.setHorizontalAlignment(SwingConstants.CENTER);
		jradiodonna.setFont(new Font("Arial Black", Font.BOLD, 14));
		jradiodonna.setBackground(new Color(0, 128, 128));
		jradiodonna.setBounds(198, 203, 93, 23);
		contentPane.add(jradiodonna);
		
		//valori presi con il click sulla Jtable
		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            // Ottieni i valori dalla riga selezionata
		           
		        	String cf=(String) table.getValueAt(selectedRow, 0);
		            String nome = (String) table.getValueAt(selectedRow, 1);
		            String cognome=(String) table.getValueAt(selectedRow, 2);
		            String sesso=(String) table.getValueAt(selectedRow, 3);
		            LocalDate localDate = (LocalDate) table.getValueAt(selectedRow, 4);
		            Date data1 = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		            String residenza=(String) table.getValueAt(selectedRow, 5);
		            String richiesta=(String) table.getValueAt(selectedRow, 6);
		            
		            

		            // Inserisci i valori nei campi di testo
		            textFieldCF.setText(cf);
		            textFieldNome.setText(nome);
		            textFieldCognome.setText(cognome);
		            textFieldResidenza.setText(residenza);
		            if (sesso.equals("M")) {
		                bg.setSelected(jradiouomo.getModel(), true);
		            } else {
		                bg.setSelected(jradiodonna.getModel(), true);
		            }
		            Calendar cal = Calendar.getInstance();
		            cal.setTime(data1);
		            FieldData.getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		            FieldData.getModel().setSelected(true);
		        }
		    }
		});

		labelNome = new JLabel("Nome");
		labelNome.setHorizontalAlignment(SwingConstants.RIGHT);
		labelNome.setFont(new Font("Arial Black", Font.PLAIN, 13));
		labelNome.setBounds(9, 107, 69, 18);
		contentPane.add(labelNome);

		textFieldCognome = new JTextField();
		textFieldCognome.setBounds(89, 155, 200, 22);
		contentPane.add(textFieldCognome);
		textFieldCognome.setColumns(10);

		labelCognome = new JLabel("Cognome");
		labelCognome.setFont(new Font("Arial Black", Font.PLAIN, 13));
		labelCognome.setBounds(10, 153, 68, 20);
		contentPane.add(labelCognome);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(88, 108, 200, 22);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);

		lblNewLabel_1 = new JLabel("CF");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(32, 62, 46, 14);
		contentPane.add(lblNewLabel_1);

		textFieldCF = new JTextField();
		textFieldCF.setBounds(89, 62, 200, 22);
		contentPane.add(textFieldCF);
		textFieldCF.setColumns(10);

		labelSesso = new JLabel("Sesso");
		labelSesso.setHorizontalAlignment(SwingConstants.RIGHT);
		labelSesso.setFont(new Font("Arial Black", Font.PLAIN, 13));
		labelSesso.setBounds(27, 200, 52, 25);
		contentPane.add(labelSesso);

		labelData = new JLabel("Data_Nascita");
		labelData.setFont(new Font("Arial Black", Font.PLAIN, 13));
		labelData.setBounds(10, 247, 104, 18);
		contentPane.add(labelData);

		labelResidenza = new JLabel("Residenza");
		labelResidenza.setFont(new Font("Arial Black", Font.PLAIN, 13));
		labelResidenza.setBounds(10, 283, 84, 20);
		contentPane.add(labelResidenza);

		FieldData = new JDatePicker();
		FieldData.setBounds(124, 248, 165, 22);
		contentPane.add(FieldData);
		//textFieldData.setColumns(10);

		textFieldResidenza = new JTextField();
		textFieldResidenza.setBounds(89, 285, 200, 22);
		contentPane.add(textFieldResidenza);
		textFieldResidenza.setColumns(10);

		labelRichieste = new JLabel("Richieste");
		labelRichieste.setFont(new Font("Arial Black", Font.PLAIN, 13));
		labelRichieste.setBounds(10, 324, 68, 18);
		contentPane.add(labelRichieste);


		bg.add(jradiouomo);
		bg.add(jradiodonna);
		
		JRadioButton jaradioDiabete = new JRadioButton("Diabete");
		jaradioDiabete.setBackground(new Color(0, 128, 128));
		jaradioDiabete.setFont(new Font("Arial Black", Font.BOLD, 14));
		jaradioDiabete.setHorizontalAlignment(SwingConstants.CENTER);
		jaradioDiabete.setBounds(89, 324, 109, 23);
		contentPane.add(jaradioDiabete);
		
		JRadioButton JradioCeliaco = new JRadioButton("Celiaco");
		JradioCeliaco.setBackground(new Color(0, 128, 128));
		JradioCeliaco.setFont(new Font("Arial Black", Font.BOLD, 14));
		JradioCeliaco.setHorizontalAlignment(SwingConstants.CENTER);
		JradioCeliaco.setBounds(89, 362, 109, 23);
		contentPane.add(JradioCeliaco);
		
		

		aggiungiButton = new JButton("Aggiungi");
		aggiungiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Trasferisco i dati in delle variabili
				String codiceFiscale = textFieldCF.getText();
				String nome = textFieldNome.getText();
				String cognome = textFieldCognome.getText();
				java.util.Date date = ((java.util.GregorianCalendar) FieldData.getModel().getValue()).getTime();
				LocalDate dataNascita = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String residenza = textFieldResidenza.getText();
				String sesso = jradiouomo.isSelected() ? "M" : "F";
				String richiesta="";
				if(jaradioDiabete.isSelected()){
					
					richiesta="D ";
				}
				if(JradioCeliaco.isSelected()) {
					
					richiesta+="C ";
				}
				//Provo ad aggiungere nel DB i dati utente
				try {
					//Creo utente
					Utente utente = Polo.createUtente(codiceFiscale, nome, cognome, sesso, residenza, dataNascita, id_nucleo);
					//Aggiungo i suoi disturbi
					if(jaradioDiabete.isSelected()){
						Polo.addDisturbo(codiceFiscale, "2");
					}
					if(JradioCeliaco.isSelected()) {
						Polo.addDisturbo(codiceFiscale, "1");
					}
					
					//Utente utente = new Utente(codiceFiscale, nome, cognome, sesso, dataNascita, residenza);
					//persone.add(utente);
					model = (DefaultTableModel) table.getModel();
					model.addRow(new Object[] { codiceFiscale, nome, cognome, sesso, dataNascita, residenza, richiesta });
					
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (PoloException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Questa persona risulta registrata", "Avviso", JOptionPane.WARNING_MESSAGE);
				} catch (FutureDateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Data non valida", "Avviso", JOptionPane.WARNING_MESSAGE);
				}
				
				
				


				
				
				// Reset input fields
				
				textFieldCF.setText("");
				textFieldNome.setText("");
				textFieldCognome.setText("");
				FieldData.getModel().setValue(null);
				textFieldResidenza.setText("");
		
				bg.clearSelection();
				
				/*
				//casting LocalDate to String valut to insert into DB
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String data=dataNascita.format(formatter);
				System.out.println(data);
				DatabaseManager dbm=new DatabaseManager();
				String[] colonne= {"cf","nome","cognome","sesso","data_di_nascita","residenza","id_nucleo","richieste_speciali"};
				String[] values= {codiceFiscale,nome,cognome,sesso,data,residenza,"1","1"};
				try {
					dbm.connect();
					dbm.insertRecord("utente", colonne, values);
				}catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				*/
			}
		});
		aggiungiButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
		aggiungiButton.setBounds(10, 433, 120, 28);
		contentPane.add(aggiungiButton);

		eliminaButton = new JButton("Elimina");
		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					
					
						//Eliminazione

						try {
							int rowIndex = table.getSelectedRow();
							String cfvalue = (String) table.getValueAt(rowIndex, 0);
							System.out.println(cfvalue);
							Polo.deleteUtente(cfvalue);
							DefaultTableModel model = (DefaultTableModel) table.getModel();
							model.removeRow(selectedRow);
							//persone.remove(selectedRow);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							 JOptionPane.showMessageDialog(null, "Si è verificato un errore nel database.", "Errore", JOptionPane.ERROR_MESSAGE);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Si è verificato un errore nel database.", "Errore", JOptionPane.ERROR_MESSAGE);
						} catch (PoloException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Si è verificato un errore nel polo.", "Errore", JOptionPane.ERROR_MESSAGE);
						}

					
					
					

				}
			}
		});
		eliminaButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
		eliminaButton.setBounds(140, 433, 120, 28);
		contentPane.add(eliminaButton);
		
		JButton btnNewButton = new JButton("Indietro");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(acc.getTipo().equals("polo")) {
					MenuPolo mp=new MenuPolo(account);
					mp.setVisible(true);
					dispose();
				}else {
					VisualizzaNucleo vn=new VisualizzaNucleo(account);
					vn.setVisible(true);
					dispose();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(10, 472, 120, 28);
		contentPane.add(btnNewButton);
		
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if(selectedRow != -1) {
					String codiceFiscale = textFieldCF.getText();
					String nome = textFieldNome.getText();
					String cognome = textFieldCognome.getText();
					java.util.Date date = ((java.util.GregorianCalendar) FieldData.getModel().getValue()).getTime();
					LocalDate dataNascita = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					String residenza = textFieldResidenza.getText();
					String sesso = jradiouomo.isSelected() ? "M" : "F";
					String richiesta = "";
					if (jaradioDiabete.isSelected()) {

						richiesta = "D ";
					}
					if (JradioCeliaco.isSelected()) {

						richiesta += "C ";
					}
					// Provo ad aggiungere nel DB i dati utente
					try {
						//aggiorno utente
						Utente utente = Polo.updateUtente(codiceFiscale, nome, cognome, sesso, dataNascita, residenza,id_nucleo);
						JOptionPane.showMessageDialog(null, "Record aggiornato correttamente", "Info",
								JOptionPane.INFORMATION_MESSAGE);
						// Aggiungo i suoi disturbi
						if (jaradioDiabete.isSelected()) {
							Polo.addDisturbo(codiceFiscale, "2");
						}
						if (JradioCeliaco.isSelected()) {
							Polo.addDisturbo(codiceFiscale, "1");
						}

					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (PoloException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Questa persona risulta registrata", "Avviso",
								JOptionPane.WARNING_MESSAGE);
					} catch (FutureDateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Data non valida", "Avviso", JOptionPane.WARNING_MESSAGE);
					}

					table.setValueAt(codiceFiscale, selectedRow, 0);
					table.setValueAt(nome, selectedRow, 1);
					table.setValueAt(cognome, selectedRow, 2);
					table.setValueAt(sesso, selectedRow, 3);
					table.setValueAt(dataNascita, selectedRow, 4);
					table.setValueAt(residenza, selectedRow, 5);
					table.setValueAt(richiesta, selectedRow, 6);
					
					DefaultTableModel modell = (DefaultTableModel) table.getModel();
					textFieldCF.setText("");
					textFieldNome.setText("");
					textFieldCognome.setText("");
					FieldData.getModel().setValue(null);
					textFieldResidenza.setText("");
					bg.clearSelection();	
				}	
			}
		});
		modificaButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		modificaButton.setBounds(140, 472, 120, 28);
		contentPane.add(modificaButton);
		
		
	}
	
	/*
	 * Funzione che aggiorna la lista con le cose già presenti
	 */
	private void updateTable() {
		try {
			ArrayList<Utente> lutente = Polo.getUtenti(id_nucleo);
			
			for(Utente u : lutente) {
				String disturbo = Polo.getDisturbiString(u.getCodiceFiscale());
				
				model.addRow(new Object[] { u.getCodiceFiscale(), u.getNome(), u.getCognome(), u.getSesso(), u.getDataNascita(), u.getResidenza(), disturbo });
			}
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PoloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}



