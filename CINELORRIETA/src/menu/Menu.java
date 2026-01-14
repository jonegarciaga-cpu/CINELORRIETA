package menu;

import java.util.ArrayList;

import gestores.BDclientes;
import gestores.DBpeliculas;
import pojos.Cliente;
import pojos.Pelicula;

public class Menu {

	private void verTodasPeliculas() {
		DBpeliculas dBAcces = new DBpeliculas();
		ArrayList<Pelicula> peliculas = dBAcces.getAllPeliculas();

		// Mostramos el resultado
		if (null == peliculas) {
			System.out.println("No hay Peliculas");
		} else {
			for (int i = 0; i < peliculas.size(); i++) {
				System.out.println(peliculas.get(i));
			}
		}
	}

	private void loging() {
		BDclientes dBAcces = new BDclientes();

		// Nuevo cliente a insertar...
		Cliente cliente = new Cliente();
		cliente.setDni("3333333A");
		cliente.setNombre("Andres");
		cliente.setApellidos("Dominguez");
		cliente.setEmail("jone@gamil");
		cliente.setPassword("sadad");

		dBAcces.insertEjemplo(cliente);
	}

}// FIN
