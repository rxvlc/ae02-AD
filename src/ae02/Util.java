package ae02;

import java.security.MessageDigest;

public class Util {

    /**
     * Cifra una contrasenya utilitzant l'algorisme MD5.
     * 
     * Aquest mètode rep una contrasenya en format de text pla i la converteix en una cadena
     * de text en format hexadecimal que representa el seu hash MD5. Això s'utilitza habitualment
     * per emmagatzemar contrasenyes de forma segura (encara que MD5 ja no és considerat completament segur).
     *
     * @param contrasenyaDescifra La contrasenya en text pla que es vol xifrar.
     * @return La contrasenya xifrada en format MD5 com una cadena hexadecimal.
     */
    public static String cifraContrasenya(String contrasenyaDescifra) {
        String contrasenyaCifrada = "";
        try {
            // Obtenir la instància de MessageDigest per MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Convertir la contrasenya en bytes i calcular el hash MD5
            byte[] messageDigest = md.digest(contrasenyaDescifra.getBytes());

            // Convertir el hash a un format hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            // Retornar el hash en format cadena
            contrasenyaCifrada = hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return contrasenyaCifrada;
    }
}
