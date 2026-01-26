package cine.vista;

import java.util.ArrayList;

import cine.controlador.Controlador;
import cine.modelo.pojos.Pelicula;

public class Menu_Pelis {

	private Controlador controlador = null;
	private Teclado teclado = null;

	public Menu_Pelis() {
		controlador = new Controlador();
		teclado = new Teclado();
	}

	public Pelicula mostrarSeleccionPelicula() {
		ArrayList<Pelicula> peliculas = controlador.cargarPeliculas();
		Pelicula pelicula = null;
		int id = 0;
		do {
			mostrarTodasPeliculas(peliculas);
			id = teclado.pideNumero("Pulse 0 para salir o elija una película");
			pelicula = getPeliculaById(peliculas, id);

			if (null == pelicula && id != 0) {
				System.out.println("La película no existe, inténtelo de nuevo.");
			}
		} while (id != 0 && pelicula == null);
		return pelicula;
	}

	private void mostrarTodasPeliculas(ArrayList<Pelicula> peliculas) {
		if (peliculas == null || peliculas.isEmpty()) {
			System.out.println("No hay películas");
		} else {
			for (Pelicula pelicula : peliculas)
				System.out.println(pelicula.getIdPelicula() + "  " + pelicula.getNombre());
		}
	}

	private Pelicula getPeliculaById(ArrayList<Pelicula> peliculas, int id) {
		Pelicula ret = null;
		for (Pelicula pelicula : peliculas) {
			if (pelicula.getIdPelicula() == id) {
				ret = pelicula;
				break;
			}
		}
		return ret;
	}
}
