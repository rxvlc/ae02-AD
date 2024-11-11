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
import java.awt.Color;

public class RegistreUsuari extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomUsuari;
	private JPasswordField pFcontrasenya;
	private JButton btnRegistreUsuari;

	/**
	 * Create the frame.
	 */
	public RegistreUsuari() {
		setResizable(false);
		setTitle("Registre Usuari");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 302, 297);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitolRegistreUsuari = new JLabel("Registre Usuari");
		lblTitolRegistreUsuari.setForeground(new Color(255, 255, 255));
		lblTitolRegistreUsuari.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblTitolRegistreUsuari.setBounds(76, 21, 126, 45);
		contentPane.add(lblTitolRegistreUsuari);

		JLabel lblNomUsuari = new JLabel("Nom d'usuari:");
		lblNomUsuari.setForeground(new Color(255, 255, 255));
		lblNomUsuari.setBounds(10, 96, 82, 14);
		contentPane.add(lblNomUsuari);

		txtNomUsuari = new JTextField();
		txtNomUsuari.setBounds(10, 110, 271, 20);
		contentPane.add(txtNomUsuari);
		txtNomUsuari.setColumns(10);

		JLabel lblContrasenya = new JLabel("Contrasenya:");
		lblContrasenya.setForeground(new Color(255, 255, 255));
		lblContrasenya.setBounds(10, 157, 82, 14);
		contentPane.add(lblContrasenya);

		btnRegistreUsuari = new JButton("Registrar Usuari");
		btnRegistreUsuari.setBounds(10, 223, 271, 23);
		contentPane.add(btnRegistreUsuari);

		pFcontrasenya = new JPasswordField();
		pFcontrasenya.setBounds(10, 171, 271, 20);
		contentPane.add(pFcontrasenya);

	}

	public JTextField getTxtNomUsuari() {
		return txtNomUsuari;
	}

	public void setTxtNomUsuari(JTextField txtNomUsuari) {
		this.txtNomUsuari = txtNomUsuari;
	}

	public JPasswordField getpFcontrasenya() {
		return pFcontrasenya;
	}

	public void setpFcontrasenya(JPasswordField pFcontrasenya) {
		this.pFcontrasenya = pFcontrasenya;
	}

	public JButton getBtnRegistreUsuari() {
		return btnRegistreUsuari;
	}

	public void setBtnRegistreUsuari(JButton btnRegistreUsuari) {
		this.btnRegistreUsuari = btnRegistreUsuari;
	}
}
