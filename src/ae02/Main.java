package ae02;

import ae02.Vista.*;

public class Main {

    /**
     * Punt d'entrada principal de l'aplicació.
     * Aquest mètode inicialitza les diferents parts de l'aplicació, com la vista,
     * el model i el controlador, i s'encarrega de configurar el flux principal
     * de l'execució.
     *
     * En concret:
     * - Crea instàncies de les vistes (login, registre i vista principal).
     * - Crea una instància del model, que conté la lògica de negoci i accés a dades.
     * - Crea el controlador, que connecta les vistes amb el model i gestiona
     *   les interaccions de l'usuari.
     *
     * @param args Arguments passats per línia de comandes (no s'utilitzen en aquesta aplicació).
     */
    public static void main(String[] args) {
        // Inicialització de les vistes
        VistaLogin login = new VistaLogin();
        Vista vista = new Vista();
        VistaRegistre registreUsuari = new VistaRegistre();
        
        // Inicialització del model
        Model model = new Model();
        
        // Inicialització del controlador
        Controlador controlador = new Controlador(login, vista, registreUsuari, model);
    }
}
