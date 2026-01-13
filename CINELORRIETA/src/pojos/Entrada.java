package pojos;

import java.util.Objects;

public class Entrada {

	private Sesion sesion = new Sesion();
	private Compra compra = new Compra();

	private int idEntrada = 0;
	private int numPersonas = 0;
	private double precio = 0;
	private double descuento = 0;
	private int idSesion = sesion.idSesion;
	private int idCompra = compra.idCompra;

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

	public int getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(int idSesion) {
		this.idSesion = idSesion;
	}

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	@Override
	public int hashCode() {
		return Objects.hash(compra, descuento, idCompra, idEntrada, idSesion, numPersonas, precio, sesion);
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
				&& idCompra == other.idCompra && idEntrada == other.idEntrada && idSesion == other.idSesion
				&& numPersonas == other.numPersonas
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio)
				&& Objects.equals(sesion, other.sesion);
	}

	@Override
	public String toString() {
		return "Entrada [sesion=" + sesion + ", compra=" + compra + ", idEntrada=" + idEntrada + ", numPersonas="
				+ numPersonas + ", precio=" + precio + ", descuento=" + descuento + ", idSesion=" + idSesion
				+ ", idCompra=" + idCompra + "]";
	}

}
