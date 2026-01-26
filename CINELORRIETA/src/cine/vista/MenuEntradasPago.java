package cine.vista;

import java.time.LocalDate;
import java.util.ArrayList;

import cine.modelo.dao.GestorEntradas;
import cine.modelo.dao.GestosCompras;
import cine.modelo.pojos.Cliente;
import cine.modelo.pojos.Compra;
import cine.modelo.pojos.Entrada;
import cine.modelo.pojos.Sesion;

public class MenuEntradasPago {

	private Teclado controladores;

	public MenuEntradasPago() {
		controladores = new Teclado();
	}

	/**
	 * Recorre carro y va sumando los precios de las entradas de el carro
	 * 
	 * @param carro
	 * @return total de el precio de entradas
	 */
	private double precioCarro(ArrayList<Entrada> carro) {
		double total = 0;
		for (Entrada entrada : carro)
			total += entrada.getPrecio();
		return total;
	}

	/**
	 * Recorre el carro contando las diferentes peliculas para saber el descueno que
	 * te corresponde
	 * 
	 * @param carro
	 * @return descuento:0.30, 0.20 o 0
	 */
	private double descuentoTotal(ArrayList<Entrada> carro) {
		double ret = 0;
		ArrayList<Integer> peliculasDistintas = new ArrayList<>();
		for (Entrada entrada : carro) {
			int idPeli = entrada.getSesion().getPeli().getIdPelicula();
			if (!peliculasDistintas.contains(idPeli)) {
				peliculasDistintas.add(idPeli);
				break;
			}
		}
		ret = porcentajeDescuento(peliculasDistintas.size());
		return ret;
	}

	private double porcentajeDescuento(int numPeliculas) {
		double ret = 0;
		if (numPeliculas >= 3) {
			ret = 0.30;
		} else if (numPeliculas >= 2) {
			ret = 0.20;
		} else {
			ret = 0;
		}
		return ret;
	}

	private Compra compras(Cliente cliente, ArrayList<Entrada> carro) {
		GestosCompras gestor = new GestosCompras();
		Compra ret = new Compra();

		ret.setFechaHora(LocalDate.now());
		ret.setCli(cliente);

		double descuento = descuentoTotal(carro);
		for (Entrada entrada : carro) {
			double descuentoEntrada = entrada.getPrecio() * descuento;
			entrada.setDescuento(descuentoEntrada);
			entrada.setPrecio(entrada.getPrecio() - descuentoEntrada);
		}

		double totalFinal = precioCarro(carro);
		ret.setPrecioTotal(totalFinal);

		gestor.insertCompra(ret);
		return ret;
	}

	private void entradas(Compra compra, ArrayList<Entrada> carro) {
		GestorEntradas dBAcces = new GestorEntradas();
		for (Entrada entrada : carro) {
			entrada.setCompra(compra); // asociar la compra a entrada
			dBAcces.insertarEntrada(entrada);
		}
	}

	public void pagar(Cliente cliente, ArrayList<Entrada> carro) {
		if (cliente != null) {

			Compra compra = compras(cliente, carro);
			entradas(compra, carro);
			carro.clear(); // Vaciar carro después del pago
			System.out.println("Compra realizada con éxito.");
			System.out.println(compra);
			System.out.println();

		} else {
			System.out.println("Recuerda que es necesario iniciar sesión antes de pagar");
		}
	}

	public ArrayList<Entrada> crearEntradaTemp(Sesion sesion, ArrayList<Entrada> carro) {
		int numPersonas = controladores.pideNumero("¿Para cuantos quieres la entrada?");
		double precioUna = sesion.getPrecio();

		Entrada entrada = new Entrada();
		entrada.setSesion(sesion);
		entrada.setNumPersonas(numPersonas);
		entrada.setPrecio(precioUna * numPersonas);

		carro.add(entrada);
		System.out.println("Entrada añadida al carrito");
		return carro;

	}

}
