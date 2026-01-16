package menu;

import java.util.ArrayList;

import gestores.DBpeliculas;
import gestores.GestorSesiones;
import pojos.Pelicula;
import pojos.Sesion;
import utiles.Controladores;

public class Menu {
	Controladores con = null;
	TextosMenu tex = null;
	ArrayList<String> carro = null;

	public Menu() {
		con = new Controladores();
		tex = new TextosMenu();
	}

	public void inicio() {
		do {
			tex.bienbenida();
			new Login().mostrar();
			/**
			 * Dentro de elegir pelicula llegas a elegir tambien la sesion de la palicul.
			 */
			eleguirPelicula();
		} while (true);
	}

	private ArrayList<Pelicula> peliculas() {
		DBpeliculas dBAcces = new DBpeliculas();
		ArrayList<Pelicula> peliculas = dBAcces.getAllPeliculas();
		return peliculas;
	}

	private ArrayList<Sesion> sesiones(int pelicula) {
		GestorSesiones gSesiones = new GestorSesiones();
		ArrayList<Sesion> sesiones = gSesiones.getAllSesiones(pelicula);
		return sesiones;
	}

	public void verTodasPeliculas() {
		ArrayList<Pelicula> peliculas = peliculas();
		if (null == peliculas) {
			System.out.println("No hay Peliculas");
		} else {
			for (int i = 0; i < peliculas.size(); i++) {
				System.out.println(peliculas.get(i).getIdPelicula() + "  " + peliculas.get(i).getNombre());
			}
		}
	}

	private void eleguirPelicula() {
		ArrayList<Pelicula> peliculas = peliculas();
		int cuantasPeliculas = peliculas.size();
		int ret = 0;
		do {
			verTodasPeliculas();
			ret = con.pideNumero("Pulse 0 para terminar o eliga una Pelicula");
			if (ret > cuantasPeliculas) {
				System.out.println("No existe esa pelicula por favor compruevelo denuevo");
			} else {
				for (int i = 0; i < cuantasPeliculas; i++) {
					if (ret == peliculas.get(i).idPelicula) {
						ret = 0;
						elegirSesionPelicula(i);
					}
				}
			}
		} while (ret != 0);

	}

	private void verSesionesPelicula(int pelicula) {
		ArrayList<Sesion> sesion = sesiones(pelicula);
		if (null == sesion) {
			System.out.println("No hay Peliculas");
		} else {
			for (int i = 0; i < sesion.size(); i++) {
				System.out.println(sesion.get(i).toStringSimple());
			}
		}
	}

	private Sesion elegirSesionPelicula(int pelicula) {
		ArrayList<Sesion> sesiones = sesiones(pelicula);
		ArrayList<Pelicula> peliculas = peliculas();
		int sesion = 0;
		Sesion ret = null;
		do {
			System.out.println(peliculas.get(pelicula));
			verSesionesPelicula(pelicula);
			sesion = con.pideNumero("Pulse 0 para terminar o eliga una Sesion");
			for (int i = 0; i < sesiones.size(); i++) {
				if (sesion == sesiones.get(i).idSesion) {
					ret = sesiones.get(i);
					sesion = 0;
				}
			}
		} while (sesion != 0);
		return ret;
	}

}// FIN
