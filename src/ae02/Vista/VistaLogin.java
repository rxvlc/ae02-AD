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

/**
 * Classe VistaLogin que crea una finestra d'inici de sessió amb una interfície gràfica.
 * Aquesta classe hereta de JFrame i conté components com etiquetes (JLabel), camps de text 
 * (JTextField, JPasswordField) i botons (JButton) per permetre a l'usuari introduir el seu nom d'usuari 
 * i contrasenya per a iniciar sessió.
 */
public class VistaLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;  // Panell principal que conté tots els components de la finestra.
    private JTextField txtUsuari;  // Camp de text per introduir el nom d'usuari.
    private JPasswordField pFcontrasenya;  // Camp de contrasenya per introduir la clau d'accés.
    JButton btnLogin;  // Botó per iniciar sessió.

    /**
     * Constructor que crea la interfície de la finestra d'inici de sessió.
     * Inicialitza els components gràfics, com camps de text, etiquetes, i botons.
     */
    public VistaLogin() {
        // Configuració de la finestra
        setTitle("Login");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 484, 324);
        
        // Crear el panell i establir-ne el fons i el límit d'espai
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 128));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Afegir títol
        JLabel lblTitolLogin = new JLabel("LOGIN BASE DE DADES");
        lblTitolLogin.setForeground(new Color(255, 255, 255));
        lblTitolLogin.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblTitolLogin.setBounds(99, 11, 257, 92);
        contentPane.add(lblTitolLogin);

        // Crear camp de text per introduir el nom d'usuari
        txtUsuari = new JTextField();
        txtUsuari.setBounds(86, 116, 287, 20);
        contentPane.add(txtUsuari);
        txtUsuari.setColumns(10);

        // Afegir etiqueta per al nom d'usuari
        JLabel lblTitolUsuari = new JLabel("Usuari: ");
        lblTitolUsuari.setForeground(new Color(255, 255, 255));
        lblTitolUsuari.setBounds(86, 100, 49, 14);
        contentPane.add(lblTitolUsuari);

        // Afegir etiqueta per a la contrasenya
        JLabel lblContrasenya = new JLabel("Contrasenya: ");
        lblContrasenya.setForeground(new Color(255, 255, 255));
        lblContrasenya.setBounds(86, 170, 103, 14);
        contentPane.add(lblContrasenya);

        // Crear botó per iniciar sessió
        btnLogin = new JButton("Iniciar Sessió");
        btnLogin.setBounds(86, 234, 287, 23);
        contentPane.add(btnLogin);

        // Crear camp de contrasenya
        pFcontrasenya = new JPasswordField();
        pFcontrasenya.setBounds(86, 186, 287, 20);
        contentPane.add(pFcontrasenya);

        // Inicialitzar components
        initComponents();
    }

    /**
     * Getter per obtenir el camp de text del nom d'usuari.
     * 
     * @return El camp de text del nom d'usuari.
     */
    public JTextField getTxtUsuari() {
        return txtUsuari;
    }

    /**
     * Setter per establir el camp de text del nom d'usuari.
     * 
     * @param txtUsuari El camp de text del nom d'usuari.
     */
    public void setTxtUsuari(JTextField txtUsuari) {
        this.txtUsuari = txtUsuari;
    }

    /**
     * Getter per obtenir el camp de contrasenya.
     * 
     * @return El camp de contrasenya.
     */
    public JPasswordField getpFcontrasenya() {
        return pFcontrasenya;
    }

    /**
     * Setter per establir el camp de contrasenya.
     * 
     * @param pFcontrasenya El camp de contrasenya.
     */
    public void setpFcontrasenya(JPasswordField pFcontrasenya) {
        this.pFcontrasenya = pFcontrasenya;
    }

    /**
     * Getter per obtenir el botó d'iniciar sessió.
     * 
     * @return El botó d'iniciar sessió.
     */
    public JButton getBtnLogin() {
        return btnLogin;
    }

    /**
     * Setter per establir el botó d'iniciar sessió.
     * 
     * @param btnLogin El botó d'iniciar sessió.
     */
    public void setBtnLogin(JButton btnLogin) {
        this.btnLogin = btnLogin;
    }

    /**
     * Inicialitza els components de la finestra. Aquesta funció fa que la finestra sigui visible
     * i la posiciona al centre de la pantalla.
     */
    public void initComponents() {
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
