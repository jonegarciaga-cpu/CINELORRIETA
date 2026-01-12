package gestor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Gestor2 {
	private static final String NOMBRE_FICHERO = "Montes.txt";
	private static final String RUTA_ABSOLUTA_FICHERO = "C:\\Users\\in1dw3\\Desktop\\JONE\\Program\\";

	public String leerFichero() {
		String ret = null;

		File file = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		file = new File(RUTA_ABSOLUTA_FICHERO + NOMBRE_FICHERO);
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			String linea;
			while ((linea = bufferedReader.readLine()) != null) {
				if (null == ret)
					ret = new String();
				ret = ret + linea + "\n";
			}

		} catch (FileNotFoundException e) {
			System.out.println("El fichero " + RUTA_ABSOLUTA_FICHERO + NOMBRE_FICHERO + " no existe");
		} catch (IOException e) {
			System.out.println("Error de lectura del fichero " + RUTA_ABSOLUTA_FICHERO + NOMBRE_FICHERO);
		} catch (Exception e) {
			System.out.println("Error de lectura del fichero " + RUTA_ABSOLUTA_FICHERO + NOMBRE_FICHERO);
		} finally {
			try {
				if (null != bufferedReader)
					bufferedReader.close();
			} catch (IOException e) {
				// Nos da igual
			}
			try {
				if (null != fileReader)
					fileReader.close();
			} catch (IOException e) {
				// Nos da igual
			}
		}
		return ret;
	}

	public String leerMonte(String nombreMonte) {
		StringBuilder ret = new StringBuilder();

		File file = new File(RUTA_ABSOLUTA_FICHERO + NOMBRE_FICHERO);
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String linea;
			boolean enMonte = false;

			while ((linea = bufferedReader.readLine()) != null) {
				if (linea.startsWith("Nombre:")) {
					if (linea.substring(7).trim().equalsIgnoreCase(nombreMonte)) {
						enMonte = true;
					} else {
						enMonte = false;
					}
				}
				if (enMonte) {
					ret.append(linea).append("\n");
				}
				if (linea.equals("----------------")) {
					enMonte = false;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no existe");
		} catch (IOException e) {
			System.out.println("Error de lectura del fichero");
		}
		return ret.toString();

	}

	public ArrayList<String> listaNombres() {
		ArrayList<String> montes = new ArrayList<>();
		String contenido = leerFichero();
		String[] lineas = contenido.split("\n"); // separa por lineas
		for (String linea : lineas) {
			linea = linea.trim();
			if (linea.startsWith("Nombre:")) {
				String nombre = linea.substring("Nombre:".length()).trim();
				montes.add(nombre);
			}
		}
		return montes;
	}

	public String mostrarMontesPorAltura(int alturaMinima) {
		StringBuilder resultado = new StringBuilder();
		StringBuilder monteActual = new StringBuilder();
		int alturaMonte = -1;
		File file = new File(RUTA_ABSOLUTA_FICHERO + NOMBRE_FICHERO);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (!linea.equals("----------------")) {
					monteActual.append(linea).append("\n");
					if (linea.startsWith("Altura:")) {
						alturaMonte = Integer.parseInt(linea.substring("Altura:".length()).trim());
					}
				} else {
					if (alturaMonte >= alturaMinima) {
						resultado.append(monteActual);
						resultado.append("----------------\n");
					}
					monteActual.setLength(0);
					alturaMonte = -1;
				}
			}
		} catch (IOException e) {
			System.out.println("Error leyendo el fichero");
		}
		return resultado.toString();
	}
}
