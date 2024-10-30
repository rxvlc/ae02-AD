package ae02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Model {

    public Model() {}

    boolean existeixUsuari(String usuari, String contrasenya) {
    	String contrasenyaCifrada = Util.cifraContrasenya(contrasenya);
    	boolean existeix = false;
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer la conexión con la base de datos
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", usuari, contrasenyaCifrada);
            
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
        } catch (SQLException e) {
            if (e.getErrorCode() == 1045) { // Código de error para "Access denied"
            	JOptionPane.showMessageDialog(null, "El usuario "+usuari+" o su contraseña NO es correcto", "Error de Autenticación", JOptionPane.WARNING_MESSAGE);
//                System.out.println("Error: Acceso denegado para el usuario.");
            } else {
            	JOptionPane.showMessageDialog(null, "Ha ocurrido un error relacionado con la base de datos, llame a sistemas", "Error de Base de datos", JOptionPane.WARNING_MESSAGE);
                System.out.println("SQLException: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        return existeix;
        
    }

}
