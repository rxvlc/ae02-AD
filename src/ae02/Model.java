package ae02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
import org.w3c.dom.NodeList;

public class Model {

	/**
	 * Constructor per defecte de la classe Model.
	 * Aquest constructor no realitza cap acció especial en crear una nova instància de la classe Model.
	 */
	public Model() {
	}


	/**
	 * Métode inicial per a indicar si un usuari existeix
	 * 
	 * @param usuari      el nom d'usuari
	 * @param contrasenya la contrasenya sense xifrar
	 * @return torna true o false depenent si existeix
	 */
	boolean existeixUsuari(String usuari, String contrasenya) {
		String contrasenyaCifrada = Util.cifraContrasenya(contrasenya);
		boolean existeix = false; // Indica si el usuario existe en la base de datos
		Dades.admin = false; // Indica si el usuario tiene permisos de administrador

		try {
			// Cargar el driver de MySQL
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecer la conexión con la base de datos utilizando las credenciales del
			// usuario
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", usuari,
					contrasenyaCifrada);
			Conexio.setConexio(con);
			existeix = true; // Si la conexión se establece, el usuario existe

			try {
				// Intentar acceder a la tabla `users`
				String query = "SELECT * FROM users LIMIT 1";
				Statement stmt = Conexio.getConexio().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				if (rs.next()) {
					// Si la consulta tiene éxito, el usuario tiene permisos
					Dades.admin = true;
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// Si la consulta falla, no tiene permisos para la tabla `users`
				System.out.println("El usuario no tiene permisos para acceder a la tabla `users`.");
			}

		} catch (SQLException e) {
			// Manejo de errores relacionados con la base de datos
			if (e.getErrorCode() == 1045) { // Error "Access denied"
				JOptionPane.showMessageDialog(null, "El usuario " + usuari + " o su contraseña NO es correcto",
						"Error de Autenticación", JOptionPane.WARNING_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null,
						"Ha ocurrido un error relacionado con la base de datos, llame a sistemas",
						"Error de Base de datos", JOptionPane.WARNING_MESSAGE);
			}
			existeix = false;
		} catch (Exception e) {
			System.out.println("Error general: " + e.getMessage());
		}

		return existeix; // Retorna true si el usuari existeix, false en cas contrari
	}

