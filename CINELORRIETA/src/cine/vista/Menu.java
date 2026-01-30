package cine.vista;

import java.time.LocalDate;
import java.util.ArrayList;

import cine.controlador.Controlador;
import cine.modelo.dao.GestorClientes;
import cine.modelo.dao.GestorEntradas;
import cine.modelo.dao.GestorFichero;
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
	private GestorFichero gestorFichero = null;
	private ArrayList<Entrada> carro = null;

	public Menu() {

		controlador = new Controlador();
		carro = new ArrayList<Entrada>();
		gestorFichero = new GestorFichero();
	}

	/**
	 * Da la bienvenida y espera a que el usuario dé enter
	 */
	private void esperarEnter() {
		mostrarMensajeBienvenida();
		controlador.leerDeTeclado("Pulsa ENTER para continuar...");
	}

	/**
	 * Comprueba si sesión es null o no y te manda a donde sigue
	 * 
	 * @param pelicula
	 * @return Sesion o null
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
	 * Selecciona una película y te manda a Sesion sino sale
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
			sesionSelecionada.setPeli(peliculaSeleccionada);
		} while (null == peliculaSeleccionada);
		return sesionSelecionada;
	}

	/**
	 * Bucle para escoger una acción
	 */
	public void escogerAccion() {
		int opcion = 0;
		do {
			controlador.limpiarPantalla();
			mostrarMensajeMenuAcciones();
			opcion = controlador.pideNumero("Qué opción deseas");
			switchDeEscogerAcion(opcion);
		} while (opcion != 1);
	}

	/**
	 * Recoge opción y te manda a la correspondiente
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
	 * Comprueba que el cliente no esté iniciado (null) Cliente != null ->
	 * {@return} Cliente Cliente == null -> {@return} lo redirecciona
	 */
	private void accionInicio() {
		if (cliente != null) {
			System.out.println("Cliente ya iniciado:" + cliente);
		} else
			cliente = buscarSIclienteExiste();
	}

	/**
	 * Comprueba que el cliente no esté iniciado (null) Cliente != null ->
	 * {@return} Cliente Cliente == null -> {@return} lo redirecciona
	 */
	private void accionRegistro() {
		if (cliente != null) {
			System.out.println("Cliente ya registrado:" + cliente);
		} else
			cliente = registrase();
	}

	/**
	 * Confirma que cliente esté registrado antes del pago y lo manda al mismo
	 */
	private void accionPago() {
		if (cliente != null) {
			pagar(cliente, carro);
			cliente = null;
		}
	}

	/**
	 * Método que inicializa la aplicación. No finaliza al menos que lleve demasiado
	 * tiempo dando vueltas y es llamado por el launcher.
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
	 * El matches() método busca una cadena en busca de una coincidencia con una
	 * expresión regular y devuelve las coincidencias.
	 * 
	 * El método matches() busca una cadena en busca de una coincidencia con una
	 * expresión regular y devuelve las coincidencias.
	 */

	/**
	 * Método que valida el DNI, que tenga 9 caracteres, la letra correcta en el
	 * caracter 9 y 8 dígitos
	 * 
	 * @param dni
	 * @return true o false
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
	 * Registra al usuario para crear un nuevo cliente
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
	 * Inicia un usuario existente en la base de datos
	 * 
	 * @return Cliente= dni + password
	 */
	private String iniciar() {
		String dni = pedirDNI();
		String pssword = pedirContraseña();
		return "Cliente [dni=" + dni + ", password=" + pssword + "]";
	}

	/**
	 * Verifica si el cliente está en la base de datos
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

	// PELÍCULAS

	/**
	 * Muestra todas las películas que se encuentran en la BBDD
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
	 * Busca una película a través de su identificador
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
	 * Deja seleccionar una película de la lista
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

	// SESIÓN

	/**
	 * Muestra la película que hemos elegido y llama al método que nos enseña todas
	 * las sesiones de la misma
	 * 
	 * @param pelicula
	 */
	private void mostrarSesiones(Pelicula pelicula) {
		System.out.println("Sesiones disponibles para " + pelicula.getNombre() + ":");
		verSesionesPelicula(pelicula.getIdPelicula());
	}

	/**
	 * Encuentra una sesión por su id
	 * 
	 * @param sesiones  donde busca la sesión
	 * @param idBuscado el id de la sesión que queremos buscar
	 * @return la sesión o null
	 */
	private Sesion encontrarSesionPorId(ArrayList<Sesion> sesiones, int idBuscado) {
		for (Sesion sesion : sesiones)
			if (sesion.getIdSesion() == idBuscado)
				return sesion;
		return null;
	}

	/**
	 * Muestra todas las sesiones de una película
	 * 
	 * @param idPelicula es el id de la película de la cual queremos ver las
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
	 * Método para elegir una sesión de una película
	 * 
	 * @param pelicula de la cual queremos la sesión
	 * @return la sesión elegida y la película a la que pertenece
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

	// MÉTODOS DE DESCUENTO

	/**
	 * Calcula qué porcentaje de descuento debe llevar la/s entradas/Compra
	 *
	 * @param numPeliculas número de películas distintas
	 * @return 0.30, 0.20 o 0 según la cantidad
	 */
	private double porcentajeDescuento(int numPeliculas) {
		if (numPeliculas >= 3)
			return 0.30;
		if (numPeliculas >= 2)
			return 0.20;
		return 0;
	}

	/**
	 * Calcula cuántas películas distintas hay en el carro y devuelve el descuento
	 * correspondiente
	 *
	 * @param carro Lista de entradas
	 * @return porcentaje de descuento a aplicar
	 */
	private double descuentoAplicar(ArrayList<Entrada> carro) {
		ArrayList<Integer> peliculasDistintas = new ArrayList<>();
		for (Entrada entrada : carro) {
			int idPeli = entrada.getSesion().getPeli().getIdPelicula();
			if (!peliculasDistintas.contains(idPeli)) {
				peliculasDistintas.add(idPeli);
			}
		}
		return porcentajeDescuento(peliculasDistintas.size());
	}

	/**
	 * Recorre el carro y actualiza el precio y descuento de las entradas
	 *
	 * @param carro Lista de entradas
	 * @return total del descuento aplicado
	 */
	private double actualizarPrecioEntradas(ArrayList<Entrada> carro) {
		double totalDescuento = 0;
		double descuento = descuentoAplicar(carro);

		for (Entrada entrada : carro) {
			double descuentoPorEntrada = entrada.getPrecio() * descuento;
			entrada.setDescuento(descuentoPorEntrada);
			entrada.setPrecio(entrada.getPrecio() - descuentoPorEntrada);
			totalDescuento += descuentoPorEntrada;
		}
		return totalDescuento;
	}

	/**
	 * Calcula el precio total del carro
	 *
	 * @param carro Lista de entradas
	 * @return total final
	 */
	private double precioCarro(ArrayList<Entrada> carro) {
		double total = 0;
		for (Entrada entrada : carro) {
			total += entrada.getPrecio();
		}
		return total;
	}

	// -----------------------------------------------------------------------------------------------------------------------

	// MÉTODOS DE COMPRA Y DE ENTRADAS

	/**
	 * Inserta los atributos de una compra
	 *
	 * @param cliente Cliente que realiza la compra
	 * @param carro   Lista de entradas
	 * @return Compra creada
	 */
	private Compra compras(Cliente cliente, ArrayList<Entrada> carro) {
		GestosCompras gestor = new GestosCompras();
		Compra compra = new Compra();

		compra.setFechaHora(LocalDate.now());
		compra.setCli(cliente);

		double descuento = actualizarPrecioEntradas(carro);
		compra.setDescuento(descuento);

		double totalFinal = precioCarro(carro);
		compra.setPrecioTotal(totalFinal);

		gestor.insertCompra(compra);
		return compra;
	}

	/**
	 * Recorre el carro final y le añade la compra correspondiente para luego
	 * insertar las entradas en la base de datos
	 *
	 * @param compra Compra asociada
	 * @param carro  Lista de entradas
	 */
	private void entradas(Compra compra, ArrayList<Entrada> carro) {
		GestorEntradas dBAcces = new GestorEntradas();
		for (Entrada entrada : carro) {
			entrada.setCompra(compra); // asociar la compra a la entrada
			dBAcces.insertarEntrada(entrada);
		}
	}

	/**
	 * Crea una entrada temporal y la añade al carro
	 *
	 * @param sesion Sesión de cine
	 * @param carro  Lista de entradas
	 * @return carro actualizado
	 */
	private ArrayList<Entrada> crearEntradaTemp(Sesion sesion, ArrayList<Entrada> carro) {
		int numPersonas = controlador.pideNumero("¿Para cuántos quieres la entrada?");
		double precioUna = sesion.getPrecio();

		Entrada entrada = new Entrada();
		entrada.setSesion(sesion);
		entrada.setNumPersonas(numPersonas);
		entrada.setPrecio(precioUna * numPersonas);

		carro.add(entrada);
		System.out.println("Entrada añadida al carrito");
		return carro;
	}

	/**
	 * Comprueba que se pague y envía a la opción de ticket
	 *
	 * @param cliente Cliente que realiza el pago
	 * @param carro   Lista de entradas
	 */
	private void pagar(Cliente cliente, ArrayList<Entrada> carro) {
		if (cliente != null) {
			Compra compra = compras(cliente, carro);
			entradas(compra, carro);
			preguntarSiTiket(compra, carro);
			carro.clear(); // Vaciar carro después del pago
		} else {
			System.out.println("Recuerda que es necesario iniciar sesión antes de pagar");
		}
	}

	/**
	 * pregunta si se desea un tiket y te lo imprime tanto en fichero como en
	 * pantalla
	 * 
	 * @param compra
	 * @param carro
	 */
	private void preguntarSiTiket(Compra compra, ArrayList<Entrada> carro) {
		boolean quiereTiket = controlador.pideBooleano("Deseas tiket");
		if (quiereTiket != false) {
			tiket(compra, carro);
		}
		tiketDigital(compra, carro);
	}

	// -----------------------------------------------------------------------------------------------------------------------

	// FICHERO--TIKET

	/**
	 * Imprime el ticket de la compra
	 *
	 * @param compra  Compra realizada
	 * @param carro   Lista de entradas
	 * @param cliente Cliente que realiza la compra
	 */
	private void tiket(Compra compra, ArrayList<Entrada> carro) {
		System.out.println("Compra realizada con éxito.");
		String texto = crearTexto(compra, carro);
		escribirTiket(texto, compra);
	}

	/**
	 * Genera un tiket
	 * 
	 * @param compra
	 * @param carro
	 */
	private void tiketDigital(Compra compra, ArrayList<Entrada> carro) {
		System.out.println("Compra realizada con éxito.");
		String texto = crearTexto(compra, carro);
		System.out.println(texto);
	}

	/**
	 * genera un String desde varios sitios diferentes
	 * 
	 * @param compra
	 * @param carro
	 * @return STRING
	 */
	private String crearTexto(Compra compra, ArrayList<Entrada> carro) {
		StringBuilder texto = new StringBuilder();
		texto.append(textoCompra(compra).toString()).append(textoCliente(compra).toString())
				.append(textoEntradas(carro).toString());
		return texto.toString();
	}

	/**
	 * Te genera el texto de la entrada
	 * 
	 * @param carro
	 * @return String entrada o varios String entrada
	 */
	private StringBuilder textoEntradas(ArrayList<Entrada> carro) {
		StringBuilder ret = new StringBuilder();
		for (Entrada entrada : carro) {
			ret.append(entrada.toStringTicket()).append("\n");
		}
		return ret;
	}

	/**
	 * Te genera el texto de la compra
	 * 
	 * @param compra
	 * @return String compra
	 */
	private StringBuilder textoCompra(Compra compra) {
		StringBuilder ret = new StringBuilder();
		ret.append("COMPRA:\n");
		ret.append("Fecha: ").append(compra.getFechaHora()).append("\n");
		ret.append("Total compra: ").append(compra.getPrecioTotal()).append("€\n");
		ret.append("===========================\n");
		return ret;
	}

	/**
	 * Te genera el texto de el cliente
	 * 
	 * @param compra
	 * @return String Cliente
	 */
	private StringBuilder textoCliente(Compra compra) {
		StringBuilder ret = new StringBuilder();
		ret.append("CLIENTE:\n");
		ret.append("DNI: ").append(compra.getCli().getDni()).append("\n");
		ret.append("Nombre: ").append(compra.getCli().getNombre()).append(" ").append(compra.getCli().getApellidos())
				.append("\n");
		ret.append("Email: ").append(compra.getCli().getEmail()).append("\n");
		ret.append("---------------------------\n");
		return ret;
	}

	/**
	 * Imprime el tiket en un fichero y te habisa
	 * 
	 * @param texto
	 * @param compra
	 */
	private void escribirTiket(String texto, Compra compra) {
		gestorFichero.sobreescribirFichero(texto, compra);
		System.out.println("Tiket impreso con exto");
		leerTiket();
	}

	/**
	 * Muestra el tiket por pantalla
	 */
	private void leerTiket() {
		ArrayList<String> lineas = gestorFichero.leerFichero();
		if (null != lineas)
			for (String linea : lineas) {
				System.out.println(linea);
			}
	}

}