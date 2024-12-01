package ae02;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import ae02.Vista.*;

public class Controlador {
	VistaLogin login;
	Vista vista;
	VistaRegistre registreUsuari;
	Model model;
	ActionListener aLpulsacioBotoLogin;
	ActionListener aLpulsacioBotoTancaSessio;
	ActionListener aLpulsacioBotoNouUsuari;
	ActionListener aLcreacioUsuari;
	ActionListener aLseleccioImportarCsv;
	ActionListener aLexecucioConsulta;
	ActionListener aLexportarCsvConsulta;

	/**
	 * Constructor de la classe Controlador.
	 * 
	 * Inicialitza el controlador associant les diferents vistes i el model. També
	 * s'encarrega de configurar els gestors d'esdeveniments (event handlers)
	 * necessaris per a la interacció entre les vistes i el model.
	 * 
	 * @param login          La vista del formulari de login (VistaLogin).
	 * @param vista          La vista principal de l'aplicació (Vista).
	 * @param registreUsuari La vista del formulari de registre d'usuaris
	 *                       (VistaRegistre).
	 * @param model          El model que conté la lògica i dades de l'aplicació
	 *                       (Model).
	 */
	public Controlador(VistaLogin login, Vista vista, VistaRegistre registreUsuari, Model model) {
		this.login = login;
		this.vista = vista;
		this.registreUsuari = registreUsuari;
		this.model = model;
		initEventHandlers();
	}

	/**
	 * Inicialitza els gestors d'esdeveniments (event handlers) per a les accions de
	 * la interfície gràfica.
	 */
	public void initEventHandlers() {

		// Gestor d'esdeveniments per al botó de login
		aLpulsacioBotoLogin = new ActionListener() {

			/**
			 * Gestiona l'esdeveniment d'acció quan es prem el botó de login.
			 * 
			 * - Comprova si l'usuari existeix en la base de dades. - Si l'usuari és vàlid,
			 * inicialitza les dades globals i configura la vista segons si l'usuari té
			 * permisos d'administrador. - Si l'usuari no és vàlid, el login no continuarà.
			 * 
			 * @param e L'esdeveniment d'acció generat per l'usuari.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obté el nom d'usuari i la contrasenya des de la vista de login
				String nomUsuari = login.getTxtUsuari().getText();
				char[] passwordArray = login.getpFcontrasenya().getPassword();
				String passwordString = new String(passwordArray);

				// Comprova si l'usuari existeix
				if (model.existeixUsuari(nomUsuari, passwordString)) {
					// Actualitza les dades globals amb el nom i la contrasenya xifrada
					Dades.nomUsuari = nomUsuari;
					Dades.hashContrasenyaUsuari = Util.cifraContrasenya(passwordString);

					// Neteja els camps de text del formulari de login
					login.getTxtUsuari().setText(null);
					login.getpFcontrasenya().setText(null);
					login.dispose(); // Tanca la finestra de login

					// Configura la vista segons si l'usuari és administrador
					if (Dades.admin) {
						// Configuració per a l'usuari administrador
						vista.getBtnNouUsuari().setVisible(true);
						vista.getBtnImportaCsv().setVisible(true);
						vista.getScrollPaneTxtConcatenacioXml().setVisible(true);
						vista.getTxtConsulta().setSize(new Dimension(850, 265)); // Restaura la mida original del camp
					} else {
						// Configuració per a un usuari no administrador
						vista.getBtnNouUsuari().setVisible(false);
						vista.getBtnImportaCsv().setVisible(false);
						vista.getScrollPaneTxtConcatenacioXml().setVisible(false);
					}

					// Neteja la taula de resultats i altres elements de la vista
					DefaultTableModel tableModel = (DefaultTableModel) vista.getTableResultatConsulta().getModel();
					tableModel.setRowCount(0); // Elimina totes les files de la taula
					vista.getTxtConsulta().setText(""); // Neteja el camp de consulta
					vista.getTableResultatConsulta().getTableHeader().setVisible(false); // Oculta l'encapçalament de la
																							// taula

					// Fa visible la vista principal i la centra en pantalla
					vista.setVisible(true);
					vista.setLocationRelativeTo(null);
				}
			}
		};

		// Afegeix un gestor d'esdeveniments al botó de login
		login.getBtnLogin().addActionListener(aLpulsacioBotoLogin);

		/**
		 * Gestor d'esdeveniments per al botó de tancar sessió.
		 */
		aLpulsacioBotoTancaSessio = new ActionListener() {

			/**
			 * Gestiona l'acció quan l'usuari prem el botó de tancar sessió.
			 * 
			 * - Oculta la vista principal i mostra la finestra de login. - Reinicia les
			 * dades globals d'usuari i permisos. - Tanca la connexió a la base de dades.
			 * 
			 * @param e L'esdeveniment d'acció generat per l'usuari.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// Oculta la finestra principal
				vista.dispose();

				// Torna a mostrar la finestra de login
				login.setVisible(true);

				// Reinicia les dades globals de l'usuari
				Dades.nomUsuari = null;
				Dades.hashContrasenyaUsuari = null;
				Dades.admin = false;

				// Tanca la connexió a la base de dades
				Conexio.tancaConexio();
			}
		};

		// Afegeix un gestor d'esdfeveniments al botó de login
		vista.getBtnTancaSesio().addActionListener(aLpulsacioBotoTancaSessio);

		// Listener per al botó "Nou Usuari"
		aLpulsacioBotoNouUsuari = new ActionListener() {

			/**
			 * Aquest mètode s'executa quan l'usuari fa clic al botó "Nou Usuari". Quan es
			 * fa clic en aquest botó, la vista principal es desactiva per evitar
			 * interaccions mentre es mostra el formulari de registre d'usuari. Després, la
			 * finestra de registre es fa visible i es posiciona al centre de la pantalla
			 * per facilitar la interacció de l'usuari amb el formulari.
			 *
			 * @param e L'esdeveniment d'acció que es produeix quan l'usuari fa clic al botó
			 *          "Nou Usuari".
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// Quan es fa clic al botó "Nou Usuari", la vista principal (vista) es desactiva
				// per evitar interaccions mentre es mostra el formulari de registre.
				vista.setEnabled(false); // Desactiva la vista principal, bloqueando la interacció de l'usuari amb
											// altres components de la interfície.

				// Es mostra la finestra de registre d'usuari (registreUsuari).
				registreUsuari.setVisible(true); // Fa visible la finestra de registre d'usuari perquè l'usuari pugui
													// emplenar el formulari de registre.

				// Es posiciona la finestra de registre al centre de la pantalla.
				registreUsuari.setLocationRelativeTo(null); // Col·loca la finestra de registre al centre de la pantalla
															// per una millor experiència d'usuari.
			}
		};

		// Listener de finestra per al registre d'usuari
		registreUsuari.addWindowListener(new java.awt.event.WindowAdapter() {

		    /**
		     * Aquest mètode s'executa quan l'usuari tanca la finestra de registre d'usuari.
		     * Quan es tanca la finestra de registre, es reactiva la vista principal (vista)
		     * per permetre que l'usuari torni a interactuar amb la interfície principal de l'aplicació.
		     *
		     * @param windowEvent L'esdeveniment de tancament de la finestra.
		     */
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        // Quan es tanque la finestra de registre, es reactiva la vista principal
		        // (vista) per permetre que l'usuari torni a interactuar amb ella.
		        vista.setEnabled(true);  // Reactiva la vista principal perquè l'usuari pugui tornar a interactuar amb ella després de tancar la finestra de registre.
		    }
		});


