package bbdd_Pojos;

import java.time.LocalDate;
import java.util.Objects;

public class Compra {

	public int idCompra = 0;
	private LocalDate fechaHora = null;
	private double precioTotal = 0;
	private double descuento = 0;
	private Cliente cli = null;

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	public LocalDate getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDate fechaHora) {
		this.fechaHora = fechaHora;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public Cliente getCli() {
		return cli;
	}

	public void setCli(Cliente cli) {
		this.cli = cli;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cli, descuento, fechaHora, idCompra, precioTotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compra other = (Compra) obj;
		return Objects.equals(cli, other.cli)
				&& Double.doubleToLongBits(descuento) == Double.doubleToLongBits(other.descuento)
				&& Objects.equals(fechaHora, other.fechaHora) && idCompra == other.idCompra
				&& Double.doubleToLongBits(precioTotal) == Double.doubleToLongBits(other.precioTotal);
	}

	@Override
	public String toString() {
		return "Compra [idCompra=" + idCompra + ", fechaHora=" + fechaHora + ", precioTotal=" + precioTotal
				+ ", descuento=" + descuento + ", cli=" + cli + "]";
	}

}
