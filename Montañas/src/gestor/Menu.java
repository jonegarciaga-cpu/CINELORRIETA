package gestor;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

	private Scanner teclado = null;
	private Gestor2 gestor = null;

	public Menu() {
		teclado = new Scanner(System.in);
		gestor = new Gestor2();
	}

	private int escribirMenu() {
		int ret = 0;
		do {
			try {
				pintarMenu();
				System.out.print("Escoge una opcion: ");
				ret = teclado.nextInt();
				teclado.nextLine();
			} catch (Exception e) {
				System.out.println("Error!!! Opcion incorrecta");
				teclado.nextLine();
				ret = -1;
			}
		} while ((ret < 0) || (ret > 3));
		return ret;
	}

	private void pintarMenu() {
		System.out.println(" ");
		System.out.println("- Menu Inicial -");
		System.out.println("----------------");
		System.out.println("1.Visualizar todos los montes del fichero");
		System.out.println("2.Buscar un Monte en el fichero ");
		System.out.println("3.Mostrar montes con una altura igual o superior a la indicada por el usuario");
		System.out.println("0. Salir");
		System.out.println(" ");
	}

	public void mostrarMenu() {
		int opcionMenu = 0;

		do {
			opcionMenu = escribirMenu();

			switch (opcionMenu) {
			case 0:
				System.out.println("Adios !!!");
				break;
			case 1:
				mostrarTodosMontes();
				break;
			case 2:
				buscarMonte();
				break;
			case 3:
				buscarMontesAltos();
				break;
			default:
				System.out.println("Esta opcion no deberia salir");
			}

		} while (opcionMenu != 0);

		teclado.close();
	}

	private void buscarMontesAltos() {
		int altura = pideNumero("Dame la altura que desees verificar que montes sean igual o mas altos.");
		String montesAltos = gestor.mostrarMontesPorAltura(altura);
		if (montesAltos != null && altura < 1529) {
			System.out.print(montesAltos);
		} else {
			System.out.println("No hay montes o no hay montes mas altos que esa altura.");
			System.out.println("La altura mas alta es 1528");
		}
	}

	private void buscarMonte() {
		String bMonte = leerDeTeclado();
		ArrayList<String> montes = gestor.listaNombres();
		if (montes != null) {
			for (int i = 0; i < montes.size(); i++) {
				if (bMonte.equalsIgnoreCase(montes.get(i))) {
					System.out.println("Monte existente:");
					String monte = gestor.leerMonte(bMonte);
					System.out.print(monte);
					break;
				} else if (i == montes.size()) {
					System.out.print("Monte no exitente o no encontrado");
				}
			}
		} else {
			System.out.print("no hay montes");
		}

	}

	private void mostrarTodosMontes() {
		String montes = gestor.leerFichero();
		if (montes != null) {
			System.out.print(montes);
		} else {
			System.out.print("no hay montes");
		}
	}

	private String leerDeTeclado() {
		String ret = null;
		System.out.print("Dame un texto: ");
		ret = teclado.nextLine();
		return ret;
	}

	private int pideNumero(String texto) {
		int numero;
		while (true) {
			try {
				System.out.println(texto);
				numero = teclado.nextInt();
				teclado.nextLine();
				if (numero >= 0) {
					return numero;
				} else {
					System.out.println("El número debe ser mayor o igual a 0.");
				}
			} catch (Exception e) {
				System.out.println("Entrada no válida. Por favor, introduce un número entero.");
				teclado.nextLine(); // limpiar entrada incorrecta
			}
		}
	}
}