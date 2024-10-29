package ae02;

import ae02.Vista.*;

public class Controlador {
	Login vista;
	Vista login;
	Model model;
	
	
	public Controlador(Vista login,Login vista, Model model) {
		this.login = login;
		this.vista = vista;
		this.model = model;
	}
	
	
}
