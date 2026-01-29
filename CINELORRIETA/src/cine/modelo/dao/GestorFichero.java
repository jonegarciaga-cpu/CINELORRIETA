package cine.modelo.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import cine.modelo.pojos.Compra;

public class GestorFichero {

	private static final String NOMBRE_FICHERO = "Tiket.txt";
	private static final String RUTA_ABSOLUTA_FICHERO = "C:\\Users\\in1dw3\\Desktop\\";

	/**
	 * Escribe un texto pasado por parametro en el fichero
	 * 
	 * @param texto
	 */
	public void sobreescribirFichero(String texto, Compra compra) {
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		try {
			fileWriter = new FileWriter(RUTA_ABSOLUTA_FICHERO + NOMBRE_FICHERO + compra.getFechaHora());
			printWriter = new PrintWriter(fileWriter);
			printWriter.println(texto);
		} catch (IOException e) {
			System.out.println("Error de escritura en el fichero " + RUTA_ABSOLUTA_FICHERO + NOMBRE_FICHERO);
		} catch (Exception e) {
			System.out.println("Error de escritura en el fichero " + RUTA_ABSOLUTA_FICHERO + NOMBRE_FICHERO);
		} finally {

			if (null != printWriter)
				printWriter.close();
			try {
				if (null != fileWriter)
					fileWriter.close();
			} catch (IOException e) {
				// Nos da igual
			}
		}
	}

	/**
	 * Abre el fichero y retorna su contenido; o null si el fichero no existe o est�
	 * vacio
	 * 
	 * @return El texto o null
	 */
	public ArrayList<String> leerFichero() {
		ArrayList<String> ret = new ArrayList<String>();

		File file = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		file = new File(RUTA_ABSOLUTA_FICHERO + NOMBRE_FICHERO);
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String linea;
			while ((linea = bufferedReader.readLine()) != null) {
				ret.add(linea);
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

}
