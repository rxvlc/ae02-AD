package ae02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Model {

    public Model() {}

    boolean existeixUsuari(String usuari, String contrasenya) {
    	String contrasenyaCifrada = Util.cifraContrasenya(contrasenya);
    	boolean existeix = false;
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer la conexión con la base de datos
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", "root", "");
            
            // Crear un Statement para ejecutar consultas
            Statement stmt = con.createStatement();

            // Usar una consulta preparada para evitar SQL Injection
            String query = "SELECT * FROM users WHERE login = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            System.out.println(usuari);
            System.out.println(contrasenya);
            pstmt.setString(1, usuari);
            pstmt.setString(2, contrasenyaCifrada);

            // Ejecutar la consulta
            ResultSet rs = pstmt.executeQuery();

            // Comprobar si hay resultados
            if (rs.next()) {
                // El usuario existe
//              BORRAAAARRRRRRRRRR  System.out.println("Usuario encontrado: " + rs.getString("login"));
            	existeix = true;
            }

            // Cerrar ResultSet, PreparedStatement, y Connection
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return existeix;
        
    }

}
