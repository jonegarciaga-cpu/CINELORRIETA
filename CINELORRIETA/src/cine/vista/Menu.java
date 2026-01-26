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

	private ArrayList<Entrada> carro = null;

	public Menu() {
		login = new Menu_Login();// no actualizado
		menuEntradasPago = new MenuEntradasPago();
		menu_Pelis = new Menu_Pelis();
		menu_Sesion = new Menu_Sesion();
		teclado = new Teclado();
		carro = new ArrayList<Entrada>();
	}

	/**
	 * Metodo que Inicializa la plicacion. No finaliza y es llamada por el launcher.
	 */
	public void inicio() {
		int muchasVueltaYaVale = 0;
		esperarEnter();
		do {
			muchasVueltaYaVale++;
			Sesion sesionSelecionada = bucleSelecionPelicula();
			carro = menuEntradasPago.crearEntradaTemp(sesionSelecionada, carro);
			escogerAccion();

		} while (muchasVueltaYaVale <= 20);
	}

	private Sesion bucleSelecionPelicula() {
		Pelicula peliculaSeleccionada = null;
		Sesion sesionSelecionada = null;
		do {
			peliculaSeleccionada = menu_Pelis.mostrarSeleccionPelicula();
			if (peliculaSeleccionada != null) {
				sesionSelecionada = bubleSelecionSesio(peliculaSeleccionada);
			}
		} while (null == peliculaSeleccionada);
		return sesionSelecionada;
	}

	private Sesion bubleSelecionSesio(Pelicula pelicula) {
		Sesion sesionSeleccionada = null;
		sesionSeleccionada = menu_Sesion.elegirSesionPelicula(pelicula);
		if (sesionSeleccionada != null) {
			System.out.println(pelicula);

		}
		return sesionSeleccionada;
	}

	private void esperarEnter() {
		teclado.limpiarPantalla();
		mostrarMensajeBienvenida();
		teclado.leerDeTeclado("Pulsa ENTER para continuar...");

	}

	public void escogerAccion() {

		int opcion = 0;

		do {
			mostrarMenuAcciones();
			opcion = teclado.pideNumero("Qué opción deseas");
			switchDeEscogerAcion(opcion);
		} while (opcion != 1);

	}

	private void switchDeEscogerAcion(int opcion) {
		switch (opcion) {
		case 1: // Nothing to do here... Opcion Continuar
			break;
		case 2:
			accionInicio();
			break;
		case 3:
			accionRegistro();
			break;
		case 4:
			accionPago();
			break;
		default:
			System.out.println("Opción no válida");
		}
	}

	private void accionInicio() {
		if (cliente != null) {
			System.out.println("Cliente ya iniciado:" + cliente);
		} else
			cliente = login.buscarSIclienteExiste();
	}

	private void accionRegistro() {
		if (cliente != null) {
			System.out.println("Cliente ya registrado:" + cliente);
		} else
			cliente = login.registrase();
	}

	private void accionPago() {
		if (cliente != null) {
			menuEntradasPago.pagar(cliente, carro);
			cliente = null;
		}
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
