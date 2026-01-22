package menu;

import bbdd_Pojos.Cliente;
import bbdd_Pojos.Pelicula;
import bbdd_Pojos.Sesion;
import utiles.Controladores;

public class Menu {
	private Controladores con;
	private MenuPeliculas pelis;
	private MenuSesiones ses;
	private MenuEntradasPago pago;

	public Menu() {
		con = new Controladores();
		pelis = new MenuPeliculas();
		ses = new MenuSesiones();
		pago = new MenuEntradasPago();
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
			pago.crearEntradatemp(sesion);

		} while (true);
	}

	/**
	 * Metodo que te da la opcion de registarte o logearte agora o antes de pagar.
	 */
	public void logearse() {
		Login log = new Login();
		int opcion = 0;
		bienbenida();
		Cliente cliente = null;
		do {
			loginInicio();
			opcion = con.pideNumero("Qué opción deseas");
			switch (opcion) {
			case 1:
				break;
			case 2:
				cliente = log.buscarSIclienteExiste();
				break;
			case 3:
				log.registrase();
				break;
			case 4:
				pago.pagar(cliente);
				break;
			default:
				System.out.println("Opción no válida");
			}
		} while (opcion != 1);
	}

	private void bienbenida() {
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