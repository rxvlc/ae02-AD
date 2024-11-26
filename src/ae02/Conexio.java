package ae02;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexio {

	public static Connection conexio;

	public static Connection getConexio() {
		return conexio;
	}

	public static void setConexio(Connection conexioAFicar) {
		conexio = conexioAFicar;
	}

	public static void tancaConexio() {
		try {
			// Intentar cerrar la conexión
			conexio.close();

			// Mostrar un mensaje de éxito cuando la conexión se cierre correctamente
			JOptionPane.showMessageDialog(null, "Conexión cerrada exitosamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			// Mostrar un mensaje de error si ocurre una excepción al cerrar la conexión
			JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);

			// Imprimir el stack trace para depuración
			e.printStackTrace();
		}
	}

}
