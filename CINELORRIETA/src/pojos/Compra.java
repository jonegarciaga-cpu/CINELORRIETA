package pojos;

import java.sql.Date;
import java.util.Objects;

public class Compra {
	private Cliente cli = new Cliente();

	public int idCompra = 0;
	private Date fechaHora = null;
	private double precioTotal = 0;
	private double descuento = 0;
	private String dni = cli.dni;

	public Cliente getCli() {
		return cli;
	}

	public void setCli(Cliente cli) {
		this.cli = cli;
	}

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cli, descuento, dni, fechaHora, idCompra, precioTotal);
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
				&& Objects.equals(dni, other.dni) && Objects.equals(fechaHora, other.fechaHora)
				&& idCompra == other.idCompra
				&& Double.doubleToLongBits(precioTotal) == Double.doubleToLongBits(other.precioTotal);
	}

	@Override
	public String toString() {
		return "Compra [cli=" + cli + ", idCompra=" + idCompra + ", fechaHora=" + fechaHora + ", precioTotal="
				+ precioTotal + ", descuento=" + descuento + ", dni=" + dni + "]";
	}

}
