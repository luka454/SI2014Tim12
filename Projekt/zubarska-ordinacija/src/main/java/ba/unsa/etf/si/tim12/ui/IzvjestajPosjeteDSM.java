package ba.unsa.etf.si.tim12.ui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;


import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerDateModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import ba.unsa.etf.si.tim12.App;
import ba.unsa.etf.si.tim12.bll.service.IzvjestajManager;
import ba.unsa.etf.si.tim12.bll.viewmodel.OdradjenePosjeteRowVM;
import ba.unsa.etf.si.tim12.bll.viewmodel.OdradjenePosjeteVM;
import ba.unsa.etf.si.tim12.bll.viewmodel.PacijentVM;
import ba.unsa.etf.si.tim12.bll.viewmodel.PosjetePacijentaRowVM;
import ba.unsa.etf.si.tim12.bll.viewmodel.PosjetePacijentaVM;
import ba.unsa.etf.si.tim12.dal.HibernateUtil;
import ba.unsa.etf.si.tim12.ui.components.UneditableTableModel;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class IzvjestajPosjeteDSM {
	private JDialog frame;
	private JTable table;
	private JLabel lblOd;
	private JLabel lblOddo;
	private JLabel lblVrstaPregleda;
	private JComboBox comboBox_6;
	private JSpinner spinnerOd;
	private JSpinner spinnerDo;	
	final static Logger logger = Logger.getLogger(IzvjestajPosjeteDSM.class);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IzvjestajPosjeteDSM window = new IzvjestajPosjeteDSM();
					window.frame.setVisible(true);
				} catch (Exception e) {
					logger.debug("Došlo je do greške.", e);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IzvjestajPosjeteDSM() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frame = new JDialog();
		MainForma.Prekini(frame);
		frame.setTitle("Izvje\u0161taj o posjetama");
		frame.setModalityType(ModalityType.APPLICATION_MODAL);
		frame.setResizable(false);
		
		frame.setBounds(100, 100, 550, 364);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 118, 503, 157);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(new UneditableTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Ime", "Prezime", "Doktor", "Vrijeme posjete"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(0).setMinWidth(2);
		scrollPane.setViewportView(table);
		
		JLabel lblPretraivanjePo = new JLabel("Odaberite vremenski interval:");
		lblPretraivanjePo.setBounds(22, 57, 204, 19);
		frame.getContentPane().add(lblPretraivanjePo);
		
		JButton btnModifikacijaMaterijala = new JButton("Prikaži");
		btnModifikacijaMaterijala.setBounds(262, 299, 121, 23);
		btnModifikacijaMaterijala.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Session sesija = null;
				try {
					sesija = HibernateUtil.getSessionFactory().openSession();
					IzvjestajManager izvjestaji = new IzvjestajManager(sesija);
					OdradjenePosjeteVM posjete = izvjestaji.odradjenePosjetePoDanu((Date) spinnerOd.getValue());
					ArrayList<OdradjenePosjeteRowVM> redovi = posjete.getOdradjenePosjete();
					((DefaultTableModel) table.getModel()).addRow(new Object[]{"Column 1", "Column 2", "Column 3", "Column 4"});
					for(int i = 0; i < redovi.size(); i++) {						
						table.getModel().setValueAt(redovi.get(i).getId(), i, 0);
						table.getModel().setValueAt(redovi.get(i).getIme(), i, 1);
						table.getModel().setValueAt(redovi.get(i).getDoktor(), i, 2);
						table.getModel().setValueAt(redovi.get(i).getVrijeme(), i, 3);
					}					
				}
				catch (Exception e1) {
					logger.debug(e1.getMessage(), e1);
				} 
				finally {
					if (sesija != null)
						sesija.close();
				}			
			}
		});
		frame.getContentPane().add(btnModifikacijaMaterijala);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.setBounds(404, 299, 121, 23);
		btnOdustani.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				frame.setVisible (false);
				frame.dispose();
				
			}
		});
		frame.getContentPane().add(btnOdustani);
		
		lblOd = new JLabel("Od:");
		lblOd.setBounds(22, 83, 27, 28);
		frame.getContentPane().add(lblOd);
		
		lblOddo = new JLabel("Do:");
		lblOddo.setBounds(315, 83, 27, 28);
		frame.getContentPane().add(lblOddo);
		
		lblVrstaPregleda = new JLabel("Vrsta pregleda:");
		lblVrstaPregleda.setBounds(22, 24, 110, 14);
		frame.getContentPane().add(lblVrstaPregleda);
		
		comboBox_6 = new JComboBox<Object>();
		comboBox_6.setModel(new DefaultComboBoxModel<Object>(new String[] {"Dnevni", "Sedmi\u010Dni", "Mjese\u010Dni"}));
		comboBox_6.setBounds(132, 21, 94, 20);
		frame.getContentPane().add(comboBox_6);
		
		Calendar calendar = Calendar.getInstance();
		Date initDate = calendar.getTime();
		spinnerOd = new JSpinner();
		spinnerOd.setModel(new SpinnerDateModel(new Date(-2208992399907L), null, null, Calendar.DAY_OF_YEAR));
		spinnerOd.setEditor(new JSpinner.DateEditor(spinnerOd, "dd/MM/yyyy"));
		spinnerOd.setBounds(61, 86, 171, 22);
		frame.getContentPane().add(spinnerOd);
		
		spinnerDo = new JSpinner();
		spinnerDo.setBounds(354, 86, 171, 22);
		spinnerDo.setModel(new SpinnerDateModel(initDate, null, null, Calendar.DAY_OF_YEAR));
		spinnerDo.setEditor(new JSpinner.DateEditor(spinnerDo, "dd/MM/yyyy"));
		frame.getContentPane().add(spinnerDo);
	}
}
