package cine.vista;

import java.time.LocalDate;
import java.util.ArrayList;

import cine.controlador.Controlador;
import cine.modelo.dao.GestorClientes;
import cine.modelo.dao.GestorEntradas;
import cine.modelo.dao.GestosCompras;
import cine.modelo.pojos.Cliente;
import cine.modelo.pojos.Compra;
import cine.modelo.pojos.Entrada;
import cine.modelo.pojos.Pelicula;
import cine.modelo.pojos.Sesion;

public class Menu {

	private static Cliente cliente = null;
	private static String mayusculas = ".*[A-Z].*";
	private static String minusculas = ".*[a-z].*";
	private static String numeros = ".*\\d.*";
	private static String gmail = "[a-z0-9._%+-]+@gmail\\.com";
	private static String dniMatches = "\\d{8}[A-Z]";

	private Controlador controlador = null;

	private ArrayList<Entrada> carro = null;

	public Menu() {

		controlador = new Controlador();
		carro = new ArrayList<Entrada>();
	}

	/**
	 * Da la bienvenida y espera a que el usuario de enter
	 */
	private void esperarEnter() {
		controlador.limpiarPantalla();
		mostrarMensajeBienvenida();
		controlador.leerDeTeclado("Pulsa ENTER para continuar...");
	}

	/**
	 * comprueba si sesion es null o no y te manda a donde sigue
	 * 
	 * @param pelicula
	 * @return Sesio o null
	 */
	private Sesion selecionSesio(Pelicula pelicula) {
		Sesion sesionSeleccionada = null;
		sesionSeleccionada = elegirSesionPelicula(pelicula);
		if (sesionSeleccionada != null) {
			System.out.println(pelicula);
		}
		return sesionSeleccionada;
	}

	/**
	 * Seleciona una pelicula y te manda a Sesion sino sale
	 * 
	 * @return Sesion o null
	 */
	private Sesion bucleSelecionPelicula() {
		Pelicula peliculaSeleccionada = null;
		Sesion sesionSelecionada = null;
		do {
			peliculaSeleccionada = mostrarSeleccionPelicula();
			if (peliculaSeleccionada != null) {
				sesionSelecionada = selecionSesio(peliculaSeleccionada);
			}
		} while (null == peliculaSeleccionada);
		return sesionSelecionada;
	}

	/**
	 * Bucle para escojer una accion
	 */
	public void escogerAccion() {
		int opcion = 0;
		do {
			mostrarMensajeMenuAcciones();
			opcion = controlador.pideNumero("Qué opción deseas");
			switchDeEscogerAcion(opcion);
		} while (opcion != 1);
	}

	/**
	 * Recoje opcion y te manda a la correspondiente
	 * 
	 * @param opcion
	 * @return "Opción no válida"
	 */
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

	/**
	 * Compueba que el cliete no este iniciado ( null ) Cliente != null ->
	 * {@return} Cliente Cleinte == null -> {@return} lo redidecion
	 */
	private void accionInicio() {
		if (cliente != null) {
			System.out.println("Cliente ya iniciado:" + cliente);
		} else
			cliente = buscarSIclienteExiste();
	}

	/**
	 * Compueba que el cliete no este iniciado ( null ) Cliente != null ->
	 * {@return} Cliente Cleinte == null -> {@return} lo redidecion
	 */
	private void accionRegistro() {
		if (cliente != null) {
			System.out.println("Cliente ya registrado:" + cliente);
		} else
			cliente = registrase();
	}

	/**
	 * Cnfirma que cliente este registrado antes de el pago y lo manda al mismo
	 */
	private void accionPago() {
		if (cliente != null) {
			pagar(cliente, carro);
			cliente = null;
		}
	}

	/**
	 * Metodo que Inicializa la plicacion. No finaliza almenos que lleve demasiado
	 * tiempo dando bueltas y es llamado por el launcher.
	 */
	public void inicio() {
		esperarEnter();
		do {
			Sesion sesionSelecionada = bucleSelecionPelicula();
			carro = crearEntradaTemp(sesionSelecionada, carro);
			escogerAccion();
		} while (true);
	}

	/**
	 * ES UN MENSAJE
	 */
	private void mostrarMensajeBienvenida() {
		System.out.println("");
		System.out.println("");
		System.out.println("BIENVENIDO A");
		System.out.println("CINELORRIETA");
		System.out.println("");
		System.out.println("");
	}

	/**
	 * ES UN MENSAJE
	 */
	private void mostrarMensajeMenuAcciones() {
		System.out.println("----Menu----");
		System.out.println("1 CONTINUAR");
		System.out.println("2 INICIAR");
		System.out.println("3 REGISTRAR");
		System.out.println("4 PAGAR");
	}

	// -----------------------------------------------------------------------------------------------------------------------

	// LOGIN

