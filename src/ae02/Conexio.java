package ae02;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * Classe Conexio.
 * 
 * Gestiona la connexió a la base de dades, permet establir-la, recuperar-la i
 * tancar-la de manera segura. També proporciona missatges informatius a l'usuari
 * en cas d'èxit o error.
 */
public class Conexio {

    /**
     * Connexió actual a la base de dades.
     */
    public static Connection conexio;

    /**
     * Retorna la connexió actual a la base de dades.
     * 
     * @return l'objecte Connection que representa la connexió actual.
     */
    public static Connection getConexio() {
        return conexio;
    }

    /**
     * Establix una nova connexió a la base de dades.
     * 
     * @param conexioAFicar la connexió a assignar com a connexió actual.
     */
    public static void setConexio(Connection conexioAFicar) {
        conexio = conexioAFicar;
    }

    /**
     * Tanca la connexió a la base de dades de manera segura.
     * Mostra un missatge a l'usuari informant de l'estat del procés.
     */
    public static void tancaConexio() {
        try {
            // Intentar tancar la connexió
            conexio.close();

            // Mostrar un missatge d'èxit quan la connexió es tanca correctament
            JOptionPane.showMessageDialog(null, "Connexió tancada amb èxit", "Èxit",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // Mostrar un missatge d'error si ocorre una excepció al tancar la connexió
            JOptionPane.showMessageDialog(null, "Error en tancar la connexió: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);

            // Imprimir el rastre de l'error per a depuració
            e.printStackTrace();
        }
    }

}
