package cine.modelo.pojos;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Describe la tabla Sesion
 */
public class Sesion {

	// -- Atributos de la tabla --

	public int idSesion = 0;
	private Timestamp fechaIni = null;
	private Date fechaFin = null;
	private double precio = 0;
	private int espectadores = 0;

	// -- Relaciones --

	// Relación 1 a 1
	private Sala sala = null;

	// Relación 1 a 1
	private Pelicula peli = null;

	public Sesion() {
	}

	public int getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(int idSesion) {
		this.idSesion = idSesion;
	}

	public Timestamp getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Timestamp timestamp) {
		this.fechaIni = timestamp;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getEspectadores() {
		return espectadores;
	}

	public void setEspectadores(int espectadores) {
		this.espectadores = espectadores;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Pelicula getPeli() {
		return peli;
	}

	public void setPeli(Pelicula peli) {
		this.peli = peli;
	}

	@Override
	public int hashCode() {
		return Objects.hash(espectadores, fechaFin, fechaIni, idSesion, peli, precio, sala);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sesion other = (Sesion) obj;
		return espectadores == other.espectadores && Objects.equals(fechaFin, other.fechaFin)
				&& Objects.equals(fechaIni, other.fechaIni) && idSesion == other.idSesion
				&& Objects.equals(peli, other.peli)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio)
				&& Objects.equals(sala, other.sala);
	}

	@Override
	public String toString() {
		return "Sesion [idSesion=" + idSesion + ", fechaIni=" + fechaIni + ", fechaFin=" + fechaFin + ", precio="
				+ precio + ", espectadores=" + espectadores + ", sala=" + sala + ", peli=" + peli + "]";
	}

	public String toStringSimple() {
		return "Sesion [idSesion=" + idSesion + ", fechaIni=" + fechaIni + ", precio=" + precio + "]";
	}

}
