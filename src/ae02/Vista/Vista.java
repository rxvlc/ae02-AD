package ae02.Vista;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ae02.Dades;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Classe que representa la vista del panell de control de l'aplicació.
 * Aquesta classe gestiona la interfície gràfica (GUI) de l'aplicació, incloent els botons,
 * taules i altres components per a la interacció de l'usuari.
 */
public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnTancaSesio;
	JButton btnNouUsuari;
	JButton btnImportaCsv;
	JTextArea txtConcatenacioXml;
	private JScrollPane scrollPaneTxtConcatenacioXml;
	private JTextArea txtConsulta;
	private JButton btnExecutarConsulta;
	private JTable tableResultatConsulta;
	private JScrollPane scrollPane;
	JButton btnExportarCSV;

	/**
	 * Constructor de la classe Vista.
	 * Aquest constructor crea la finestra principal del panell de control de l'aplicació
	 * amb tots els components de la interfície gràfica.
	 */
	public Vista() {
		// Configura les propietats de la finestra principal
		setTitle("Panel de control");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 630);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Crear i afegir el panell de desplaçament per a la concatenació XML
		scrollPaneTxtConcatenacioXml = new JScrollPane();
		scrollPaneTxtConcatenacioXml.setVisible(false);
		scrollPaneTxtConcatenacioXml.setBounds(10, 315, 850, 265);
		contentPane.add(scrollPaneTxtConcatenacioXml);

		txtConcatenacioXml = new JTextArea();
		txtConcatenacioXml.setEditable(false);
		scrollPaneTxtConcatenacioXml.setViewportView(txtConcatenacioXml);

		// Crear i afegir el panell de desplaçament per a la taula de resultats de consulta
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 315, 848, 265);
		contentPane.add(scrollPane);

		DefaultTableModel tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Totes les cel·les seran no editables
			}
		};

		tableResultatConsulta = new JTable(tableModel);
		tableResultatConsulta.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(tableResultatConsulta);

		// Crear i afegir el botó de tancament de sessió
		btnTancaSesio = new JButton("Tancar Sessió");
		btnTancaSesio.setBackground(new Color(255, 0, 0));
		btnTancaSesio.setBounds(868, 11, 116, 23);
		contentPane.add(btnTancaSesio);

		// Crear i afegir el botó de crear nou usuari
		btnNouUsuari = new JButton("Nou Usuari");
		btnNouUsuari.setBounds(868, 501, 116, 23);
		contentPane.add(btnNouUsuari);

		// Crear i afegir el botó per importar CSV
		btnImportaCsv = new JButton("Importar Csv");
		btnImportaCsv.setBounds(868, 529, 116, 23);
		contentPane.add(btnImportaCsv);

		// Crear i afegir el camp de text per a la consulta SQL
		txtConsulta = new JTextArea();
		txtConsulta.setBounds(10, 40, 850, 260);
		contentPane.add(txtConsulta);

		// Crear i afegir la etiqueta de títol per a la consulta SQL
		JLabel lblTitolConsulta = new JLabel("Escriu Ací la consulta SQL:");
		lblTitolConsulta.setForeground(new Color(255, 255, 255));
		lblTitolConsulta.setBounds(10, 20, 237, 14);
		contentPane.add(lblTitolConsulta);

		// Crear i afegir el botó per exportar a CSV
		btnExportarCSV = new JButton("Exportar CSV");
		btnExportarCSV.setEnabled(false);
		btnExportarCSV.setBounds(868, 557, 116, 23);
		contentPane.add(btnExportarCSV);

		// Crear i afegir el botó per executar la consulta SQL
		btnExecutarConsulta = new JButton("Executar");
		btnExecutarConsulta.setBounds(742, 11, 116, 23);
		contentPane.add(btnExecutarConsulta);

	}

	 
	// Mètodes per obtenir i establir els components de la vista

	/**
	 * Obté el botó per tancar la sessió.
	 * 
	 * @return El botó "Tancar Sessió"
	 */
	public JButton getBtnTancaSesio() {
	    return btnTancaSesio;
	}

	/**
	 * Estableix el botó per tancar la sessió.
	 * 
	 * @param btnTancaSesio El botó "Tancar Sessió"
	 */
	public void setBtnTancaSesio(JButton btnTancaSesio) {
	    this.btnTancaSesio = btnTancaSesio;
	}

	/**
	 * Obté el botó per crear un nou usuari.
	 * 
	 * @return El botó "Nou Usuari"
	 */
	public JButton getBtnNouUsuari() {
	    return btnNouUsuari;
	}

	/**
	 * Obté el botó per importar un fitxer CSV.
	 * 
	 * @return El botó "Importar Csv"
	 */
	public JButton getBtnImportaCsv() {
	    return btnImportaCsv;
	}

	/**
	 * Obté el text àrea per la concatenació de l'XML.
	 * 
	 * @return El text àrea per mostrar la concatenació de l'XML
	 */
	public JTextArea getTxtConcatenacioXml() {
	    return txtConcatenacioXml;
	}

	/**
	 * Estableix el text àrea per la concatenació de l'XML.
	 * 
	 * @param txtConcatenacioXml El text àrea per mostrar la concatenació de l'XML
	 */
	public void setTxtConcatenacioXml(JTextArea txtConcatenacioXml) {
	    this.txtConcatenacioXml = txtConcatenacioXml;
	}

	/**
	 * Obté el panell de desplaçament per la concatenació de l'XML.
	 * 
	 * @return El panell de desplaçament per la concatenació de l'XML
	 */
	public JScrollPane getScrollPaneTxtConcatenacioXml() {
	    return scrollPaneTxtConcatenacioXml;
	}

	/**
	 * Obté el text àrea per escriure la consulta SQL.
	 * 
	 * @return El text àrea per escriure la consulta SQL
	 */
	public JTextArea getTxtConsulta() {
	    return txtConsulta;
	}

	/**
	 * Obté el botó per executar la consulta.
	 * 
	 * @return El botó "Executar"
	 */
	public JButton getBtnExecutarConsulta() {
	    return btnExecutarConsulta;
	}

	/**
	 * Obté la taula per mostrar els resultats de la consulta.
	 * 
	 * @return La taula dels resultats de la consulta
	 */
	public JTable getTableResultatConsulta() {
	    return tableResultatConsulta;
	}

	/**
	 * Obté el botó per exportar els resultats a un fitxer CSV.
	 * 
	 * @return El botó "Exportar CSV"
	 */
	public JButton getBtnExportarCSV() {
	    return btnExportarCSV;
	}

}
