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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Classe VistaRegistre que representa la interfície gràfica per al registre
 * d'usuaris. 
 * Aquesta vista permet que l'usuari introdueixi un nom d'usuari i una 
 * contrasenya, amb la validació que la contrasenya es repeteixi correctament.
 * 
 * Inclou camps de text, camps de contrasenya i un botó per realitzar l'acció 
 * de registre.
 */
public class VistaRegistre extends JFrame {

	/**
	 * Identificador de versió per a la serialització de la classe.
	 * Utilitzat per a garantir la compatibilitat entre diferents versions de la classe serialitzada.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Panell principal de la finestra.
	 * Conté tots els components de la interfície gràfica d'usuari.
	 */
	private JPanel contentPane;

	/**
	 * Camp de text per a introduir el nom d'usuari.
	 * Permet que l'usuari especifique el seu identificador.
	 */
	private JTextField txtNomUsuari;

	/**
	 * Camp de contrasenya per a la contrasenya principal.
	 * Utilitzat per a introduir de forma segura la clau d'accés.
	 */
	private JPasswordField pFcontrasenya;

	/**
	 * Botó per a realitzar el registre de l'usuari.
	 * Al prémer-lo, s'inicia el procés de validació i registre.
	 */
	private JButton btnRegistreUsuari;

	/**
	 * Camp de contrasenya per a repetir la contrasenya principal.
	 * Serveix per a verificar que ambdues entrades de contrasenya coincideixen.
	 */
	private JPasswordField pFRepeteixContrasenya;


    /**
     * Constructor de la finestra VistaRegistre.
     * Configura les propietats inicials de la finestra, els components gràfics
     * i les seves posicions dins del panell.
     */
    public VistaRegistre() {
        setResizable(false); // No permetre redimensionar la finestra
        setTitle("Registre Usuari"); // Establir el títol de la finestra
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Tancar la finestra sense finalitzar el programa
        setBounds(100, 100, 302, 331); // Establir les dimensions i la posició inicial de la finestra

        // Configuració del panell principal
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 128)); // Color de fons blau
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Màrgen interior
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Títol principal
        JLabel lblTitolRegistreUsuari = new JLabel("Registre Usuari");
        lblTitolRegistreUsuari.setForeground(new Color(255, 255, 255)); // Color de text blanc
        lblTitolRegistreUsuari.setFont(new Font("Tahoma", Font.PLAIN, 19)); // Font del títol
        lblTitolRegistreUsuari.setBounds(76, 21, 126, 45);
        contentPane.add(lblTitolRegistreUsuari);

        // Etiqueta i camp de text per al nom d'usuari
        JLabel lblNomUsuari = new JLabel("Nom d'usuari:");
        lblNomUsuari.setForeground(new Color(255, 255, 255));
        lblNomUsuari.setBounds(10, 96, 82, 14);
        contentPane.add(lblNomUsuari);

        txtNomUsuari = new JTextField();
        txtNomUsuari.setBounds(10, 110, 271, 20);
        contentPane.add(txtNomUsuari);
        txtNomUsuari.setColumns(10);

        // Etiqueta i camp de contrasenya
        JLabel lblContrasenya = new JLabel("Contrasenya:");
        lblContrasenya.setForeground(new Color(255, 255, 255));
        lblContrasenya.setBounds(10, 157, 82, 14);
        contentPane.add(lblContrasenya);

        pFcontrasenya = new JPasswordField();
        pFcontrasenya.setBounds(10, 171, 271, 20);
        contentPane.add(pFcontrasenya);

        // Etiqueta i camp per repetir la contrasenya
        JLabel lblRepeteixContrasenya = new JLabel("Repeteix Contrasenya:");
        lblRepeteixContrasenya.setForeground(Color.WHITE);
        lblRepeteixContrasenya.setBounds(10, 210, 159, 14);
        contentPane.add(lblRepeteixContrasenya);

        pFRepeteixContrasenya = new JPasswordField();
        pFRepeteixContrasenya.setBounds(10, 224, 271, 20);
        contentPane.add(pFRepeteixContrasenya);

        // Botó de registre d'usuari
        btnRegistreUsuari = new JButton("Registrar Usuari");
        btnRegistreUsuari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acció del botó (pendents d'implementació)
            }
        });
        btnRegistreUsuari.setBounds(10, 258, 271, 23);
        contentPane.add(btnRegistreUsuari);
    }

    /**
     * Obté el camp de text per al nom d'usuari.
     * @return Camp de text JTextField per al nom d'usuari.
     */
    public JTextField getTxtNomUsuari() {
        return txtNomUsuari;
    }

    /**
     * Estableix el camp de text per al nom d'usuari.
     * @param txtNomUsuari Camp de text JTextField a establir.
     */
    public void setTxtNomUsuari(JTextField txtNomUsuari) {
        this.txtNomUsuari = txtNomUsuari;
    }

    /**
     * Obté el camp de contrasenya principal.
     * @return Camp de contrasenya JPasswordField.
     */
    public JPasswordField getpFcontrasenya() {
        return pFcontrasenya;
    }

    /**
     * Estableix el camp de contrasenya principal.
     * @param pFcontrasenya Camp de contrasenya JPasswordField a establir.
     */
    public void setpFcontrasenya(JPasswordField pFcontrasenya) {
        this.pFcontrasenya = pFcontrasenya;
    }

    /**
     * Obté el botó de registre d'usuari.
     * @return Botó JButton per al registre.
     */
    public JButton getBtnRegistreUsuari() {
        return btnRegistreUsuari;
    }

    /**
     * Estableix el botó de registre d'usuari.
     * @param btnRegistreUsuari Botó JButton a establir.
     */
    public void setBtnRegistreUsuari(JButton btnRegistreUsuari) {
        this.btnRegistreUsuari = btnRegistreUsuari;
    }

    /**
     * Obté el camp de contrasenya per repetir la contrasenya.
     * @return Camp de contrasenya JPasswordField.
     */
    public JPasswordField getpFRepeteixContrasenya() {
        return pFRepeteixContrasenya;
    }
}
