package menu;

import java.util.ArrayList;

import bbdd_Gestores.GestorPeliculas;
import bbdd_Pojos.Pelicula;
import utiles.Controladores;

public class MenuPeliculas {

	Controladores con = null;

	public MenuPeliculas() {
		con = new Controladores();
	}

	/**
	 * Metodo que carga Todas las peliculas de la base de datos.
	 * 
	 * @return Peliculas
	 */
	private ArrayList<Pelicula> cargarPeliculas() {
		GestorPeliculas dBAcces = new GestorPeliculas();
		return dBAcces.getAllPeliculas();
	}

	/**
	 * Metodo para leer todas las peliculas
	 */
	private void verTodasPeliculas() {
		ArrayList<Pelicula> peliculas = cargarPeliculas();
		if (peliculas == null || peliculas.isEmpty())
			System.out.println("No hay películas");
		else
			for (Pelicula p : peliculas)
				System.out.println(p.getIdPelicula() + "  " + p.getNombre());
	}

	/**
	 * Pide una pelicula que el usuario desee y la retorna
	 * 
	 * @return la pelicula que elige el usuario
	 */
	public Pelicula elegirPelicula() {
		ArrayList<Pelicula> peliculas = cargarPeliculas();
		int id;
		Pelicula peliElegida = null;
		do {
			verTodasPeliculas();
			id = con.pideNumero("Pulse 0 para salir o elija una película");
			if (id == 0)
				return null;
			peliElegida = encontrarPeliculaPorId(peliculas, id);
			if (peliElegida != null)
				return peliElegida;
			System.out.println("La película no existe, inténtelo de nuevo.");
		} while (true);
	}

	/**
	 * Busca la pelicula por su id
	 * 
	 * @param peliculas donde tengo que buscar
	 * @param idBuscado pelicula que quiere el usuario
	 * @return
	 */
	private Pelicula encontrarPeliculaPorId(ArrayList<Pelicula> peliculas, int idBuscado) {
		for (Pelicula ret : peliculas)
			if (ret.getIdPelicula() == idBuscado)
				return ret;
		return null;
	}
}
