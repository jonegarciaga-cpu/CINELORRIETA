package menu;

import java.time.LocalDate;
import java.util.ArrayList;

import bbdd_Gestores.GestorEntradas;
import bbdd_Gestores.GestosCompras;
import bbdd_Pojos.Cliente;
import bbdd_Pojos.Compra;
import bbdd_Pojos.Entrada;
import bbdd_Pojos.Sesion;
import utiles.Controladores;

public class MenuEntradasPago {

	private Controladores con;
	private ArrayList<Entrada> carro;

	public MenuEntradasPago() {
		con = new Controladores();
		carro = new ArrayList<>();
	}

	private double precioCarro(ArrayList<Entrada> carro) {
		double total = 0;
		for (Entrada entrada : carro)
			total += entrada.getPrecio();
		return total;
	}

	private double descuentoTotal(ArrayList<Entrada> carro) {
		ArrayList<Integer> peliculasDistintas = new ArrayList<>();
		int totalEntradas = 0;
		for (Entrada entrada : carro) {
			int idPeli = entrada.getSesion().getPeli().getIdPelicula();
			if (!peliculasDistintas.contains(idPeli))
				peliculasDistintas.add(idPeli);
			totalEntradas += entrada.getNumPersonas();
		}
		if (peliculasDistintas.size() >= 2)
			return 0.20;
		else if (totalEntradas >= 3)
			return 0.30;
		return 0;
	}

	private Compra compras(Cliente cliente, ArrayList<Entrada> carro) {
		GestosCompras gc = new GestosCompras();
		Compra ret = new Compra();
		ret.setFechaHora(LocalDate.now());
		ret.setCli(cliente);
		double totalCarrito = precioCarro(carro);
		double descuento = descuentoTotal(carro);
		for (Entrada entrada : carro) {
			double descuentoEntrada = entrada.getPrecio() * descuento;
			entrada.setDescuento(descuentoEntrada);
			entrada.setPrecio(entrada.getPrecio() - descuentoEntrada);
		}
		double totalFinal = totalCarrito * (1 - descuento);
		ret.setPrecioTotal(totalFinal);
		gc.insertCompra(ret);
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
			Compra compra = compras(cliente, carro);
			entradas(carro, compra);
			carro.clear(); // Vaciar carro después del pago
			System.out.println("Compra realizada con éxito.");
		} else {
			System.out.println("Recuerda que es necesario iniciar sesión antes de pagar");
		}
	}

	public void crearEntradatemp(Sesion sesion) {
		int numPersonas = con.pideNumero("¿Para cuantos quieres la entrada?");
		double precioUna = sesion.getPrecio();

		Entrada entrada = new Entrada();
		entrada.setSesion(sesion);
		entrada.setNumPersonas(numPersonas);
		entrada.setPrecio(precioUna * numPersonas);

		carro.add(entrada);

		System.out.println("Entrada añadida al carrito");
	}

}