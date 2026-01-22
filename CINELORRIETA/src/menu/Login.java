package menu;

import java.util.ArrayList;

import gestores.GestorClientes;
import pojos.Cliente;
import utiles.Controladores;

public class Login {

	private Controladores con = null;

	private static String mayusculas = ".*[A-Z].*";
	private static String minusculas = ".*[a-z].*";
	private static String numeros = ".*\\d.*";
	
	public Login() {
		con = new Controladores();
	}

	/**
	 * El matches()método busca una cadena en busca de una coincidencia con una
	 * expresión regular y devuelve las coincidencias.
	 * 
	 * El método matches() busca una cadena en busca de una coincidencia con una
	 * expresión regular y devuelve las coincidencias.
	 */

	private String pedirGmail() {
		String ret;
		do {
			ret = con.leerDeTeclado("Introduce un correo Gmail: ").toLowerCase();
		} while (!ret.matches("[a-z0-9._%+-]+@gmail\\.com"));

		return ret;
	}

	private String pedirContraseña() {
		String ret;

		do {
			ret = con.leerDeTeclado("Introduce una contraseña: ");
			System.out.println(
					"ALERTA: la contraseña debe tener una MAYÚSCULA, una minúscula, un NÚMERO y 6 CARACTERES COMO MÍNIMO");
		} while (ret.length() < 6 || !ret.matches(mayusculas) || !ret.matches(minusculas) || !ret.matches(numeros));
		return ret;
	}

	private static boolean validarDNI(String dni) {
		if (dni == null || dni.length() != 9) {
			return false;
		}

		if (!dni.matches("\\d{8}[A-Z]")) {
			return false;
		}

		String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		int numero = Integer.parseInt(dni.substring(0, 8));
		char letraCorrecta = letras.charAt(numero % 23);

		return dni.charAt(8) == letraCorrecta;
	}

	private String pedirDNI() {
		String ret;
		do {
			ret = con.leerDeTeclado("Introduce un DNI válido: ").toUpperCase();
			if (!validarDNI(ret)) {
				System.out.println("DNI incorrecto.");
			}
		} while (!validarDNI(ret));

		return ret;
	}

	private String iniciar() {
		String dni = pedirDNI();
		String pssword = pedirContraseña();
		return "Cliente [dni=" + dni + ", password=" + pssword + "]";
	}

	public Cliente buscarSIclienteExiste() {
		String cliente = iniciar();
		Cliente ret = null;
		GestorClientes dBAcces = new GestorClientes();
		ArrayList<Cliente> clientes = dBAcces.getAllClientes();

		if (null == clientes) {
			System.out.println("No hay clientes");
		} else {
			for (int i = 0; i < clientes.size(); i++) {
				if (clientes.get(i).toStringSimple().equals(cliente)) {
					System.out.println("BIENVENIDO DE VUELTA");
					ret = clientes.get(i);
				}
			}
		}
		return ret;
	}

	public void registrase() {
		GestorClientes dBAcces = new GestorClientes();
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
		
		dBAcces.insertCliente(cliente);
	}

}
