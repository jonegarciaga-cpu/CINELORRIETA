package cine.modelo.pojos;

import java.util.Objects;

public class Entrada {

	private int idEntrada = 0;
	private int numPersonas = 0;
	private double precio = 0;
	private double descuento = 0;
	private Sesion sesion = null;
	private Compra compra = null;

	public int getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(int idEntrada) {
		this.idEntrada = idEntrada;
	}

	public int getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(int numPersonas) {
		this.numPersonas = numPersonas;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public Sesion getSesion() {
		return sesion;
	}

	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	@Override
	public int hashCode() {
		return Objects.hash(compra, descuento, idEntrada, numPersonas, precio, sesion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entrada other = (Entrada) obj;
		return Objects.equals(compra, other.compra)
				&& Double.doubleToLongBits(descuento) == Double.doubleToLongBits(other.descuento)
				&& idEntrada == other.idEntrada && numPersonas == other.numPersonas
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio)
				&& Objects.equals(sesion, other.sesion);
	}

	@Override
	public String toString() {
		return "Entrada [idEntrada=" + idEntrada + ", numPersonas=" + numPersonas + ", precio=" + precio
				+ ", descuento=" + descuento + ", sesion=" + sesion + ", compra=" + compra + "]";
	}

}
