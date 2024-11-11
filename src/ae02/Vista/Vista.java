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

	/**
	 * Create the frame.
	 */
	public Vista() {
		setTitle("Panel de control");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 630);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				scrollPaneTxtConcatenacioXml = new JScrollPane();
				scrollPaneTxtConcatenacioXml.setVisible(false);
				scrollPaneTxtConcatenacioXml.setBounds(10, 315, 850, 265);
				contentPane.add(scrollPaneTxtConcatenacioXml);
				
						txtConcatenacioXml = new JTextArea();
						txtConcatenacioXml.setEditable(false);
						scrollPaneTxtConcatenacioXml.setViewportView(txtConcatenacioXml);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 315, 848, 265);
		contentPane.add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false; // Todas las celdas serán no editables
		    }
		};
		
		tableResultatConsulta = new JTable(tableModel);
		tableResultatConsulta.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(tableResultatConsulta);
		
		btnTancaSesio = new JButton("Tancar Sesió");
		btnTancaSesio.setBackground(new Color(255, 0, 0));
		btnTancaSesio.setBounds(868, 11, 116, 23);
		contentPane.add(btnTancaSesio);

		btnNouUsuari = new JButton("Nou Usuari");
		btnNouUsuari.setBounds(868, 557, 116, 23);
		contentPane.add(btnNouUsuari);

		btnImportaCsv = new JButton("Importar Csv");
		btnImportaCsv.setBounds(868, 529, 116, 23);
		contentPane.add(btnImportaCsv);

		txtConsulta = new JTextArea();
		txtConsulta.setBounds(10, 40, 850, 260);
		contentPane.add(txtConsulta);

		JLabel lblTitolConsulta = new JLabel("Escriu Ací la consulta SQL:");
		lblTitolConsulta.setForeground(new Color(255, 255, 255));
		lblTitolConsulta.setBounds(10, 20, 237, 14);
		contentPane.add(lblTitolConsulta);

		btnExecutarConsulta = new JButton("Executar");
		btnExecutarConsulta.setBounds(742, 11, 116, 23);
		contentPane.add(btnExecutarConsulta);

		initComponents();
	}

	public void initComponents() {

	}

	public JButton getBtnTancaSesio() {
		return btnTancaSesio;
	}

	public void setBtnTancaSesio(JButton btnTancaSesio) {
		this.btnTancaSesio = btnTancaSesio;
	}

	public JButton getBtnNouUsuari() {
		return btnNouUsuari;
	}

	public JButton getBtnImportaCsv() {
		return btnImportaCsv;
	}

	public JTextArea getTxtConcatenacioXml() {
		return txtConcatenacioXml;
	}

	public void setTxtConcatenacioXml(JTextArea txtConcatenacioXml) {
		this.txtConcatenacioXml = txtConcatenacioXml;
	}

	public JScrollPane getScrollPaneTxtConcatenacioXml() {
		return scrollPaneTxtConcatenacioXml;
	}

	public JTextArea getTxtConsulta() {
		return txtConsulta;
	}

	public JButton getBtnExecutarConsulta() {
		return btnExecutarConsulta;
	}

	public JTable getTableResultatConsulta() {
		return tableResultatConsulta;
	}
}
