package ae02.Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuari;
	private JPasswordField pFcontrasenya;

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitolLogin = new JLabel("LOGIN BASE DE DADES");
		lblTitolLogin.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTitolLogin.setBounds(99, 11, 257, 92);
		contentPane.add(lblTitolLogin);
		
		txtUsuari = new JTextField();
		txtUsuari.setBounds(86, 116, 287, 20);
		contentPane.add(txtUsuari);
		txtUsuari.setColumns(10);
		
		JLabel lblTitolUsuari = new JLabel("Usuari: ");
		lblTitolUsuari.setBounds(86, 100, 49, 14);
		contentPane.add(lblTitolUsuari);
		
		JLabel lblContrasenya = new JLabel("Contrasenya: ");
		lblContrasenya.setBounds(86, 170, 103, 14);
		contentPane.add(lblContrasenya);
		
		JButton btnLogin = new JButton("Iniciar Sesió");
		btnLogin.setBounds(86, 234, 287, 23);
		contentPane.add(btnLogin);
		
		pFcontrasenya = new JPasswordField();
		pFcontrasenya.setBounds(86, 186, 287, 20);
		contentPane.add(pFcontrasenya);
		initComponents();
	}
	
	public void initComponents() {
		setVisible(true);
	}
}
