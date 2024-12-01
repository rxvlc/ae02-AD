package ae02;

import java.sql.ResultSet;

public class Dades {

    // Atributs de la classe Dades

    /**
     * Nom de l'usuari actual.
     * Es guarda com una cadena de text (String) que representa el nom de l'usuari.
     */
    public static String nomUsuari = null;

    /**
     * Contrasenya de l'usuari actual emmagatzemada com un hash.
     * Es guarda com una cadena de text (String) que representa el hash de la contrasenya.
     */
    public static String hashContrasenyaUsuari = null;

    /**
     * Variable que indica si l'usuari és administrador.
     */
    public static boolean admin = false;

    /**
     * Resultat de la consulta realitzada a la base de dades.
     * Aquest atribut emmagatzema un objecte ResultSet que conté els resultats de la consulta SQL.
     */
    public static ResultSet ultimaConsulta = null;
}