		// Assignació del listener al botó "Nou Usuari" en la vista principal
		vista.getBtnNouUsuari().addActionListener(aLpulsacioBotoNouUsuari);

		// Listener per a la creació d'usuari
		aLcreacioUsuari = new ActionListener() {

		    /**
		     * Aquest mètode s'executa quan l'usuari fa clic al botó de registre d'usuari.
		     * Recupera les dades introduïdes pel nou usuari (nom d'usuari i contrasenya),
		     * comprova si les contrasenyes coincideixen i, si no existeix un usuari amb el mateix nom,
		     * registra el nou usuari al sistema.
		     *
		     * @param e L'esdeveniment de clic al botó de registre d'usuari.
		     */
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Quan l'usuari fa clic al botó de registre, es recuperen les dades introduïdes
		        // pel nou usuari: nom d'usuari i contrasenya.

		        // Obtenir el nom d'usuari introduït pel registre
		        String nomUsuari = registreUsuari.getTxtNomUsuari().getText();

		        // Obtenir la contrasenya introduïda com a array de caràcters
		        char[] passwordArray = registreUsuari.getpFcontrasenya().getPassword();
		        String passwordString = new String(passwordArray);

		        // Obtenir la contrasenya repetida
		        char[] passwordArrayRepetida = registreUsuari.getpFRepeteixContrasenya().getPassword();
		        String passwordStringRepetida = new String(passwordArrayRepetida);

		        // Comprovar si les dues contrasenyes coincideixen
		        if (passwordString.equals(passwordStringRepetida)) {

		            // Comprovar si ja existeix un usuari amb el mateix nom
		            if (model.existeixUsuariRegistre(nomUsuari)) {
		                // Si l'usuari ja existeix, es mostra un missatge d'error
		                JOptionPane.showMessageDialog(null, "L'usuari " + nomUsuari + " ja existeix",
		                        "Error de registre", JOptionPane.WARNING_MESSAGE);
		            } else {
		                // Si l'usuari no existeix, es registra el nou usuari
		                model.registraUsuari(nomUsuari, passwordString);

		                // Mostrar missatge d'èxit en la creació de l'usuari
		                JOptionPane.showMessageDialog(null, "Usuari creat amb èxit", "Creació exitosa",
		                        JOptionPane.INFORMATION_MESSAGE);

		                // Tancar la finestra de registre
		                registreUsuari.dispose();

		                // Reactivar la finestra principal i mostrar-la
		                vista.setEnabled(true);
		                vista.setVisible(true);
		                vista.setLocationRelativeTo(null);
		            }

		        } else {
		            // Si les contrasenyes no coincideixen, mostrar un missatge d'error
		            JOptionPane.showMessageDialog(null, "Les contrasenyes no coincideixen", "Error",
		                    JOptionPane.INFORMATION_MESSAGE);
		        }
		    }
		};

		// Vincula el listener al botó de registre d'usuari
		registreUsuari.getBtnRegistreUsuari().addActionListener(aLcreacioUsuari);


		aLseleccioImportarCsv = new ActionListener() {

		    /**
		     * Aquest mètode s'executa quan l'usuari fa clic al botó d'importació CSV.
		     * Obre un quadre de diàleg per seleccionar un arxiu CSV, realitza la importació
		     * de l'arxiu seleccionat i mostra el resultat a la vista.
		     *
		     * @param e L'esdeveniment de clic al botó d'importació CSV.
		     */
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Crear un JFileChooser per permetre a l'usuari seleccionar un arxiu
		        JFileChooser fileChooser = new JFileChooser();

		        // Establir el filtre per a arxius CSV, per tal que només es mostrin arxius amb extensió ".csv"
		        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arxius CSV", "csv"));

		        // Obrir el quadre de diàleg de selecció d'arxius i capturar el resultat
		        int result = fileChooser.showOpenDialog(null);

		        // Comprovar si l'usuari ha seleccionat un arxiu
		        if (result == JFileChooser.APPROVE_OPTION) {
		            // Si s'ha seleccionat un arxiu, fer visible l'àrea de text i el JScrollPane
		            // per mostrar la concatenació d'arxius XML
		            vista.getScrollPaneTxtConcatenacioXml().setVisible(true);
		            vista.getTxtConcatenacioXml().setVisible(true);
		            vista.getTableResultatConsulta().setVisible(false);  // Amagar la taula de resultats

		            // Obtenir l'arxiu seleccionat
		            File selectedFile = fileChooser.getSelectedFile();

		            // Mostrar un missatge de càrrega mentre es processa l'arxiu
		            vista.getTxtConcatenacioXml().setText("Carregant... Per favor espere.");

		            // Cridar al mètode que importa l'arxiu CSV i realitza l'operació corresponent
		            model.importaCsv(selectedFile, "population");

		            // Obtenir la concatenació dels arxius XML processats i mostrar-la a l'àrea de text
		            String concatenacio = model.concatenacioArxiusXml();
		            vista.getTxtConcatenacioXml().setText(concatenacio);

		            // Deshabilitar el botó d'exportació CSV una vegada l'arxiu ha estat importat
		            vista.getBtnExportarCSV().setEnabled(false);

		        } else {
		            // Si l'usuari no selecciona cap arxiu, es mostra un missatge no fem res
		        }
		    }

		};


		// Vincula el ActionListener al botón de importación CSV
		vista.getBtnImportaCsv().addActionListener(aLseleccioImportarCsv);

		aLexecucioConsulta = new ActionListener() {

		    /**
		     * Aquest mètode s'executa quan l'usuari fa clic al botó d'executar consulta.
		     * Realitza l'execució d'una consulta SQL, mostra els resultats en un JTable
		     * i habilita el botó d'exportació CSV si la consulta és exitosa.
		     *
		     * @param e L'esdeveniment de clic al botó d'executar consulta.
		     */
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Fer visible la taula de resultats i amagar l'àrea de text per a la concatenació de XML
		        vista.getScrollPaneTxtConcatenacioXml().setVisible(false);  // Amagar l'àrea de concatenació de XML
		        vista.getTableResultatConsulta().setVisible(true);          // Mostrar la taula de resultats

		        // Netejar el JTable abans de carregar els nous resultats
		        DefaultTableModel tableModel = (DefaultTableModel) vista.getTableResultatConsulta().getModel();
		        tableModel.setRowCount(0);  // Netejar les files anteriors de la taula

		        // Amagar l'encapçalament de la taula abans de mostrar els resultats
		        vista.getTableResultatConsulta().getTableHeader().setVisible(false);

		        // Executar la consulta i obtenir el ResultSet
		        ResultSet resultSet = model.executarConsulta(vista.getTxtConsulta().getText());

		        // Comprovar si hi ha hagut un error en executar la consulta (resultSet és null)
		        if (resultSet == null) {
		            return;  // Aturar l'execució si no s'ha obtingut un ResultSet vàlid
		        }
		        Dades.ultimaConsulta = resultSet;  // Desar el ResultSet per a un ús posterior

		        try {
		            // Obtenir la quantitat de columnes al ResultSet
		            ResultSetMetaData metaData = resultSet.getMetaData();
		            int columnCount = metaData.getColumnCount();

		            // Afegir els noms de les columnes a l'array
		            String[] columnNames = new String[columnCount];
		            for (int i = 1; i <= columnCount; i++) {
		                columnNames[i - 1] = metaData.getColumnName(i);  // Desar els noms de les columnes
		            }

		            // Establir els noms de les columnes a la taula JTable
		            tableModel.setColumnIdentifiers(columnNames);

		            // Afegir les files amb les dades del ResultSet
		            while (resultSet.next()) {
		                Object[] row = new Object[columnCount];
		                for (int i = 1; i <= columnCount; i++) {
		                    row[i - 1] = resultSet.getObject(i);  // Obtenir els valors de cada fila
		                }
		                tableModel.addRow(row);  // Afegir la fila a la taula
		            }

		            // Habilitar el botó per a exportar a CSV
		            vista.getBtnExportarCSV().setEnabled(true);

		            // Fer visible l'encapçalament de la taula una vegada s'han carregat les dades
		            vista.getTableResultatConsulta().getTableHeader().setVisible(true);

		        } catch (SQLException ex) {
		            // En cas d'error en processar el ResultSet, mostrar el missatge d'error
		            ex.printStackTrace();
		        }
		    }
		};

		// Vincula l'ActionListener al botó d'executar consulta
		vista.getBtnExecutarConsulta().addActionListener(aLexecucioConsulta);


		aLexportarCsvConsulta = new ActionListener() {

		    /**
		     * Este método se ejecuta cuando el usuario hace clic en el botón de exportación a CSV.
		     * Permite al usuario seleccionar un directorio en el que guardar el archivo CSV con los resultados
		     * de la última consulta ejecutada. Si no hay resultados disponibles, muestra un mensaje de error.
		     *
		     * @param e El evento de clic en el botón de exportación CSV.
		     */
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Verifiquem si hi ha una consulta recent que es pugui exportar
		        if (Dades.ultimaConsulta != null) {
		            // Mostrem un JFileChooser per seleccionar el directori
		            JFileChooser fileChooser = new JFileChooser();
		            fileChooser.setDialogTitle("Selecciona un directori per guardar l'arxiu CSV");
		            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Només permet la selecció de directoris
		            fileChooser.setAcceptAllFileFilterUsed(false); // No mostrar arxius de tots els tipus

		            // Mostrem el quadre de diàleg per seleccionar el directori
		            int seleccion = fileChooser.showSaveDialog(null);
		            if (seleccion == JFileChooser.APPROVE_OPTION) {
		                // Obtenim el directori seleccionat
		                File directorio = fileChooser.getSelectedFile();

		                // Comprovem si el directori és vàlid
		                if (directorio != null && directorio.exists()) {
		                    // Creem un arxiu CSV en el directori seleccionat
		                    String nombreArchivo = "consulta_exportada.csv";
		                    File arxiuCSV = new File(directorio, nombreArchivo);

		                    // Cridem al mètode exportaTablaACsv passant-li el ResultSet i l'arxiu
		                    model.exportaTablaACsv(Dades.ultimaConsulta, arxiuCSV);
		                    JOptionPane.showMessageDialog(null, "Arxiu CSV exportat correctament", "Èxit",
		                            JOptionPane.INFORMATION_MESSAGE);

		                } else {
		                    JOptionPane.showMessageDialog(null, "Directori no vàlid", "Error",
		                            JOptionPane.ERROR_MESSAGE);
		                }
		            }
		        } else {
		            // Si no hi ha dades per exportar
		            JOptionPane.showMessageDialog(null, "La consulta que has fet no ha donat resultats",
		                    "Error Exportació", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		};

		// Vinculem el ActionListener al botó d'exportar CSV
		vista.getBtnExportarCSV().addActionListener(aLexportarCsvConsulta);


	}

}
