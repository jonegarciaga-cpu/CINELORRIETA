package cine.modelo.utils;

public class Conectividad {

	/**
	 * Verifica que la BBDD es correctamente conectada a mi programa.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver cargado correctamente");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver NO encontrado");
		}
	}
}
