package ae02;

import ae02.Vista.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VistaLogin login = new VistaLogin();
		Vista vista = new Vista();
		VistaRegistre registreUsuari = new VistaRegistre();
		Model model = new Model();
		Controlador controlador = new Controlador(login, vista, registreUsuari, model);
	}

}
