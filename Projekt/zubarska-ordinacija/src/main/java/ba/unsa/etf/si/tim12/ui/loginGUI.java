package ba.unsa.etf.si.tim12.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;
import javax.swing.JLabel;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import ba.unsa.etf.si.tim12.bll.service.KorisnikManager;
import ba.unsa.etf.si.tim12.bll.viewmodel.LoginVM;
import ba.unsa.etf.si.tim12.dal.HibernateUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class loginGUI {

	private JDialog frmPrijava;
	private JLabel lblLozinka;
	private JPasswordField passwordField;
	private JTextField textField;
	private boolean uspjelaPrijava = false;
	//Enil:
	//Mijenjam ovo u static, mora biti da bih mogao provjeriti username iz drugih formu
	//npr. iz PasswordMgr forme (da provjerim jel stari password tačan)
	private static String username;
	private static final Logger logger = Logger.getLogger(loginGUI.class);

	/**
	 * Create the application.
	 */
	public loginGUI() {
		initialize();
		frmPrijava.setVisible(true);
		frmPrijava.requestFocus();
	}
	public static String DajUsername()
	{
		return username;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPrijava = new JDialog();

		frmPrijava.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (Prijava()) {
						uspjelaPrijava = true;
						frmPrijava.dispatchEvent(new WindowEvent(frmPrijava,
								WindowEvent.WINDOW_CLOSING));
						frmPrijava.setVisible(false);
						frmPrijava.dispose();
					} else {
						uspjelaPrijava = false;
						JOptionPane.showMessageDialog(frmPrijava,
								"Prijava nije uspjela", "Obavještenje",
								JOptionPane.INFORMATION_MESSAGE);
					}

				}
			}
		});
		frmPrijava.setResizable(false);
		frmPrijava.setTitle("Prijava");
		frmPrijava.setBounds(100, 100, 307, 149);
		//frmPrijava.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		frmPrijava.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0){}
			public void windowClosing(WindowEvent arg0) { 
				if(!uspjelaPrijava)
					System.exit(0);
			}
			public void windowClosed(WindowEvent arg0) {}
			public void windowActivated(WindowEvent arg0) {}
		});
		frmPrijava.getContentPane().setLayout(null);
		frmPrijava.setLocationRelativeTo(null);

		JButton prijavaBtn = new JButton("Prijavi se");
		prijavaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Prijava()) {
					uspjelaPrijava = true;
					frmPrijava.dispatchEvent(new WindowEvent(frmPrijava,
							WindowEvent.WINDOW_CLOSING));
					frmPrijava.setVisible(false);
					frmPrijava.dispose();
				} else {
					uspjelaPrijava = false;
					JOptionPane.showMessageDialog(frmPrijava,
							"Prijava nije uspjela", "Obavještenje",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		prijavaBtn.setBounds(164, 86, 113, 23);
		prijavaBtn.setActionCommand("OK");
		frmPrijava.getContentPane().add(prijavaBtn);

		lblLozinka = new JLabel("Password:");
		lblLozinka.setBounds(10, 60, 79, 14);
		frmPrijava.getContentPane().add(lblLozinka);

		passwordField = new JPasswordField("");
		passwordField.setToolTipText("");
		passwordField.setForeground(Color.GRAY);
		passwordField.setColumns(10);
		passwordField.setBounds(111, 55, 166, 20);
		frmPrijava.getContentPane().add(passwordField);

		JLabel label = new JLabel("Korisni\u010Dko ime:");
		label.setBounds(10, 24, 95, 14);
		frmPrijava.getContentPane().add(label);

		textField = new JTextField();
		textField.setForeground(Color.GRAY);
		textField.setColumns(10);
		textField.setBounds(111, 21, 166, 20);
		frmPrijava.getContentPane().add(textField);
		frmPrijava.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		frmPrijava.getRootPane().setDefaultButton(prijavaBtn);
		
		JButton btnZatvori = new JButton("Zatvori");
		btnZatvori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnZatvori.setActionCommand("OK");
		btnZatvori.setBounds(10, 86, 79, 23);
		//Ispraljven UIS issue, ako zatreba moze se lako button vratiti
		//frmPrijava.getContentPane().add(btnZatvori);
	}

	// Implemetirati funkciju za prijavu
	public boolean Prijava() {
		Session sess = null;

		try {

			sess = HibernateUtil.getSessionFactory().openSession();
			KorisnikManager m = new KorisnikManager(sess);

			LoginVM lgvm = new LoginVM();

			lgvm.setUsername(textField.getText());
			lgvm.setPassword(new String(passwordField.getPassword()));

			username = lgvm.getUsername();

			return m.provjeriPassword(lgvm);
		} catch(ExceptionInInitializerError e){
			JOptionPane.showMessageDialog(null, "Greška pri povezivanju s bazom", 
					"Greška!", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"Greška!", JOptionPane.ERROR_MESSAGE);
			logger.debug(e.getMessage(), e);
			return false;
		} finally {
			if (sess != null)
				sess.close();
		}
	}
}