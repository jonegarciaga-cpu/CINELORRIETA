package menu;

import java.time.LocalDate;
import java.util.ArrayList;

import gestores.GestorPeliculas;
import gestores.GestorSesiones;
import gestores.GestosCompras;
import pojos.Cliente;
import pojos.Compra;
import pojos.Entrada;
import pojos.Pelicula;
import pojos.Sesion;
import utiles.Controladores;

public class Menu {

	private Controladores con;
	private TextosMenu tex;
	private ArrayList<Entrada> carro;

	public Menu() {
		con = new Controladores();
		tex = new TextosMenu();
		carro = new ArrayList<>();
	}

	/**
	 * Metodo que Inicializa la plicacion. No finaliza y es llamada por el launcher.
	 */
	public void inicio() {
		do {
			logearse();
			Pelicula pelicula = elegirPelicula();
			if (pelicula == null)
				break;
			Sesion sesion = elegirSesionPelicula(pelicula);
			if (sesion == null)
				break;
			precioCarro(carro);
//			 compras(cliente, carro);
		} while (true);
	}

	/**
	 * Metodo que te da la opcion de registarte o logearte agora o antes de pagar.
	 */
	public void logearse() {
		Login log = new Login();
		int opcion = 0;
		tex.bienbenida();
		do {
			tex.loginInicio();
			opcion = con.pideNumero("Qué opción deseas");
			switch (opcion) {
			case 1:
				break;
			case 2:
				log.buscarSIclienteExiste();
				break;
			case 3:
				log.registrase();
				break;
			default:
				System.out.println("Opción no válida");
			}
		} while (opcion != 1);
	}

//------------------------------------------------------------------------------------------------

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
	public void verTodasPeliculas() {
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
	private Pelicula elegirPelicula() {
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

//------------------------------------------------------------------------------------------------

	private ArrayList<Sesion> cargarSesiones(int idPelicula) {
		GestorSesiones gSesiones = new GestorSesiones();
		return gSesiones.getAllSesiones(idPelicula);
	}

	private void mostrarSesiones(Pelicula pelicula) {
		System.out.println("Sesiones disponibles para " + pelicula.getNombre() + ":");
		verSesionesPelicula(pelicula.getIdPelicula());
	}

	private Sesion encontrarSesionPorId(ArrayList<Sesion> sesiones, int idBuscado) {
		for (Sesion sesion : sesiones)
			if (sesion.getIdSesion() == idBuscado)
				return sesion;
		return null;
	}

	private void verSesionesPelicula(int idPelicula) {
		ArrayList<Sesion> sesiones = cargarSesiones(idPelicula);
		if (sesiones == null || sesiones.isEmpty())
			System.out.println("No hay sesiones");
		else
			for (Sesion sesion : sesiones)
				System.out.println(sesion.toStringSimple());
	}

	private Sesion elegirSesionPelicula(Pelicula pelicula) {
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

//------------------------------------------------------------------------------------------------
	private double precioCarro(ArrayList<Entrada> carro) {
		double total = 0;
		for (Entrada entrada : carro)
			total += entrada.getPrecio();
		return total;
	}

	private double descuentoTotal(ArrayList<Entrada> carro) {
		ArrayList<Integer> peliculasDistintas = new ArrayList<>();
		int totalEntradas = 0;
		for (Entrada entrada : carro) {
			int idPeli = entrada.getSesion().getPeli().getIdPelicula();
			if (!peliculasDistintas.contains(idPeli))
				peliculasDistintas.add(idPeli);
			totalEntradas += entrada.getNumPersonas();
		}
		if (peliculasDistintas.size() >= 2)
			return 0.20;
		else if (totalEntradas >= 3)
			return 0.30;
		return 0;
	}

	private Compra compras(Cliente cliente, ArrayList<Entrada> carro) {
		GestosCompras gc = new GestosCompras();
		Compra ret = new Compra();
		ret.setFechaHora(LocalDate.now());
		ret.setCli(cliente);
		double totalCarrito = precioCarro(carro);
		double descuento = descuentoTotal(carro);
		for (Entrada entrada : carro) {
			double descuentoEntrada = entrada.getPrecio() * descuento;
			entrada.setDescuento(descuentoEntrada);
			entrada.setPrecio(entrada.getPrecio() - descuentoEntrada);
		}
		double totalFinal = totalCarrito * (1 - descuento);
		ret.setPrecioTotal(totalFinal);
		gc.insertCompra(ret);
		return ret;
	}
}