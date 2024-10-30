package ae02;

import java.security.MessageDigest;

public class Util {

	public static String cifraContrasenya(String contrasenyaDescifra) {
		String contrasenyaCifrada = "";
		   try {
	            // Obtener la instancia de MessageDigest para MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");

	            // Convertir la contraseña en bytes y calcular el hash MD5
	            byte[] messageDigest = md.digest(contrasenyaDescifra.getBytes());

	            // Convertir el hash en un formato hexadecimal
	            StringBuilder hexString = new StringBuilder();
	            for (byte b : messageDigest) {
	                String hex = Integer.toHexString(0xff & b);
	                if (hex.length() == 1) hexString.append('0');
	                hexString.append(hex);
	            }

	            // Devolver el hash en formato de cadena
	            contrasenyaCifrada = hexString.toString();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		   return contrasenyaCifrada;
	}
	
}
