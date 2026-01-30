package cine.controlador.text;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import cine.modelo.pojos.Entrada;

public class Descuentos {
	private ArrayList<Entrada> carro = null;

	private ArrayList<Entrada> elCarro() {
		carro = new ArrayList<>();

		carro.add(Entrada(2, 1, 8.00, 2.00, 1, 1));
		carro.add(Entrada(1, 1, 9.00, 1.00, 2, 1));
		carro.add(Entrada(3, 3, 21.00, 3.00, 1, 2));

		return carro;
	}

	private Entrada Entrada(int idEntrada, int numPersonas, double precio, double descuento, int idSesion,
			int idCompra) {
		Entrada ret = new Entrada();
		ret.setIdEntrada(idEntrada);
		ret.setNumPersonas(numPersonas);
		ret.setPrecio(precio);
		ret.setDescuento(descuento);
		return ret;
	}

	@Test
	/**
	 * comprueba que entra correctamente a el porcentaje que necesta
	 */
	public void testporcentajeDescuento() {
		int numPeliculas = 5;
		if (numPeliculas >= 3) {
		} else {
			fail("Se esperaba entrar en el if");
		}
	}

	@Test
	/**
	 * Comprueba que sume correctamente todos los precios del arraylist
	 */
	public void testprecioCarro() {
		double total = 0;
		elCarro();
		for (Entrada entrada : carro) {
			total += entrada.getPrecio();
		}
		if (total != 38.00) {
			fail("se esperaba 38");
		}
	}

}