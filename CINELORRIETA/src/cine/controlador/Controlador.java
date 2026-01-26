package cine.controlador;

import java.util.ArrayList;
import java.util.Scanner;

import cine.modelo.dao.GestorPeliculas;
import cine.modelo.dao.GestorSesiones;
import cine.modelo.pojos.Pelicula;
import cine.modelo.pojos.Sesion;

public class Controlador {

	private Scanner sc = new Scanner(System.in);

	/**
	 * Método que carga todas las películas de la base de datos.
	 * 
	 * @return Lista de películas
	 */
	public ArrayList<Pelicula> cargarPeliculas() {
		GestorPeliculas dBAcces = new GestorPeliculas();
		return dBAcces.getAllPeliculas();
	}

	/**
	 * Método que carga las sesiones de una película en concreto.
	 * 
	 * @param idPelicula Id de la película
	 * @return Lista de sesiones de la película
	 */
	public ArrayList<Sesion> cargarSesiones(int idPelicula) {
		GestorSesiones gSesiones = new GestorSesiones();
		return gSesiones.getAllSesiones(idPelicula);
	}

	/**
	 * Te obliga a mantenerte en un bucle hasta que selecciones un número positivo.
	 * 
	 * @param text Mensaje a mostrar al usuario
	 * @return Número entero positivo
	 */
	public int pideNumero(String text) {
		int ret = -1;
		boolean error = true;
		do {
			try {
				System.out.println(text);
				ret = sc.nextInt();
				sc.nextLine();
				error = !(ret > -1);
			} catch (Exception e) {
				System.out.println("No es un número, por favor ingrese un número");
				error = true;
				sc.nextLine();
			}
		} while (error);
		return ret;
	}

	/**
	 * Te obliga a mantenerte en un bucle hasta que selecciones un número positivo.
	 * 
	 * @param text Mensaje a mostrar al usuario
	 * @return Número decimal positivo
	 */
	public double pideNumeroDouble(String text) {
		double ret = -1;
		boolean error = true;
		do {
			try {
				System.out.println(text);
				System.out.println("Recuerde usar , no .");
				ret = sc.nextDouble();
				sc.nextLine();
				error = !(ret > -1);
			} catch (Exception e) {
				System.out.println("No es un número, por favor ingrese un número");
				error = true;
				sc.nextLine();
			}
		} while (error);
		return ret;
	}

	/**
	 * Te obliga a mantenerte en un bucle hasta que selecciones un "sí" o "no".
	 * 
	 * @param text Mensaje a mostrar al usuario
	 * @return true si la respuesta es "sí", false si es "no"
	 */
	public boolean pideBooleano(String text) {
		boolean ret = false;
		boolean error = true;
		do {
			try {
				System.out.println(text);
				String input = sc.nextLine().trim().toLowerCase().charAt(0) + "";
				error = false;
				if (input.equalsIgnoreCase("s")) {
					ret = true;
				} else if (input.equalsIgnoreCase("n")) {
					ret = false;
				} else {
					System.out.println("Error, escribe s/n");
				}
			} catch (Exception e) {
				System.out.println("Error al leer la entrada.");
				error = true;
				sc.nextLine();
			}
		} while (error);
		return ret;
	}

	/**
	 * Lee por el teclado lo que escribe el usuario y lo retorna.
	 * 
	 * @param text Mensaje a mostrar al usuario
	 * @return Texto ingresado por el usuario
	 */
	public String leerDeTeclado(String text) {
		String ret = null;
		System.out.print(text);
		ret = sc.nextLine();
		return ret;
	}

	/**
	 * Limpia la pantalla para que no aparezcan demasiadas cosas en consola.
	 */
	public void limpiarPantalla() {
		for (int i = 0; i < 20; i++) {
			System.out.println();
		}
	}
}