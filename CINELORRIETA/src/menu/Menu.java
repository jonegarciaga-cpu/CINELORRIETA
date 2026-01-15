package menu;

import java.util.ArrayList;

import gestores.DBpeliculas;
import pojos.Pelicula;
import utiles.Controladores;

public class Menu {

	Controladores con = new Controladores();
	TextosMenu tex = new TextosMenu();

	public void inicio() {
		tex.bienbenida();
		boolean logeado = new Login().mostrar();
		eleguirPelicula();
	}

	private ArrayList<Pelicula> peliculas() {
		DBpeliculas dBAcces = new DBpeliculas();
		ArrayList<Pelicula> peliculas = dBAcces.getAllPeliculas();
		return peliculas;
	}

	private void eleguirPelicula() {
		ArrayList<Pelicula> peliculas = peliculas();
		int cuantasPeliculas = peliculas.size();
		int ret = 0;
		do {
			verTodasPeliculas();
			ret = con.pideNumero("Pulse 0 para terminar la operacio o eliga una Pelicula");
			if (ret > cuantasPeliculas) {
				System.out.println("No existe esa pelicula por favor compruevelo denuevo");
			} else if (ret == cuantasPeliculas) {
				fechasPelicula();
			}
		} while (ret != 0);
	}

	private void fechasPelicula() {
		// TODO Auto-generated method stub

	}

	public void verTodasPeliculas() {
		ArrayList<Pelicula> peliculas = peliculas();
		if (null == peliculas) {
			System.out.println("No hay Peliculas");
		} else {
			for (int i = 0; i < peliculas.size(); i++) {
				// ordenarlas por fecha de sesion
				System.out.println(peliculas.get(i).getIdPelicula() + "  " + peliculas.get(i).getNombre());
			}
		}
	}

}// FIN
