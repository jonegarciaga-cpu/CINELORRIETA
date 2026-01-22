package utiles;

import java.util.Scanner;

public class Controladores {
	private Scanner sc = new Scanner(System.in);

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
					System.out.println("Error escribe s/n");
				}
			} catch (Exception e) {
				System.out.println("Error al leer la entrada.");
				error = true;
				sc.nextLine();
			}
		} while (error);
		return ret;
	}

	public String leerDeTeclado(String text) {
		String ret = null;
		System.out.print(text);
		ret = sc.nextLine();
		return ret;
	}	
}