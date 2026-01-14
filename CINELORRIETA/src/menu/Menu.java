package menu;

import java.util.ArrayList;

import gestores.DBpeliculas;
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

	

}// FIN