	/**
	 * El matches()método busca una cadena en busca de una coincidencia con una
	 * expresión regular y devuelve las coincidencias.
	 * 
	 * El método matches() busca una cadena en busca de una coincidencia con una
	 * expresión regular y devuelve las coincidencias.
	 */

	/**
	 * Metodo que valida el DNI, que tenga 9 caracteres, la letra correcta en el
	 * carcater 9 y 8 dijitos
	 * 
	 * @param dni
	 * @return true o folse
	 */
	private static boolean validarDNI(String dni) {
		if (dni == null || dni.length() != 9) {
			return false;
		}

		if (!dni.matches(dniMatches)) {
			return false;
		}

		String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		int numero = Integer.parseInt(dni.substring(0, 8));
		char letraCorrecta = letras.charAt(numero % 23);

		return dni.charAt(8) == letraCorrecta;
	}

	/**
	 * Le pide al usuario su DNI y lo manda a validar
	 * 
	 * @return DNI
	 */
	private String pedirDNI() {
		String ret;
		do {
			ret = controlador.leerDeTeclado("Introduce un DNI válido: ").toUpperCase();
			if (!validarDNI(ret)) {
				System.out.println("DNI incorrecto.");
			}
		} while (!validarDNI(ret));
		return ret;
	}

	/**
	 * Le pide al usuario su GMAIL y lo valida
	 * 
	 * @return Gmail
	 */
	private String pedirGmail() {
		String ret;
		do {
			ret = controlador.leerDeTeclado("Introduce un correo Gmail: ").toLowerCase();
		} while (!ret.matches(gmail));
		return ret;
	}

	/**
	 * Le pide al usuario su CONTRASEÑA y lo verifica que cumpla todas las
	 * condiciones
	 * 
	 * @return Contraseña
	 */
	private String pedirContraseña() {
		String ret;

		do {
			ret = controlador.leerDeTeclado("Introduce una contraseña: ");
			System.out.println(
					"ALERTA: la contraseña debe tener una MAYÚSCULA, una minúscula, un NÚMERO y 6 CARACTERES COMO MÍNIMO");
		} while (ret.length() < 6 || !ret.matches(mayusculas) || !ret.matches(minusculas) || !ret.matches(numeros));
		return ret;
	}

	/**
	 * registra al usuario para crear un nuevo cliente
	 * 
	 * @return cliente
	 */
	private Cliente registrase() {
		GestorClientes dBAcces = new GestorClientes();
		Cliente cliente = new Cliente();

		String dni = pedirDNI();
		cliente.setDni(dni);

		String nombre = controlador.leerDeTeclado("Nombre: ");
		cliente.setNombre(nombre);

		String apellido = controlador.leerDeTeclado("Apellido: ");
		cliente.setApellidos(apellido);

		String gmail = pedirGmail();
		cliente.setEmail(gmail);

		String pssword = pedirContraseña();
		cliente.setPassword(pssword);

		dBAcces.insertCliente(cliente);
		return cliente;
	}

	/**
	 * inicia un usuario exitente en la base de datos
	 * 
	 * @return Cliente= dni + password
	 */
	private String iniciar() {
		String dni = pedirDNI();
		String pssword = pedirContraseña();
		return "Cliente [dni=" + dni + ", password=" + pssword + "]";
	}

	/**
	 * Verifica si el cliente esta en la base de datos
	 * 
	 * @param clientes
	 * @return cliente
	 */
	private Cliente verificarCliente(ArrayList<Cliente> clientes) {
		String cliente = iniciar();
		Cliente ret = null;
		for (int i = 0; i < clientes.size(); i++) {
			if (clientes.get(i).toStringSimple().equals(cliente)) {
				System.out.println("BIENVENIDO DE VUELTA");
				ret = clientes.get(i);
			}
		}
		return ret;
	}

	/**
	 * Comprueba que el cliente no sea null y lo manda a verificar
	 * 
	 * @return Cliente
	 */
	private Cliente buscarSIclienteExiste() {
		Cliente ret = null;
		GestorClientes dBAcces = new GestorClientes();
		ArrayList<Cliente> clientes = dBAcces.getAllClientes();

		if (null == clientes) {
			System.out.println("No hay clientes");
		} else {
			ret = verificarCliente(clientes);
		}
		return ret;
	}

	// -----------------------------------------------------------------------------------------------------------------------

	// PELICULAS

	/**
	 * Muestra todas las peliculas que se encuentran en la BBDD
	 * 
	 * @param peliculas
	 */
	private void mostrarTodasPeliculas(ArrayList<Pelicula> peliculas) {
		if (peliculas == null || peliculas.isEmpty()) {
			System.out.println("No hay películas");
		} else {
			for (Pelicula pelicula : peliculas)
				System.out.println(pelicula.getIdPelicula() + "  " + pelicula.getNombre());
		}
	}

	/**
	 * Busca una pelicula atrabes de su identificador
	 * 
	 * @param peliculas
	 * @param id
	 * @return pelicula o null
	 */
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

