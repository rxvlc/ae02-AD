package ae02;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import ae02.Vista.*;

public class Controlador {
	Login login;
	Vista vista;
	RegistreUsuari registreUsuari;
	Model model;
	ActionListener aLpulsacioBotoLogin;
	ActionListener aLpulsacioBotoTancaSessio;
	ActionListener aLpulsacioBotoNouUsuari;
	ActionListener aLcreacioUsuari;
	ActionListener aLseleccioImportarCsv;
	ActionListener aLexecucioConsulta;

	public Controlador(Login login, Vista vista, RegistreUsuari registreUsuari, Model model) {
		this.login = login;
		this.vista = vista;
		this.registreUsuari = registreUsuari;
		this.model = model;
		initEventHandlers();
	}

	public void initEventHandlers() {

		aLpulsacioBotoLogin = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nomUsuari = login.getTxtUsuari().getText();
				char[] passwordArray = login.getpFcontrasenya().getPassword();
				String passwordString = new String(passwordArray);
				if (model.existeixUsuari(nomUsuari, passwordString,true)) {
					Dades.nomUsuari = nomUsuari;
					Dades.hashContrasenyaUsuari = Util.cifraContrasenya(passwordString);

					login.getTxtUsuari().setText(null);
					login.getpFcontrasenya().setText(null);
					login.dispose();

					// Preguntar si va en clase dades el nom de usuari o en model gracies
					if (!Dades.nomUsuari.equals("admin")) {
						vista.getBtnNouUsuari().setVisible(false);
						vista.getBtnImportaCsv().setVisible(false);
						vista.getScrollPaneTxtConcatenacioXml().setVisible(false);
						vista.getTxtConsulta().setSize(new Dimension(965, 260));
					} else {
						// Restaurar al estado inicial cuando es "admin"
						vista.getBtnNouUsuari().setVisible(true); // Hacer visible el botón
						vista.getBtnImportaCsv().setVisible(true); // Hacer visible el botón
						vista.getScrollPaneTxtConcatenacioXml().setVisible(true); // Hacer visible el JScrollPane
						vista.getTxtConsulta().setSize(new Dimension(965, 265)); // Restaurar el tamaño original de																// TxtConsulta
					}
					
					 // Limpiar la tabla (si la estás utilizando)
				    DefaultTableModel tableModel = (DefaultTableModel) vista.getTableResultatConsulta().getModel();
				    tableModel.setRowCount(0); // Esto elimina todas las filas de la tabla
				    vista.getTxtConsulta().setText("");
			        vista.getTableResultatConsulta().getTableHeader().setVisible(false);
				    
					vista.setVisible(true);
					vista.setLocationRelativeTo(null);

				}
			}
		};

		login.getBtnLogin().addActionListener(aLpulsacioBotoLogin);

		aLpulsacioBotoTancaSessio = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				vista.dispose();
				login.setVisible(true);
				Dades.nomUsuari = null;
				Dades.hashContrasenyaUsuari = null;
			}
		};

		vista.getBtnTancaSesio().addActionListener(aLpulsacioBotoTancaSessio);

		aLpulsacioBotoNouUsuari = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				vista.setEnabled(false);
				registreUsuari.setVisible(true);
				registreUsuari.setLocationRelativeTo(null);
			}
		};

		registreUsuari.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				vista.setEnabled(true); // Per a reactivar el formulari main quan es tanque o es registre un usuari
			}
		});

		vista.getBtnNouUsuari().addActionListener(aLpulsacioBotoNouUsuari);

		aLcreacioUsuari = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nomUsuari = registreUsuari.getTxtNomUsuari().getText();
				char[] passwordArray = registreUsuari.getpFcontrasenya().getPassword();
				String passwordString = new String(passwordArray);
				if (model.existeixUsuari(nomUsuari, passwordString)) {
					JOptionPane.showMessageDialog(null, "El usuari " + nomUsuari + " ja existeix", "Error de registre",
							JOptionPane.WARNING_MESSAGE);
				} else {
					model.registraUsuari(nomUsuari, passwordString);
				}
			}
		};

		registreUsuari.getBtnRegistreUsuari().addActionListener(aLcreacioUsuari);

		aLseleccioImportarCsv = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Crear un JFileChooser
				JFileChooser fileChooser = new JFileChooser();

				// Establecer el filtro para archivos CSV
				fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));

				// Abrir el cuadro de diálogo de selección de archivos
				int result = fileChooser.showOpenDialog(null);

				// Comprobar si el usuario seleccionó un archivo
				if (result == JFileChooser.APPROVE_OPTION) {
					vista.getScrollPaneTxtConcatenacioXml().setVisible(true);
					vista.getTxtConcatenacioXml().setVisible(true);
					vista.getTableResultatConsulta().setVisible(false);
					
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
					vista.getTxtConcatenacioXml().setText("Carregant... Per favor espere.");
					model.importaCsv(selectedFile, "population");
					String concatenacio = model.concatenacioArxiusXml();
					vista.getTxtConcatenacioXml().setText(concatenacio);

				} else {
					System.out.println("No se seleccionó ningún archivo.");
				}
			}

		};

		vista.getBtnImportaCsv().addActionListener(aLseleccioImportarCsv);

		aLexecucioConsulta = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				vista.getScrollPaneTxtConcatenacioXml().setVisible(false);
				vista.getTableResultatConsulta().setVisible(true);
				
				// Limpiar el JTable antes de cargar nuevos resultados
		        DefaultTableModel tableModel = (DefaultTableModel) vista.getTableResultatConsulta().getModel();
		        tableModel.setRowCount(0); // Limpiar las filas anteriores
		        

		        vista.getTableResultatConsulta().getTableHeader().setVisible(false);
		        ResultSet resultSet = model.executarConsulta(vista.getTxtConsulta().getText());

		        // Verificar si hubo un error al ejecutar la consulta (resultSet es null)
		        if (resultSet == null) {
		            return;  // Detener la ejecución si no se obtuvo un ResultSet válido
		        }

		        try {
		            // Obtener la cantidad de columnas en el ResultSet
		            ResultSetMetaData metaData = resultSet.getMetaData();
		            int columnCount = metaData.getColumnCount();

		            // Añadir los nombres de las columnas
		            String[] columnNames = new String[columnCount];
		            for (int i = 1; i <= columnCount; i++) {
		                columnNames[i - 1] = metaData.getColumnName(i);
		            }

		            // Establecer los nombres de las columnas en el JTable
		            tableModel.setColumnIdentifiers(columnNames);

		            // Añadir las filas con los datos del ResultSet
		            while (resultSet.next()) {
		                Object[] row = new Object[columnCount];
		                for (int i = 1; i <= columnCount; i++) {
		                    row[i - 1] = resultSet.getObject(i);
		                }
		                tableModel.addRow(row);
		            }

			        vista.getTableResultatConsulta().getTableHeader().setVisible(true);

		        } catch (SQLException ex) {
		            // En caso de error, mostrar el mensaje de error
		            ex.printStackTrace();
		        }
			}
		};

		vista.getBtnExecutarConsulta().addActionListener(aLexecucioConsulta);

	}

}
