package cine.vista;

import java.util.ArrayList;

import cine.modelo.pojos.Cliente;
import cine.modelo.pojos.Entrada;
import cine.modelo.pojos.Pelicula;
import cine.modelo.pojos.Sesion;

public class Menu {
	private static Cliente cliente = null;
	private Menu_Login login = null;
	private Teclado teclado = null;
	private Menu_Pelis menu_Pelis = null;
	private Menu_Sesion menu_Sesion = null;
	private MenuEntradasPago menuEntradasPago = null;

	public Menu() {
		login = new Menu_Login();// no actualizado
		menuEntradasPago = new MenuEntradasPago();
		menu_Pelis = new Menu_Pelis();
		menu_Sesion = new Menu_Sesion();
		teclado = new Teclado();
	}

	/**
	 * Metodo que Inicializa la plicacion. No finaliza y es llamada por el launcher.
	 */
	public void inicio() {
		do {
			Pelicula peliculaSeleccionada = bucleSelecionPelicula();
			Sesion sesionSelecionada = bubleSelecionSesio(peliculaSeleccionada);
			ArrayList<Entrada> carro = menuEntradasPago.crearEntradaTemp(sesionSelecionada);
			escogerAccion();
		} while (true);
	}

	private Pelicula bucleSelecionPelicula() {
		Pelicula peliculaSeleccionada = null;
		do {
			mostrarMensajeBienvenida();
			peliculaSeleccionada = menu_Pelis.mostrarSeleccionPelicula();
		} while (null == peliculaSeleccionada);
		return peliculaSeleccionada;
	}

	private Sesion bubleSelecionSesio(Pelicula pelicula) {
		Sesion sesionSeleccionada = null;
		do {
			sesionSeleccionada = menu_Sesion.elegirSesionPelicula(pelicula);
		} while (null == sesionSeleccionada);
		return sesionSeleccionada;
	}

	public void escogerAccion() {

		int opcion = 0;

		do {
			mostrarMenuAcciones();
			opcion = teclado.pideNumero("Qué opción deseas");

			switch (opcion) {
			case 1: // Nothing to do here... Opcion Continuar
				break;
			case 2:
				accion2();
				break;
			case 3:
				accion3();
				break;
			case 4:// pago.pagar(cliente); dolo si cliente es diferebte a null
				break;
			default:
				System.out.println("Opción no válida");
			}
		} while (opcion != 1);
	}

	private void accion2() {
		if (cliente != null) {
			System.out.println("Cliente ya iniciado:" + cliente);
		} else
			cliente = login.buscarSIclienteExiste();
	}

	private void accion3() {
		if (cliente != null) {
			System.out.println("Cliente ya registrado:" + cliente);
		} else
			cliente = login.registrase();

	}

	private void mostrarMensajeBienvenida() {
		System.out.println("");
		System.out.println("");
		System.out.println("BIENVENIDO A");
		System.out.println("CINELORRIETA");
		System.out.println("");
		System.out.println("");
	}

	public void mostrarMenuAcciones() {
		System.out.println("----Menu----");
		System.out.println("1 CONTINUAR");
		System.out.println("2 INICIAR");
		System.out.println("3 REGISTRAR");
		System.out.println("4 PAGAR");
	}
}
