package ba.unsa.etf.si.tim12.ui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import ba.unsa.etf.si.tim12.bll.service.MaterijaliManager;
import ba.unsa.etf.si.tim12.bll.viewmodel.MaterijalVM;
import ba.unsa.etf.si.tim12.dal.HibernateUtil;
import ba.unsa.etf.si.tim12.ui.components.UneditableTableModel;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class PrikazMaterijalaGUI {

	private JDialog frame;
	private JTable table;
	private JTextField textField;
	private JButton btnNewButton;
	static final Logger logger = Logger.getLogger(PrikazMaterijalaGUI.class);

	/**
	 * Create the application.
	 */
	public PrikazMaterijalaGUI() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		MainForma.Prekini(frame);
		frame.setTitle("Prikaz materijala");
		frame.setModalityType(ModalityType.APPLICATION_MODAL);
		frame.setResizable(false);
		frame.setBounds(100, 100, 524, 364);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 67, 483, 221);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setModel(new UneditableTableModel(new Object[][] {},
				new String[] { "ID", "Ime materijala", "Jedini\u010Dna cijena",
						"Mjerna jedinica" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(29);
		table.getColumnModel().getColumn(1).setPreferredWidth(91);
		table.getColumnModel().getColumn(1).setMinWidth(2);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		JLabel lblPretraivanjePo = new JLabel(
				"Pretra\u017Eivanje po imenu materijala:");
		lblPretraivanjePo.setBounds(10, 21, 225, 19);
		frame.getContentPane().add(lblPretraivanjePo);

		textField = new JTextField();
		textField.setBounds(235, 20, 149, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("Pretra\u017Ei");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Session sess = null;
				// dodavanje pretrazenih materijala u tabelu
				try {
					sess = HibernateUtil.getSessionFactory().openSession();
					MaterijaliManager m = new MaterijaliManager(sess);
					ArrayList<MaterijalVM> nadjeniMaterijali = m
							.nadjiPoImenu(textField.getText());
					// prvo praznjenje
					table.setModel(new UneditableTableModel(
							new Object[][] {},
							new String[] { "ID", "Ime materijala",
									"Jedini\u010Dna cijena", "Mjerna jedinica" }));
					UneditableTableModel model = (UneditableTableModel) table
							.getModel();
					for (MaterijalVM materijal : nadjeniMaterijali) {
						model.addRow(new Object[] { materijal.getId(),
								materijal.getNaziv(), materijal.getCijena(),
								materijal.getMjernaJedinica() });
					}

				} catch (Exception e) {
					logger.debug(e.getMessage(), e);
				} finally {
					if (sess != null)
						sess.close();
				}
			}
		});
		// ENIL: mijenjam ikonu
		btnNewButton
				.setIcon(new ImageIcon("src/main/resources/SearchIcon.png"));
		btnNewButton.setBounds(394, 20, 99, 20);
		frame.getContentPane().add(btnNewButton);

		JButton btnModifikacijaMaterijala = new JButton("Obri\u0161i");
		btnModifikacijaMaterijala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Session sess = null;

				try {
					sess = HibernateUtil.getSessionFactory().openSession();
					MaterijaliManager m = new MaterijaliManager(sess);
					// obrisati
					// TODO osigurati se u slucaju da nije odabra niti jedan red
					if (table.getSelectedRow() < 0) {
						JOptionPane.showMessageDialog(frame,
								"Odaberite materijal u tabeli", "Obavještenje",
								JOptionPane.INFORMATION_MESSAGE);
					}

					Long id = Long.parseLong(table.getValueAt(
							table.getSelectedRow(), 0).toString());

					boolean izbrisano = m.izbrisiMaterijal(id);
					((UneditableTableModel) table.getModel()).removeRow(table
							.getSelectedRow());
					if (izbrisano) {
						JOptionPane.showMessageDialog(frame,
								"Materijal je obrisan", "Obavještenje",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frame,
								"Greška u brisanju", "Obavještenje",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception e) {
					logger.debug(e.getMessage(), e);
				} finally {
					if (sess != null)
						sess.close();
				}

			}
		});
		btnModifikacijaMaterijala.setBounds(240, 299, 121, 23);
		frame.getContentPane().add(btnModifikacijaMaterijala);

		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame,
						WindowEvent.WINDOW_CLOSING));
			}
		});
		btnOdustani.setBounds(372, 299, 121, 23);
		frame.getContentPane().add(btnOdustani);
	}
}