	/**
	 * Deja selecionar una pelicula de la lista
	 * 
	 * @return pelicula
	 */
	private Pelicula mostrarSeleccionPelicula() {
		ArrayList<Pelicula> peliculas = controlador.cargarPeliculas();
		Pelicula pelicula = null;
		int id = 0;
		do {
			mostrarTodasPeliculas(peliculas);
			id = controlador.pideNumero("Pulse 0 para salir o elija una película");
			pelicula = getPeliculaById(peliculas, id);

			if (null == pelicula && id != 0) {
				System.out.println("La película no existe, inténtelo de nuevo.");
			}
		} while (id != 0 && pelicula == null);
		return pelicula;
	}

	// -----------------------------------------------------------------------------------------------------------------------

	// SESION

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
		ArrayList<Sesion> sesiones = controlador.cargarSesiones(idPelicula);
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
		ArrayList<Sesion> sesiones = controlador.cargarSesiones(pelicula.getIdPelicula());
		if (sesiones == null || sesiones.isEmpty())
			return null;
		int idSesion;
		Sesion sesionElegida = null;
		do {
			mostrarSesiones(pelicula);
			idSesion = controlador.pideNumero("Pulse 0 para terminar o elija una sesión");
			if (idSesion == 0)
				return null;
			sesionElegida = encontrarSesionPorId(sesiones, idSesion);
			if (sesionElegida != null)
				return sesionElegida;
			System.out.println("Sesión no encontrada, inténtelo de nuevo.");
		} while (true);
	}

	// -----------------------------------------------------------------------------------------------------------------------

	// COMPRRA Y ENTRADAS

	private double porcentajeDescuento(int numPeliculas) {
		double ret = 0;
		if (numPeliculas >= 3) {
			ret = 0.30;
		} else if (numPeliculas >= 2) {
			ret = 0.20;
		} else {
			ret = 0;
		}
		return ret;
	}

	private double descuentoAplicar(ArrayList<Entrada> carro) {
		double ret = 0;
		ArrayList<Integer> peliculasDistintas = new ArrayList<>();
		for (Entrada entrada : carro) {
			int idPeli = entrada.getSesion().getPeli().getIdPelicula();
			if (!peliculasDistintas.contains(idPeli)) {
				peliculasDistintas.add(idPeli);
			}
		}
		ret = porcentajeDescuento(peliculasDistintas.size());
		return ret;
	}

	private double actualizarPrecioEntradas(ArrayList<Entrada> carro) {
		double ret = 0;
		double descuento = descuentoAplicar(carro);
		for (int i = 0; i < carro.size(); i++) {
			Entrada entrada = carro.get(i);
			double descuentoPorEntrada = entrada.getPrecio() * descuento;
			entrada.setDescuento(descuentoPorEntrada);
			entrada.setPrecio(entrada.getPrecio() - descuentoPorEntrada);
			ret = ret + descuentoPorEntrada;
		}
		return ret;
	}

	private double precioCarro(ArrayList<Entrada> carro) {
		double total = 0;
		for (Entrada entrada : carro)
			total += entrada.getPrecio();
		return total;
	}

	private Compra compras(Cliente cliente, ArrayList<Entrada> carro) {
		GestosCompras gestor = new GestosCompras();
		Compra ret = new Compra();

		ret.setFechaHora(LocalDate.now());
		ret.setCli(cliente);

		double descuento = actualizarPrecioEntradas(carro);
		ret.setDescuento(descuento);
		double totalFinal = precioCarro(carro);
		ret.setPrecioTotal(totalFinal);

		gestor.insertCompra(ret);
		return ret;
	}

	private void entradas(Compra compra, ArrayList<Entrada> carro) {
		GestorEntradas dBAcces = new GestorEntradas();
		for (Entrada entrada : carro) {
			entrada.setCompra(compra); // asociar la compra a entrada
			dBAcces.insertarEntrada(entrada);
		}
	}

	private void pagar(Cliente cliente, ArrayList<Entrada> carro) {
		if (cliente != null) {
			Compra compra = compras(cliente, carro);
			entradas(compra, carro);
			carro.clear(); // Vaciar carro después del pago
			System.out.println("Compra realizada con éxito.");
			System.out.println(compra);
			System.out.println();
		} else {
			System.out.println("Recuerda que es necesario iniciar sesión antes de pagar");
		}
	}

	private ArrayList<Entrada> crearEntradaTemp(Sesion sesion, ArrayList<Entrada> carro) {
		int numPersonas = controlador.pideNumero("¿Para cuantos quieres la entrada?");
		double precioUna = sesion.getPrecio();

		Entrada entrada = new Entrada();
		entrada.setSesion(sesion);
		entrada.setNumPersonas(numPersonas);
		entrada.setPrecio(precioUna * numPersonas);

		carro.add(entrada);
		System.out.println("Entrada añadida al carrito");
		return carro;

	}

	// -----------------------------------------------------------------------------------------------------------------------

}
