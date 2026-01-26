package cine.controlador;

import java.util.ArrayList;

import cine.modelo.dao.GestorPeliculas;
import cine.modelo.dao.GestorSesiones;
import cine.modelo.pojos.Pelicula;
import cine.modelo.pojos.Sesion;

public class Controlador {

	/**
	 * Metodo que carga Todas las peliculas de la base de datos.
	 * 
	 * @return Peliculas
	 */
	public ArrayList<Pelicula> cargarPeliculas() {
		GestorPeliculas dBAcces = new GestorPeliculas();
		return dBAcces.getAllPeliculas();
	}

	/**
	 * Metodo que carga las sesiones de una pelicula en concreto.
	 * 
	 * @param idPelicula de la pelicula
	 * @return las sesiones de la pelicula
	 */
	public ArrayList<Sesion> cargarSesiones(int idPelicula) {
		GestorSesiones gSesiones = new GestorSesiones();
		return gSesiones.getAllSesiones(idPelicula);
	}
}
