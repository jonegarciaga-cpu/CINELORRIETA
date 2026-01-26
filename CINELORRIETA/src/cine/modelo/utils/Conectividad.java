package cine.modelo.utils;

public class Conectividad {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver cargado correctamente");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver NO encontrado");
		}
	}
}
