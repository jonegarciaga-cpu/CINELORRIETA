package menu;

import java.time.LocalDate;
import java.util.ArrayList;

import gestores.GestosCompras;
import pojos.Cliente;
import pojos.Compra;
import pojos.Entrada;
import pojos.Pelicula;
import pojos.Sesion;
import utiles.Controladores;

public class Menu {

	private Controladores con;
	private Peliculas pelis;
	private Sesiones ses;

	private ArrayList<Entrada> carro;

	public Menu() {
		pelis = new Peliculas();
		ses = new Sesiones();
		con = new Controladores();
		carro = new ArrayList<>();
	}

	/**
	 * Metodo que Inicializa la plicacion. No finaliza y es llamada por el launcher.
	 */
	public void inicio() {
		do {
			logearse();
			Pelicula pelicula = pelis.elegirPelicula();
			if (pelicula == null) {
				break;
			}
			Sesion sesion = ses.elegirSesionPelicula(pelicula);
			if (sesion == null) {
				break;
			}
			// entrada

		} while (true);
	}

	/**
	 * Metodo que te da la opcion de registarte o logearte agora o antes de pagar.
	 */
	public void logearse() {
		Login log = new Login();
		int opcion = 0;
		bienbenida();
		do {
			loginInicio();
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
			case 4:
				precioCarro(carro); // ns de donde saca carro ahora mismo
//				 compras(cliente, carro);
				break;
			default:
				System.out.println("Opción no válida");
			}
		} while (opcion != 1);
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

	// PRIVATE
	public Compra compras(Cliente cliente, ArrayList<Entrada> carro) {
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

// ------------------------------------------------------------------------------------------------

	public void bienbenida() {
		System.out.println("");
		System.out.println("");
		System.out.println("BIENVENIDO A");
		System.out.println("CINELORRIETA");
		System.out.println("");
		System.out.println("");
	}

	public void loginInicio() {
		System.out.println("----Menu----");
		System.out.println("1 SALIR o HACER LUEGO");
		System.out.println("2 INICIAR");
		System.out.println("3 REGISTRAR");
		System.out.println("4 PAGAR");

	}
}