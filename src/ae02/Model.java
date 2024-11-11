package ae02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Model {

	public Model() {
	}

	boolean existeixUsuari(String usuari, String contrasenya) {
		String contrasenyaCifrada = Util.cifraContrasenya(contrasenya);
		boolean existeix = false;
		try {
			// Cargar el driver de MySQL
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecer la conexión con la base de datos
			Connection con;

			if (Dades.nomUsuari != null) {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", Dades.nomUsuari,
						Dades.hashContrasenyaUsuari);
			} else {
				// POSIBLE PREGUNTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", "admin",
						Util.cifraContrasenya("admin"));
			}

			// Crear un Statement para ejecutar consultas
			Statement stmt = con.createStatement();

			// Usar una consulta preparada para evitar SQL Injection
			String query = "SELECT * FROM users WHERE login = ? AND password = ?";
			PreparedStatement pstmt = con.prepareStatement(query);
//			System.out.println(usuari);
//			System.out.println(contrasenya);
			pstmt.setString(1, usuari);
			pstmt.setString(2, contrasenyaCifrada);

			// Ejecutar la consulta
			ResultSet rs = pstmt.executeQuery();

			// Comprobar si hay resultados
			if (rs.next()) {
				// El usuario existe
//              BORRAAAARRRRRRRRRR  
//				System.out.println("Usuario encontrado: " + rs.getString("login"));
				existeix = true;
			}

			// Cerrar ResultSet, PreparedStatement, y Connection
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			if (Dades.nomUsuari == null) {
				if (e.getErrorCode() == 1045) { // Código de error para "Access denied"
					JOptionPane.showMessageDialog(null, "El usuario " + usuari + " o su contraseña NO es correcto",
							"Error de Autenticación", JOptionPane.WARNING_MESSAGE);
//                System.out.println("Error: Acceso denegado para el usuario.");
				} else {
					JOptionPane.showMessageDialog(null,
							"Ha ocurrido un error relacionado con la base de datos, llame a sistemas",
							"Error de Base de datos", JOptionPane.WARNING_MESSAGE);
//                System.out.println("SQLException: " + e.getMessage());
				}
			}
			existeix = false;
		} catch (Exception e) {
			System.out.println("Error general: " + e.getMessage());
		}
		return existeix;

	}

	boolean existeixUsuari(String usuari, String contrasenya, boolean esFormLogin) {
		String contrasenyaCifrada = Util.cifraContrasenya(contrasenya);
		boolean existeix = false;
		try {
			// Cargar el driver de MySQL
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecer la conexión con la base de datos
			Connection con;

				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", usuari,
						Util.cifraContrasenya(contrasenya));			

			// Crear un Statement para ejecutar consultas
			Statement stmt = con.createStatement();

			// Usar una consulta preparada para evitar SQL Injection
			String query = "SELECT * FROM users WHERE login = ? AND password = ?";
			PreparedStatement pstmt = con.prepareStatement(query);
//			System.out.println(usuari);
//			System.out.println(contrasenya);
			pstmt.setString(1, usuari);
			pstmt.setString(2, contrasenyaCifrada);

			// Ejecutar la consulta
			ResultSet rs = pstmt.executeQuery();

			// Comprobar si hay resultados
			if (rs.next()) {
				// El usuario existe
//              BORRAAAARRRRRRRRRR  
//				System.out.println("Usuario encontrado: " + rs.getString("login"));
				existeix = true;
			}

			// Cerrar ResultSet, PreparedStatement, y Connection
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
				if (e.getErrorCode() == 1045) { // Código de error para "Access denied"
					JOptionPane.showMessageDialog(null, "El usuario " + usuari + " o su contraseña NO es correcto",
							"Error de Autenticación", JOptionPane.WARNING_MESSAGE);
//                System.out.println("Error: Acceso denegado para el usuario.");
				} else {
					JOptionPane.showMessageDialog(null,
							"Ha ocurrido un error relacionado con la base de datos, llame a sistemas",
							"Error de Base de datos", JOptionPane.WARNING_MESSAGE);
//                System.out.println("SQLException: " + e.getMessage());
				}
			existeix = false;
		} catch (Exception e) {
			System.out.println("Error general: " + e.getMessage());
		}
		return existeix;

	}

	boolean registraUsuari(String usuari, String contrasenya) {
		String contrasenyaCifrada = Util.cifraContrasenya(contrasenya);
		boolean registrat = false;

		try {
			// Verificar si el usuario no existe para registrarlo
			if (!existeixUsuari(usuari, contrasenya)) {
				// Cargar el driver de MySQL
				Class.forName("com.mysql.cj.jdbc.Driver");

				// Establecer la conexión con la base de datos
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", Dades.nomUsuari,
						Dades.hashContrasenyaUsuari);

				// Usar una consulta preparada para evitar SQL Injection
				String query = "INSERT INTO users (login,password,type) VALUES (?,?,'client')";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, usuari);
				pstmt.setString(2, contrasenyaCifrada);

				// Ejecutar la consulta de inserción
				int rowsAffected = pstmt.executeUpdate();
				registrat = rowsAffected > 0;

				pstmt.close();

				// Crear el usuario en la base de datos con permisos específicos si se insertó
				// correctamente
				if (registrat) {
					// Crear el usuario en MySQL
					String createUserQuery = "CREATE USER ? IDENTIFIED BY ?";
					PreparedStatement createUserStmt = con.prepareStatement(createUserQuery);
					createUserStmt.setString(1, usuari);
					createUserStmt.setString(2, contrasenyaCifrada);
					System.out.println(contrasenyaCifrada);
					createUserStmt.executeUpdate();
					createUserStmt.close();

					// Otorgar permisos SELECT sobre la tabla `population`
					String grantPermissionQuery = "GRANT SELECT ON population.population TO ?";
					PreparedStatement grantStmt = con.prepareStatement(grantPermissionQuery);
					grantStmt.setString(1, usuari);
					grantStmt.executeUpdate();
					grantStmt.close();
				}

				con.close();
			} else {
				JOptionPane.showMessageDialog(null, "L'usuari ja existeix", "Error Creacio usuari",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Ha ocurrido un error relacionado con la base de datos, llame a sistemas", "Error de Base de datos",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error general: " + e.getMessage());
		}
		return registrat;
	}

	String[] tornaCapcaleresCsv(File arxiu) {
		String[] capcaleres = null;
		FileReader fr;
		try {
			fr = new FileReader(arxiu);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			if (linea != null) {
				capcaleres = linea.split(";");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return capcaleres;
	}

	boolean creacioTaulaAmbCamps(String nomTaula, String[] campsTaula) {
		boolean creada = false;

		try {
			// Cargar el driver de MySQL
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecer la conexión con la base de datos
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", Dades.nomUsuari,
					Dades.hashContrasenyaUsuari);

			// Primero, eliminar la tabla si ya existe
			String dropQuery = "DROP TABLE IF EXISTS " + nomTaula + ";";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(dropQuery);

			// Construir la consulta de creación de tabla
			String query = "CREATE TABLE " + nomTaula + " (";
			for (int i = 0; i < campsTaula.length; i++) {
				query += campsTaula[i] + " VARCHAR(30)";
				if (i < campsTaula.length - 1) {
					query += ", ";
				}
			}
			query += ");";
			// Ejecutar la consulta de creación de tabla
			stmt.executeUpdate(query);
			creada = true;

			// Cerrar la conexión y el Statement
			stmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			creada = false;
		}

		return creada;
	}

	boolean recorreCsvICreaXmlPerCadaLinea(File arxiu) {

		try {
			FileReader fr = new FileReader(arxiu);
			BufferedReader br = new BufferedReader(fr);
			// Leer y descartar la línea de encabezado
			String linea = br.readLine();

			// Leer cada línea de datos
			while ((linea = br.readLine()) != null) {
				// Dividir la línea en partes usando coma como delimitador
				String[] datos = linea.split(";");

				// Crear el objeto Country usando los datos de la línea
				Country country = new Country(datos[0], // country
						Integer.parseInt(datos[1].trim()), // population
						Integer.parseInt(datos[2].trim()), // density
						Integer.parseInt(datos[3].trim()), // area
						Double.parseDouble(datos[4].replace(",", ".").trim()), // fertility
						Integer.parseInt(datos[5].trim()), // age
						datos[6].trim(), // urban (mantener como String debido al símbolo de %)
						Double.parseDouble(datos[7].replace(",", ".").trim()) // share
				);
				creaXmlPopulation(country);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	boolean creaXmlPopulation(Country country) {
		// Verificar y crear el directorio ./xml si no existe
		File directori = new File("./xml");
		if (!directori.exists()) {
			directori.mkdirs(); // Crea el directorio y subdirectorios si es necesario
		}

		try {
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();

			// Crear elemento raíz "country"
			Element raiz = doc.createElement("country");
			doc.appendChild(raiz);

			// Crear subelementos para cada propiedad del país
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(country.getCountry()));
			raiz.appendChild(name);

			Element population = doc.createElement("population");
			population.appendChild(doc.createTextNode(String.valueOf(country.getPopulation())));
			raiz.appendChild(population);

			Element density = doc.createElement("density");
			density.appendChild(doc.createTextNode(String.valueOf(country.getDensity())));
			raiz.appendChild(density);

			Element area = doc.createElement("area");
			area.appendChild(doc.createTextNode(String.valueOf(country.getArea())));
			raiz.appendChild(area);

			Element fertility = doc.createElement("fertility");
			fertility.appendChild(doc.createTextNode(String.valueOf(country.getFertility())));
			raiz.appendChild(fertility);

			Element age = doc.createElement("age");
			age.appendChild(doc.createTextNode(String.valueOf(country.getAge())));
			raiz.appendChild(age);

			Element urban = doc.createElement("urban");
			urban.appendChild(doc.createTextNode(country.getUrban()));
			raiz.appendChild(urban);

			Element share = doc.createElement("share");
			share.appendChild(doc.createTextNode(String.valueOf(country.getShare())));
			raiz.appendChild(share);

			// Configurar el transformador para escribir el XML
			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

			// Escribir el XML en un archivo
			DOMSource source = new DOMSource(doc);
			try (FileWriter fw = new FileWriter("./xml/" + country.getCountry() + ".xml")) {
				StreamResult result = new StreamResult(fw);
				aTransformer.transform(source, result);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} catch (TransformerException ex) {
			System.out.println("Error escribiendo el documento XML");
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			System.out.println("Error construyendo el documento XML");
			ex.printStackTrace();
		}
		return false;
	}

	Country xmlACountry(File arxiu) {
		Country country = null;

		try {
			// Configuración para parsear el XML
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(arxiu); // Lee el archivo XML
			document.getDocumentElement().normalize(); // Normaliza el documento XML

			// Obtener el nodo raíz del XML
			Element raiz = document.getDocumentElement();
//			System.out.println("Contingut XML: " + raiz.getNodeName());

			// Leer el nodo "country" y asignar los valores a un objeto Country
			NodeList nodeList = document.getElementsByTagName("country");
			if (nodeList.getLength() > 0) {
				Element countryElement = (Element) nodeList.item(0); // Solo un país por archivo

				// Asignar valores a los atributos del objeto Country directamente
				String name = countryElement.getElementsByTagName("name").item(0).getTextContent();
				int population = Integer
						.parseInt(countryElement.getElementsByTagName("population").item(0).getTextContent());
				int density = Integer.parseInt(countryElement.getElementsByTagName("density").item(0).getTextContent());
				int area = Integer.parseInt(countryElement.getElementsByTagName("area").item(0).getTextContent());
				double fertility = Double
						.parseDouble(countryElement.getElementsByTagName("fertility").item(0).getTextContent());
				int age = Integer.parseInt(countryElement.getElementsByTagName("age").item(0).getTextContent());
				String urban = countryElement.getElementsByTagName("urban").item(0).getTextContent();
				double share = Double
						.parseDouble(countryElement.getElementsByTagName("share").item(0).getTextContent());

				// Crear el objeto Country con los datos extraídos
				country = new Country(name, population, density, area, fertility, age, urban, share);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return country;
	}

	boolean insertaDadesXmlCreats() {
		File directory = new File("./xml/");
		// Verificar si el directorio existe
		if (!directory.exists() || !directory.isDirectory()) {
//			System.out.println("El directorio ./xml/ no existe o no es un directorio.");
			return false;
		}

		// Filtrar solo archivos XML en el directorio
		String[] xmlFiles = directory.list((dir, name) -> name.toLowerCase().endsWith(".xml"));

		// Recorrer cada archivo XML
		if (xmlFiles != null) {
			for (String fileName : xmlFiles) {
				File xmlFile = new File(directory, fileName);
//	            System.out.println("Procesando archivo: " + xmlFile.getName());

				// Convertir el archivo XML a un objeto Country
				Country country = xmlACountry(xmlFile);

				// Verificar si el objeto Country se creó correctamente
				if (country != null) {
					// Llamar a toString() para mostrar o almacenar la representación del país
//					System.out.println(country.toString()); // Aquí puedes hacer lo que necesites con el toString
					registraCountry(country);
				} else {
//					System.out.println("Error al procesar el archivo XML: " + xmlFile.getName());
				}
			}
			return true;
		} else {
//			System.out.println("No se encontraron archivos XML en el directorio ./xml/");
			return false;
		}
	}

	String concatenacioArxiusXml() {
		StringBuilder content = new StringBuilder();
		File directory = new File("./xml/");
		if (directory.exists() && directory.isDirectory()) {
			// Filtrar los archivos XML
			String[] xmlFiles = directory.list((dir, name) -> name.toLowerCase().endsWith(".xml"));

			// Recorrer cada archivo XML
			if (xmlFiles != null) {
				for (String fileName : xmlFiles) {
					File xmlFile = new File(directory, fileName);
					System.out.println("Procesando archivo: " + xmlFile.getName());

					// Leer el contenido del archivo XML
					try {
						BufferedReader reader = new BufferedReader(new FileReader(xmlFile));
						String line;
						while ((line = reader.readLine()) != null) {
							content.append(line).append("\n");
						}
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("No se encontraron archivos XML en el directorio.");
			}
		} else {
			System.out.println("El directorio ./xml/ no existe o no es un directorio.");
		}
		return content.toString();
	}

	boolean registraCountry(Country country) {
		boolean registrat = false;

		try {
			// Cargar el driver de MySQL
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecer la conexión con la base de datos
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", Dades.nomUsuari,
					Dades.hashContrasenyaUsuari);

			// Usar una consulta preparada para evitar SQL Injection
			String query = "INSERT INTO population (country, population, density, area, fertility, age, urban, share) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, country.getCountry()); // Nombre del país
			pstmt.setInt(2, country.getPopulation()); // Población
			pstmt.setInt(3, country.getDensity()); // Densidad
			pstmt.setInt(4, country.getArea()); // Área
			pstmt.setDouble(5, country.getFertility()); // Fertilidad
			pstmt.setInt(6, country.getAge()); // Edad media
			pstmt.setString(7, country.getUrban()); // Porcentaje urbano
			pstmt.setDouble(8, country.getShare()); // Participación en la población global

			// Ejecutar la consulta de inserción
			int rowsAffected = pstmt.executeUpdate();
			registrat = rowsAffected > 0;

			pstmt.close();

			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Ha ocurrido un error relacionado con la base de datos, llame a sistemas", "Error de Base de datos",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error general: " + e.getMessage());
		}
		return registrat;
	}

	boolean importaArxiuCsvICreaTaulaAmbEll(File arxiu, String nomTaula) {
		String[] camps = tornaCapcaleresCsv(arxiu);
		return creacioTaulaAmbCamps(nomTaula, camps);
	}

	boolean importaCsv(File arxiu, String nomTaula) {
		importaArxiuCsvICreaTaulaAmbEll(arxiu, nomTaula);
		recorreCsvICreaXmlPerCadaLinea(arxiu);
		insertaDadesXmlCreats();
		return true;
	}

	public ResultSet executarConsulta(String consultaSQL) {

		ResultSet resultSet = null;
		Statement statement = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecer la conexión con la base de datos
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", Dades.nomUsuari,
					Dades.hashContrasenyaUsuari);
			// Crear un Statement para ejecutar la consulta
			statement = con.createStatement();

			// Ejecutar la consulta y obtener el ResultSet
			resultSet = statement.executeQuery(consultaSQL);
		}catch (SQLException e) {
	        // Si ocurre una SQLException, verificamos el código de error para proporcionar un mensaje específico
	        if (e.getErrorCode() == 1044) { // Errores de permisos en MySQL, por ejemplo, "SELECT command denied"
	            JOptionPane.showMessageDialog(null, "Error de permisos: No tienes acceso para realizar esta consulta.",
	                    "Error de SQL", JOptionPane.ERROR_MESSAGE);
	        } else {
	            // En caso de error SQL general
	            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage(),
	                    "Error de SQL", JOptionPane.ERROR_MESSAGE);
	        }
	        e.printStackTrace(); // Opcional, para depuración en consola

	    } catch (Exception e) {
	        // Manejar otros tipos de excepciones (por ejemplo, ClassNotFoundException)
	        JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(),
	                "Error General", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace(); // Opcional, para depuración en consola
	    }

		return resultSet;
	}

}
