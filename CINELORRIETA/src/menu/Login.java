package menu;

import gestores.BDclientes;
import pojos.Cliente;
import utiles.Controladores;

public class Login {

	Controladores con = new Controladores();

	public void loginInicio() {
		System.out.println("----Menu----");
		System.out.println("1 SALIR");
		System.out.println("2 INICIAR");
		System.out.println("3 REGISTRAR");
		System.out.println("4 MAS ADELANTE");
	}

	public void mostrar() {
		int opcion = 0;
		do {
			loginInicio();
			opcion = con.pideNumero("Que opcion deseas");
			switch (opcion) {
			case 1:
				break;
			case 2:

				break;
			case 3:
				registrase();
				break;
			default:
				System.out.println("Opcion no valido");
			}
		} while (opcion != 1);
		System.out.println("BYE");
	}

	private void registrase() {
		BDclientes dBAcces = new BDclientes();
		Cliente cliente = new Cliente();
		String dni = pedirDNI();
		cliente.setDni(dni);
		String nombre = con.leerDeTeclado("Nombre: ");
		cliente.setNombre(nombre);
		String apellido = con.leerDeTeclado("Apellido: ");
		cliente.setApellidos(apellido);
		String gmail = pedirGmail();
		cliente.setEmail(gmail);
		String pssword = pedirContraseña();
		cliente.setPassword(pssword);
		dBAcces.insertEjemplo(cliente);
	}

	private String pedirContraseña() {
		String ret = null;
		return ret;
	}

	private String pedirGmail() {
		String ret = null;
		return ret;
	}

	private String pedirDNI() {
		String ret = null;
		return ret;
	}

}