	/**
	 * Comprova si existeix almenys un usuari registrat a la base de dades. Aquesta
	 * funció consulta la taula `users` i comprova si hi ha registres.
	 *
	 * @param usuari Nom d'usuari a comprovar.
	 * @return Retorna `true` si s'ha trobat almenys un usuari, `false` en cas
	 *         contrari.
	 */
	boolean existeixUsuariRegistre(String usuari) {
		boolean res = false;
		try {
			// Consulta SQL per comptar els usuaris registrats
			String query = "SELECT COUNT(*) FROM users WHERE login = ?";
			PreparedStatement stmt = Conexio.getConexio().prepareStatement(query);
			stmt.setString(1, usuari);

			// Executar la consulta i obtenir el resultat
			ResultSet rs = stmt.executeQuery();

			// Si el resultat té almenys una fila amb una quantitat > 0, existeix l'usuari
			if (rs.next() && rs.getInt(1) > 0) {
				res = true;
			}

			// Tancar el ResultSet i el PreparedStatement
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// Si ocorre un error de SQL, mostrar el missatge d'error
			e.printStackTrace();
		} catch (Exception e) {
			// En cas d'altres excepcions generals
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Registra un usuari a la base de dades si no existeix prèviament. La
	 * contrasenya s'encripta abans de ser guardada. A més, crea un usuari a MySQL
	 * amb permisos específics per accedir a la taula `population`.
	 *
	 * @param usuari      El nom d'usuari a registrar.
	 * @param contrasenya La contrasenya de l'usuari a registrar.
	 * @return Retorna `true` si l'usuari es registra correctament, `false` en cas
	 *         contrari.
	 */
	boolean registraUsuari(String usuari, String contrasenya) {
		// Cifrar la contrasenya abans d'inserir-la a la base de dades
		String contrasenyaCifrada = Util.cifraContrasenya(contrasenya);
		boolean registrat = false;

		try {
			// Comprovar si l'usuari ja existeix a la base de dades

			// Preparar la consulta SQL per a evitar SQL Injection
			String consulta = "INSERT INTO users (login, password, type) VALUES (?, ?, 'client')";
			PreparedStatement stmtPreparat = Conexio.getConexio().prepareStatement(consulta);
			stmtPreparat.setString(1, usuari);
			stmtPreparat.setString(2, contrasenyaCifrada);

			// Executar la consulta d'inserció
			int filesAfectades = stmtPreparat.executeUpdate();
			registrat = filesAfectades > 0;

			stmtPreparat.close();

			// Si l'usuari es registra correctament, es crea també a MySQL amb permisos
			// específics
			if (registrat) {
				// Crear l'usuari a MySQL
				String consultaCrearUsuari = "CREATE USER ? IDENTIFIED BY ?";
				PreparedStatement stmtCrearUsuari = Conexio.getConexio().prepareStatement(consultaCrearUsuari);
				stmtCrearUsuari.setString(1, usuari);
				stmtCrearUsuari.setString(2, contrasenyaCifrada);
				System.out.println(contrasenyaCifrada);
				stmtCrearUsuari.executeUpdate();
				stmtCrearUsuari.close();

				// Concedir permisos SELECT sobre la taula `population`
				String consultaConcedirPermisos = "GRANT SELECT ON population.population TO ?";
				PreparedStatement stmtConcedirPermisos = Conexio.getConexio()
						.prepareStatement(consultaConcedirPermisos);
				stmtConcedirPermisos.setString(1, usuari);
				stmtConcedirPermisos.executeUpdate();
				stmtConcedirPermisos.close();

			}

		} catch (SQLException e) {
			// En cas d'error amb la base de dades, mostrar un missatge d'error
			JOptionPane.showMessageDialog(null,
					"Ha ocorregut un error relacionat amb la base de dades, crida a sistemes", "Error de Base de dades",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			// En cas d'error general, mostrar un missatge de consola
			System.out.println("Error general: " + e.getMessage());
		}
		return registrat;
	}

	/**
	 * Llegeix la primera línia d'un fitxer CSV i retorna les capçaleres com un
	 * array de strings. Aquesta funció assumeix que les capçaleres estan separades
	 * per punts i coma (;) i es troben a la primera línia del fitxer.
	 *
	 * @param arxiu El fitxer CSV del qual es vol llegir la primera línia.
	 * @return Retorna un array de strings amb les capçaleres del fitxer. Si no hi
	 *         ha capçaleres, retorna `null`.
	 */
	String[] tornaCapcaleresCsv(File arxiu) {
		// Declaració de la variable que contindrà les capçaleres
		String[] capcaleres = null;
		FileReader fr;

		try {
			// Crear un FileReader per llegir el fitxer
			fr = new FileReader(arxiu);

			// Crear un BufferedReader per llegir línies del fitxer de forma eficient
			BufferedReader br = new BufferedReader(fr);

			// Llegir la primera línia del fitxer
			String linea = br.readLine();

			// Comprovar si la línia no és null (especificant que hi ha dades en el fitxer)
			if (linea != null) {
				// Separar la línia per punts i coma i guardar el resultat en l'array de
				// capçaleres
				capcaleres = linea.split(";");
			}

			// Tancar el BufferedReader i el FileReader (opcional aquí, però recomanable per
			// a la neteja)
			br.close();
			fr.close();

		} catch (IOException e) {
			// Maneig d'errors d'entrada/sortida (com problemes per llegir el fitxer)
			e.printStackTrace();
		}

		// Retornar les capçaleres (pot ser null si no hi ha capçaleres o si hi ha un
		// error)
		return capcaleres;
	}

	/**
	 * Crea una taula a la base de dades amb els noms de camps proporcionats. Si la
	 * taula ja existeix, la elimina abans de crear una nova.
	 *
	 * @param nomTaula   El nom de la taula que es vol crear.
	 * @param campsTaula Un array de strings amb els noms dels camps (columnes) de
	 *                   la taula.
	 * @return Retorna `true` si la taula es va crear correctament, `false` en cas
	 *         contrari.
	 */
	boolean creacioTaulaAmbCamps(String nomTaula, String[] campsTaula) {
		// Variable per indicar si la taula ha sigut creada
		boolean creada = false;

		try {
			// Primer, eliminar la taula si ja existeix
			String dropQuery = "DROP TABLE IF EXISTS " + nomTaula + ";";
			Statement stmt = Conexio.getConexio().createStatement();
			stmt.executeUpdate(dropQuery);

			// Construir la consulta per a crear la taula
			String query = "CREATE TABLE " + nomTaula + " (";
			for (int i = 0; i < campsTaula.length; i++) {
				// Afegir cada camp amb tipus de dades VARCHAR(30)
				query += campsTaula[i] + " VARCHAR(30)";
				// Afegir una coma si no és l'últim camp
				if (i < campsTaula.length - 1) {
					query += ", ";
				}
			}
			query += ");"; // Tancar la consulta de creació de la taula

			// Executar la consulta de creació de la taula
			stmt.executeUpdate(query);
			creada = true; // La taula ha sigut creada amb èxit

			// Tancar el Statement
			stmt.close();

		} catch (Exception e) {
			// En cas d'error, mostrar el trace de l'excepció i establir creada com a false
			e.printStackTrace();
			creada = false;
		}

		// Retornar si la taula s'ha creat o no
		return creada;
	}

	/**
	 * Llegeix un fitxer CSV, processa cada línia de dades i crea un arxiu XML per a
	 * cada línia. La primera línia es considera com a capçalera i es descarta. Per
	 * cada línia de dades, es crea un objecte `Country` i es genera un arxiu XML
	 * amb la informació.
	 *
	 * @param arxiu El fitxer CSV que conté les dades a processar.
	 * @return Retorna `true` si s'han processat les línies correctament, `false` en
	 *         cas contrari.
	 */
	boolean recorreCsvICreaXmlPerCadaLinea(File arxiu) {
		try {
			// Obrir el fitxer CSV per llegir-lo
			FileReader fr = new FileReader(arxiu);
			BufferedReader br = new BufferedReader(fr);

			// Llegir i descartar la línia d'encapçalament
			String linea = br.readLine();

			// Llegir cada línia de dades
			while ((linea = br.readLine()) != null) {
				// Dividir la línia en parts utilitzant el caràcter de separació ';'
				String[] dades = linea.split(";");

				// Crear l'objecte Country amb les dades de la línia
				Country country = new Country(dades[0], // nom del país
						Integer.parseInt(dades[1].trim()), // població
						Integer.parseInt(dades[2].trim()), // densitat
						Integer.parseInt(dades[3].trim()), // superfície
						Double.parseDouble(dades[4].replace(",", ".").trim()), // fertilitat
						Integer.parseInt(dades[5].trim()), // edat mitjana
						dades[6].trim(), // urbà (mantenir com a String per al símbol de %)
						Double.parseDouble(dades[7].replace(",", ".").trim()) // participació
				);

				// Cridar a la funció per crear l'arxiu XML per a cada país
				creaXmlPopulation(country);
			}
		} catch (Exception e) {
			// Capturar qualsevol excepció i mostrar l'error
			e.printStackTrace();
		}
		// Retornar `false` si no s'ha pogut completar el procés
		return false;
	}

	/**
	 * Crea un fitxer XML per a un país amb les seves propietats. Si el directori
	 * "./xml" no existeix, es crea automàticament. El fitxer XML conté una
	 * estructura amb els elements del país (nom, població, densitat, etc.). El
	 * fitxer es guarda en el directori "./xml/" amb el nom del país com a nom de
	 * fitxer.
	 *
	 * @param country L'objecte `Country` que conté les dades del país a escriure en
	 *                el fitxer XML.
	 * @return Retorna `true` si el fitxer XML s'ha creat correctament, `false` si
	 *         hi ha hagut un error.
	 */
	boolean creaXmlPopulation(Country country) {
		// Verificar i crear el directori ./xml si no existeix
		File directori = new File("./xml");
		if (!directori.exists()) {
			directori.mkdirs(); // Crear el directori i subdirectoris si cal
		}

		try {
			// Crear el DocumentBuilder per generar el document XML
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();

			// Crear l'element arrel "country"
			Element raiz = doc.createElement("country");
			doc.appendChild(raiz);

			// Crear subelements per cada propietat del país
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(country.getCountry())); // Nom del país
			raiz.appendChild(name);

			Element population = doc.createElement("population");
			population.appendChild(doc.createTextNode(String.valueOf(country.getPopulation()))); // Població
			raiz.appendChild(population);

			Element density = doc.createElement("density");
			density.appendChild(doc.createTextNode(String.valueOf(country.getDensity()))); // Densitat
			raiz.appendChild(density);

			Element area = doc.createElement("area");
			area.appendChild(doc.createTextNode(String.valueOf(country.getArea()))); // Superfície
			raiz.appendChild(area);

			Element fertility = doc.createElement("fertility");
			fertility.appendChild(doc.createTextNode(String.valueOf(country.getFertility()))); // Fertilitat
			raiz.appendChild(fertility);

			Element age = doc.createElement("age");
			age.appendChild(doc.createTextNode(String.valueOf(country.getAge()))); // Edat mitjana
			raiz.appendChild(age);

			Element urban = doc.createElement("urban");
			urban.appendChild(doc.createTextNode(country.getUrban())); // Urbà (es manté com String per al símbol de %)
			raiz.appendChild(urban);

			Element share = doc.createElement("share");
			share.appendChild(doc.createTextNode(String.valueOf(country.getShare()))); // Participació
			raiz.appendChild(share);

			// Configurar el transformador per escriure el document XML
			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

			// Escriure el document XML al fitxer
			DOMSource source = new DOMSource(doc);
			try (FileWriter fw = new FileWriter("./xml/" + country.getCountry() + ".xml")) {
				StreamResult result = new StreamResult(fw);
				aTransformer.transform(source, result);
			} catch (IOException e) {
				// Si es produeix un error en escriure el fitxer
				e.printStackTrace();
			}
			return true;
		} catch (TransformerException ex) {
			// Si es produeix un error al generar el document XML
			System.out.println("Error escrivint el document XML");
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			// Si es produeix un error en la configuració del document XML
			System.out.println("Error construint el document XML");
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Lee un archivo XML y convierte los datos del país en un objeto `Country`. El
	 * archivo XML debe contener los elementos correspondientes a un país (nombre,
	 * población, densidad, área, fertilidad, edad, urbano, y participación).
	 *
	 * @param arxiu El archivo XML que contiene los datos del país.
	 * @return Un objeto `Country` con los datos extraídos del archivo XML, o `null`
	 *         si hay un error.
	 */
	Country xmlACountry(File arxiu) {
		Country country = null;

		try {
			// Configuració per parsejar el XML
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(arxiu); // Llegeix el fitxer XML
			document.getDocumentElement().normalize(); // Normalitza el document XML

			// Obtenir el node arrel del XML
			Element raiz = document.getDocumentElement();

			// Llegir el node "country" i assignar els valors a un objecte Country
			NodeList nodeList = document.getElementsByTagName("country");
			if (nodeList.getLength() > 0) {
				Element countryElement = (Element) nodeList.item(0); // Només un país per arxiu

				// Assignar valors als atributs de l'objecte Country directament
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

				// Crear l'objecte Country amb les dades extretes
				country = new Country(name, population, density, area, fertility, age, urban, share);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return country;
	}

	/**
	 * Inserta datos de los archivos XML creados en el directorio ./xml/. Este
	 * método busca los archivos XML dentro de dicho directorio, los convierte en
	 * objetos `Country`, y registra cada uno de esos objetos llamando al método
	 * `registraCountry()`.
	 *
	 * @return `true` si se procesaron archivos XML correctamente, o `false` si hubo
	 *         algún error o no se encontraron archivos XML.
	 */
	boolean insertaDadesXmlCreats() {
		File directory = new File("./xml/");

		// Verificar si el directorio existe
		if (!directory.exists() || !directory.isDirectory()) {
			JOptionPane.showMessageDialog(null, "El directorio ./xml/ no existe o no es un directorio.",
					"Error de Directorio", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Filtrar solo archivos XML en el directorio
		String[] xmlFiles = directory.list((dir, name) -> name.toLowerCase().endsWith(".xml"));

		// Recorrer cada archivo XML
		if (xmlFiles != null) {
			for (String fileName : xmlFiles) {
				File xmlFile = new File(directory, fileName);

				// Convertir el archivo XML a un objeto Country
				Country country = xmlACountry(xmlFile);

				// Verificar si el objeto Country se creó correctamente
				if (country != null) {
					// Llamar a registraCountry() para registrar el país
					registraCountry(country);
					JOptionPane.showMessageDialog(null, "País procesado correctamente: " + country.getCountry(),
							"Éxito", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// Si hubo un problema al procesar el archivo XML
					JOptionPane.showMessageDialog(null, "Error al procesar el archivo XML: " + xmlFile.getName(),
							"Error al Procesar", JOptionPane.ERROR_MESSAGE);
				}
			}
			return true;
		} else {
			// Si no se encontraron archivos XML en el directorio
			JOptionPane.showMessageDialog(null, "No se encontraron archivos XML en el directorio ./xml/",
					"Archivos No Encontrados", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

	/**
	 * Lliga i concatena el contingut de tots els arxius XML al directori ./xml/.
	 * Aquest mètode cerca arxius XML al directori, llig el seu contingut i el
	 * concatena en un sol String.
	 *
	 * @return El contingut concatenat de tots els arxius XML.
	 */
	String concatenacioArxiusXml() {
		StringBuilder content = new StringBuilder();
		File directory = new File("./xml/");

		// Comprovar si el directori existeix i és vàlid
		if (directory.exists() && directory.isDirectory()) {
			// Filtrar els arxius XML al directori
			String[] xmlFiles = directory.list((dir, name) -> name.toLowerCase().endsWith(".xml"));

			// Recórrer els arxius XML trobats
			if (xmlFiles != null) {
				for (String fileName : xmlFiles) {
					File xmlFile = new File(directory, fileName);
					// Mostrar un missatge informatiu sobre l'arxiu que s'està processant
					JOptionPane.showMessageDialog(null, "Processant arxiu: " + xmlFile.getName(), "Processant XML",
							JOptionPane.INFORMATION_MESSAGE);

					// Llegir el contingut de l'arxiu XML
					try (BufferedReader reader = new BufferedReader(new FileReader(xmlFile))) {
						String line;
						while ((line = reader.readLine()) != null) {
							content.append(line).append("\n");
						}
					} catch (IOException e) {
						// Si hi ha un error al llegir l'arxiu, mostrar un missatge d'error
						JOptionPane.showMessageDialog(null, "Error al llegir l'arxiu: " + xmlFile.getName(),
								"Error de Lectura", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			} else {
				// Si no es troben arxius XML, mostrar un missatge d'advertència
				JOptionPane.showMessageDialog(null, "No s'han trobat arxius XML al directori.", "Arxius No Trobats",
						JOptionPane.WARNING_MESSAGE);
			}
		} else {
			// Si el directori no existeix o no és vàlid, mostrar un missatge d'error
			JOptionPane.showMessageDialog(null, "El directori ./xml/ no existeix o no és un directori.",
					"Error de Directori", JOptionPane.ERROR_MESSAGE);
		}

		// Retornar el contingut concatenat dels arxius XML
		return content.toString();
	}

	/**
	 * Registra la informació d'un país a la base de dades a la taula 'population'.
	 * Aquest mètode utilitza una consulta preparada per evitar injeccions SQL.
	 *
	 * @param country Objeto Country que conté la informació del país a registrar.
	 * @return Retorna true si el país s'ha registrat correctament, false en cas
	 *         contrari.
	 */
	boolean registraCountry(Country country) {
		boolean registrat = false;

		try {
			// Usar una consulta preparada per evitar SQL Injection
			String query = "INSERT INTO population (country, population, density, area, fertility, age, urban, share) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = Conexio.getConexio().prepareStatement(query);
			pstmt.setString(1, country.getCountry()); // Nom del país
			pstmt.setInt(2, country.getPopulation()); // Població
			pstmt.setInt(3, country.getDensity()); // Densitat
			pstmt.setInt(4, country.getArea()); // Àrea
			pstmt.setDouble(5, country.getFertility()); // Fertilitat
			pstmt.setInt(6, country.getAge()); // Edat mitjana
			pstmt.setString(7, country.getUrban()); // Percentatge urbà
			pstmt.setDouble(8, country.getShare()); // Participació en la població global

			// Executar la consulta d'inserció
			int rowsAffected = pstmt.executeUpdate();
			registrat = rowsAffected > 0; // Comprovar si s'han afectat files

			pstmt.close();

		} catch (SQLException e) {
			// Mostrar missatge d'error a l'usuari quan es produeix un error en la base de
			// dades
			JOptionPane.showMessageDialog(null,
					"Ha ocorregut un error relacionat amb la base de dades. Si us plau, contacta amb sistemes.",
					"Error de Base de Dades", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			// Missatge d'error general
			JOptionPane.showMessageDialog(null,
					"S'ha produït un error inesperat. Si us plau, contacta amb suport tècnic.", "Error General",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error general: " + e.getMessage());
		}

		// Retornar si la inserció va ser exitosa
		return registrat;
	}

	/**
	 * Importa un arxiu CSV i crea una taula a la base de dades amb els camps
	 * definits al CSV. Aquest mètode utilitza el nom de la taula proporcionat i les
	 * capçaleres extretes del CSV.
	 * 
	 * @param arxiu    Arxiu CSV a importar.
	 * @param nomTaula Nom de la taula que es vol crear a la base de dades.
	 * @return Retorna true si la taula s'ha creat correctament amb els camps, false
	 *         en cas contrari.
	 */
	boolean importaArxiuCsvICreaTaulaAmbEll(File arxiu, String nomTaula) {
		// Obtenim les capçaleres del CSV per definir els camps de la taula
		String[] camps = tornaCapcaleresCsv(arxiu);

		// Cridem a la funció per crear la taula amb els camps obtinguts
		return creacioTaulaAmbCamps(nomTaula, camps);
	}

	/**
	 * Importa un arxiu CSV, crea la taula a la base de dades, converteix les línies
	 * en arxius XML i insereix les dades a la base de dades.
	 * 
	 * Aquest mètode combina diverses operacions: importa un CSV, crea la taula,
	 * converteix les línies a XML i inserta les dades generades a la base de dades.
	 * 
	 * @param arxiu    Arxiu CSV a importar.
	 * @param nomTaula Nom de la taula on s'han d'inserir les dades.
	 * @return Retorna true si el procés es realitza correctament, false si hi ha
	 *         algun error.
	 */
	boolean importaCsv(File arxiu, String nomTaula) {
		// Primer, importem el CSV i creem la taula amb els camps corresponents
		importaArxiuCsvICreaTaulaAmbEll(arxiu, nomTaula);

		// A continuació, recorrem el CSV i generem un arxiu XML per cada línia
		recorreCsvICreaXmlPerCadaLinea(arxiu);

		// Finalment, inserim les dades generades als arxius XML a la base de dades
		insertaDadesXmlCreats();

		// Retornem true per indicar que el procés ha finalitzat correctament
		return true;
	}

	/**
	 * Executa una consulta SQL a la base de dades i retorna el conjunt de
	 * resultats. Aquest mètode utilitza un `Statement` per executar la consulta i
	 * obtenir un `ResultSet` amb els resultats. També gestiona errors relacionats
	 * amb permisos de base de dades i errors generals d'execució.
	 * 
	 * @param consultaSQL La consulta SQL que es vol executar a la base de dades.
	 * @return Un `ResultSet` amb els resultats de la consulta, o `null` si es
	 *         produeix un error.
	 */
	ResultSet executarConsulta(String consultaSQL) {

		ResultSet resultSet = null;
		Statement statement = null;

		try {
			// Crear un Statement per executar la consulta amb configuració específica
			statement = Conexio.getConexio().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, // Permet moure's en
																								// qualsevol direcció
					ResultSet.CONCUR_READ_ONLY // Només lectura
			);

			// Executar la consulta i obtenir el ResultSet
			resultSet = statement.executeQuery(consultaSQL);
		} catch (SQLException e) {
			// Si es produeix una SQLException, verifiquem el codi d'error per proporcionar
			// un missatge específic
			System.out.println(e.getErrorCode());
			if (e.getErrorCode() == 1044) { // Errors de permisos a MySQL, per exemple, "SELECT command denied"
				JOptionPane.showMessageDialog(null, "Error de permisos: No tens accés per realitzar aquesta consulta.",
						"Error SQL", JOptionPane.ERROR_MESSAGE);
			} else if (e.getErrorCode() == 1142) { // SELECT command denied
				JOptionPane.showMessageDialog(null, "Error SQL - Comandament denegat \n\nError: \n" + e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);
			} else {
				// En cas d'error SQL general
				JOptionPane.showMessageDialog(null, "Error en executar la consulta: " + e.getMessage(), "Error SQL",
						JOptionPane.ERROR_MESSAGE);
			}
			e.printStackTrace(); // Opcional, per depuració a la consola

		} catch (Exception e) {
			// Maneig d'altres tipus d'excepcions (per exemple, ClassNotFoundException)
			JOptionPane.showMessageDialog(null, "Error inesperat: " + e.getMessage(), "Error General",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(); // Opcional, per depuració a la consola
		}

		return resultSet;
	}

	/**
	 * Exporta les dades d'una taula a un arxiu CSV. Aquest mètode agafa les dades d'un `ResultSet` 
	 * obtingut d'una consulta SQL i les escriu a un arxiu CSV, incloent els noms de les columnes com a 
	 * capçalera de l'arxiu. Si es produeix un error, retorna `false`; en cas contrari, retorna `true`.
	 * 
	 * @param dadesTaula El conjunt de resultats de la consulta SQL (ResultSet) que conté les dades a exportar.
	 * @param arxiuCsv L'arxiu CSV on s'escriuran les dades.
	 * @return `true` si l'exportació es realitza correctament, `false` si es produeix un error.
	 */
	public boolean exportaTablaACsv(ResultSet dadesTaula, File arxiuCsv) {
	    try {
	        // Crear l'arxiu CSV
	        BufferedWriter writer = new BufferedWriter(new FileWriter(arxiuCsv));

	        // Obtenir les columnes de la taula
	        ResultSetMetaData metaData = dadesTaula.getMetaData();
	        int numColumnas = metaData.getColumnCount();

	        // Escriure els encapçalaments (noms de les columnes)
	        for (int i = 1; i <= numColumnas; i++) {
	            writer.write(metaData.getColumnName(i));
	            if (i < numColumnas)
	                writer.write(";");
	        }
	        writer.newLine();

	        dadesTaula.beforeFirst();
	        // Escriure les dades de cada fila
	        while (dadesTaula.next()) {
	            for (int i = 1; i <= numColumnas; i++) {
	                writer.write(dadesTaula.getString(i));
	                if (i < numColumnas)
	                    writer.write(";");
	            }
	            writer.newLine();
	        }

	        // Tancar l'arxiu
	        writer.close();
	        return true;
	    } catch (IOException | SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
