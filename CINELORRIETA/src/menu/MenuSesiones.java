package menu;

import java.util.ArrayList;

import bbdd_Gestores.GestorSesiones;
import bbdd_Pojos.Pelicula;
import bbdd_Pojos.Sesion;
import utiles.Controladores;

public class MenuSesiones {
	Controladores con = null;

	public MenuSesiones() {
		con = new Controladores();
	}

	/**
	 * Metodo que carga las sesiones de una pelicula en concreto.
	 * 
	 * @param idPelicula de la pelicula
	 * @return las sesiones de la pelicula
	 */
	private ArrayList<Sesion> cargarSesiones(int idPelicula) {
		GestorSesiones gSesiones = new GestorSesiones();
		return gSesiones.getAllSesiones(idPelicula);
	}

	/**
	 * muestra la pelicula que emos elejido y llama al metodo que nos ensella todas
	 * las sesiones de la misma
	 * 
	 * @param pelicula
	 */
	private void mostrarSesiones(Pelicula pelicula) {
		System.out.println("Sesiones disponibles para " + pelicula.getNombre() + ":");
		verSesionesPelicula(pelicula.getIdPelicula());
	}

	/**
	 * encuntra una sesion por su id
	 * 
	 * @param sesiones  donde busca la sesion
	 * @param idBuscado el id de la sesion que qeremos buscar
	 * @return la sesion o null
	 */
	private Sesion encontrarSesionPorId(ArrayList<Sesion> sesiones, int idBuscado) {
		for (Sesion sesion : sesiones)
			if (sesion.getIdSesion() == idBuscado)
				return sesion;
		return null;
	}

	/**
	 * muestra todas las sesiones de una pelicula
	 * 
	 * @param idPelicula es el id de la pelicula de la cual queremis ver las
	 *                   sesiones
	 */
	private void verSesionesPelicula(int idPelicula) {
		ArrayList<Sesion> sesiones = cargarSesiones(idPelicula);
		if (sesiones == null || sesiones.isEmpty())
			System.out.println("No hay sesiones");
		else
			for (Sesion sesion : sesiones)
				System.out.println(sesion.toStringSimple());
	}

	/**
	 * metodo para elejir una sesion de una pelicula
	 * 
	 * @param pelicula de la cual queremos la sesion
	 * @return la sesion elejida y la pelicula a la que pertenece
	 */
	public Sesion elegirSesionPelicula(Pelicula pelicula) {
		ArrayList<Sesion> sesiones = cargarSesiones(pelicula.getIdPelicula());
		if (sesiones == null || sesiones.isEmpty())
			return null;
		int idSesion;
		Sesion sesionElegida = null;
		do {
			mostrarSesiones(pelicula);
			idSesion = con.pideNumero("Pulse 0 para terminar o elija una sesión");
			if (idSesion == 0)
				return null;
			sesionElegida = encontrarSesionPorId(sesiones, idSesion);
			if (sesionElegida != null)
				return sesionElegida;
			System.out.println("Sesión no encontrada, inténtelo de nuevo.");
		} while (true);
	}
}
