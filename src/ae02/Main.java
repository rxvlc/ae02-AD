package ae02;

import ae02.Vista.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Login login = new Login();
		Vista vista = new Vista();
		Model model = new Model();
		Controlador controlador = new Controlador(vista,login, model);
	}

}
