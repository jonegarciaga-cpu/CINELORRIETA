package cine.controlador.text;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;

import cine.modelo.dao.GestorClientes;
import cine.modelo.dao.GestorPeliculas;
import cine.modelo.dao.GestorSesiones;
import cine.modelo.pojos.Cliente;
import cine.modelo.pojos.Pelicula;
import cine.modelo.pojos.Sesion;

public class Gestor {
	public static int numCliente = 60;
	public static int numPelicula = 36;
	public static int numSesiones = 6;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	/**
	 * Comprueba que saca todas las peliculas de la bbdd
	 */
	public void testGetAllPeliculas() {
		GestorPeliculas controlador = new GestorPeliculas();
		ArrayList<Pelicula> peliculas = controlador.getAllPeliculas();
		if (peliculas.size() != numPelicula) {
			fail("Se esperaban " + numPelicula + " clientes");
		}
	}

	@Test
	/**
	 * Comprueba que saca todas las sesiones de la bbdd
	 * 
	 * SELECT * FROM `sesion` WHERE idPelicula= 3;
	 */
	public void testGetAllSesiones() {
		GestorSesiones controlador = new GestorSesiones();
		ArrayList<Sesion> sesiones = controlador.getAllSesiones(3);
		if (sesiones.size() != numSesiones) {
			fail("Se esperaban " + numSesiones + " clientes");
		}
	}

	@Test
	/**
	 * Comprueba que saca todos los clientes de la bbdd
	 */
	public void testGetAllClientes() {
		GestorClientes controlador = new GestorClientes();
		ArrayList<Cliente> clientes = controlador.getAllClientes();
		if (clientes.size() != numCliente) {
			fail("Se esperaban " + numCliente + " clientes");
		}
	}

}