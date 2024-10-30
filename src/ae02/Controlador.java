package ae02;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ae02.Vista.*;

public class Controlador {
	Login login;
	Vista vista;
	Model model;
	ActionListener aLonPressButton;

	public Controlador(Login login, Vista vista, Model model) {
		this.login = login;
		this.vista = vista;
		this.model = model;
		initEventHandlers();
	}

	public void initEventHandlers() {

		aLonPressButton = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nomUsuari = login.getTxtUsuari().getText();
				char[] passwordArray = login.getpFcontrasenya().getPassword();
				String passwordString = new String(passwordArray);
				if(model.existeixUsuari(nomUsuari,passwordString)) {
					login.dispose();
					vista.setVisible(true);
				}
			}
		};

		login.getBtnLogin().addActionListener(aLonPressButton);
	}
	
	

}
