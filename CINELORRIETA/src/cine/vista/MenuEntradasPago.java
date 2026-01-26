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
	private ArrayList<Entrada> carro;

	public MenuEntradasPago() {
		controladores = new Teclado();
		carro = new ArrayList<Entrada>();
	}

	/**
	 * Recorre carro y va sumando los precios de las entradas de el carro
	 * 
	 * @param carro
	 * @return total de el precio de entradas
	 */
	private double precioCarro() {
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
	private double descuentoTotal() {
		ArrayList<Integer> peliculasDistintas = new ArrayList<>();
		int totalEntradas = 0;
		for (Entrada entrada : carro) {
			int idPeli = entrada.getSesion().getPeli().getIdPelicula();
			if (!peliculasDistintas.contains(idPeli))
				peliculasDistintas.add(idPeli);
			totalEntradas += entrada.getNumPersonas();
		}
		if (totalEntradas >= 3)
			return 0.30;
		else if (peliculasDistintas.size() >= 2)
			return 0.20;
		return 0;
	}

	private Compra compras(Cliente cliente) {
		GestosCompras gestor = new GestosCompras();
		Compra ret = new Compra();

		ret.setFechaHora(LocalDate.now());
		ret.setCli(cliente);

		double descuento = descuentoTotal();
		for (Entrada entrada : carro) {
			double descuentoEntrada = entrada.getPrecio() * descuento;
			entrada.setDescuento(descuentoEntrada);
			entrada.setPrecio(entrada.getPrecio() - descuentoEntrada);
		}

		double totalFinal = precioCarro();
		ret.setPrecioTotal(totalFinal);

		gestor.insertCompra(ret);
		return ret;
	}

	private void entradas(ArrayList<Entrada> entradas, Compra compra) {
		GestorEntradas dBAcces = new GestorEntradas();
		for (Entrada entrada : entradas) {
			entrada.setCompra(compra); // asociar la compra a entrada
			dBAcces.insertarEntrada(entrada);
		}
	}

	public void pagar(Cliente cliente) {
		if (cliente != null) {
			System.out.println(carro);
			Compra compra = compras(cliente);
			entradas(carro, compra);
			carro.clear(); // Vaciar carro después del pago
			System.out.println("Compra realizada con éxito.");
		} else {
			System.out.println("Recuerda que es necesario iniciar sesión antes de pagar");
		}
	}

	public ArrayList<Entrada> crearEntradaTemp(Sesion sesion) {
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