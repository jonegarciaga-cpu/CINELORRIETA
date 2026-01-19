package menu;

import java.util.ArrayList;

import gestores.GestorEntradas;
import gestores.GestorPeliculas;
import gestores.GestorSesiones;
import pojos.Entrada;
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
			// Dentro de elegir pelicula llegas a elegir tambien la sesion de la palicula.
			eleguirPelicula();

		} while (true);
	}

	private ArrayList<Pelicula> cargarPeliculas() {
		GestorPeliculas dBAcces = new GestorPeliculas();
		ArrayList<Pelicula> peliculas = dBAcces.getAllPeliculas();
		return peliculas;
	}

	private ArrayList<Sesion> cargarSesiones(int pelicula) {
		GestorSesiones gSesiones = new GestorSesiones();
		ArrayList<Sesion> sesiones = gSesiones.getAllSesiones(pelicula);
		return sesiones;
	}

	public void verTodasPeliculas() {
		ArrayList<Pelicula> peliculas = cargarPeliculas();
		if (null == peliculas) {
			System.out.println("No hay Peliculas");
		} else {
			for (int i = 0; i < peliculas.size(); i++) {
				System.out.println(peliculas.get(i).getIdPelicula() + "  " + peliculas.get(i).getNombre());
			}
		}
	}

	private void eleguirPelicula() {
		ArrayList<Pelicula> peliculas = cargarPeliculas();
		int ret = 0;
		do {
			verTodasPeliculas();
			ret = con.pideNumero("Pulse 0 para terminar o eliga una Pelicula");
			if (ret > peliculas.size()) {
				System.out.println("No existe esa pelicula por favor compruevelo denuevo");
			} else {
				ret = encontrarPelicula(peliculas, ret);
			}
		} while (ret != 0);

	}

	private int encontrarPelicula(ArrayList<Pelicula> peliculas, int ret) {
		for (int i = 0; i < peliculas.size(); i++) {
			if (ret == peliculas.get(i).idPelicula) {
				ret = 0;
				elegirSesionPelicula(i);
			}
		}
		return ret;
	}

	private void verSesionesPelicula(int pelicula) {
		ArrayList<Sesion> sesion = cargarSesiones(pelicula);
		if (null == sesion) {
			System.out.println("No hay Peliculas");
		} else {
			for (int i = 0; i < sesion.size(); i++) {
				System.out.println(sesion.get(i).toStringSimple());
			}
		}
	}

	private Sesion elegirSesionPelicula(int pelicula) {
		ArrayList<Sesion> sesiones = cargarSesiones(pelicula);
		ArrayList<Pelicula> peliculas = cargarPeliculas();
		int sesion = 0;
		Sesion ret = null;
		do {
			System.out.println(peliculas.get(pelicula));
			verSesionesPelicula(pelicula);
			sesion = con.pideNumero("Pulse 0 para terminar o eliga una Sesion");
			ret = encontrarSesion(sesiones, sesion, ret);
			if (ret == null) {
				System.out.println("Sesion no encontrada");
			} else {
				sesion = 0;
			}
		} while (sesion != 0);
		return ret;
	}

	private Sesion encontrarSesion(ArrayList<Sesion> sesiones, int sesion, Sesion ret) {
		for (int i = 0; i < sesiones.size(); i++) {
			if (sesion == sesiones.get(i).idSesion) {
				ret = sesiones.get(i);
			}
		}
		return ret;
	}

	public void entrada(Sesion sesion) {
		GestorEntradas dBAcces = new GestorEntradas();
		Entrada entrada = new Entrada();
		int personas = con.pideNumero("Para cuantas personas quieres la entrada");
		entrada.setNumPersonas(personas);
		double precioTotal = personas * sesion.getPrecio();
		entrada.setPrecio(precioTotal);
		entrada.setSesion(sesion);
		dBAcces.insertarEntrada(entrada);

	}

//	private void printEntrada(Sesion sesion, Entrada entrada) {
//
//	}

}// FIN =P