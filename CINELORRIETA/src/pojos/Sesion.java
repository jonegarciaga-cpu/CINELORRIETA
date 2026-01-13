package pojos;

import java.sql.Date;
import java.util.Objects;

public class Sesion {

	private Sala sala = new Sala();
	private Pelicula peli = new Pelicula();

	public int idSesion = 0;
	private Date FechaIni = null;
	private Date FechaFin = null;
	private double precio = 0;
	private int espectadores = 0;
	private int idSala = sala.idSala;
	private int idPelicula = peli.idPelicula;

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

	public int getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(int idSesion) {
		this.idSesion = idSesion;
	}

	public Date getFechaIni() {
		return FechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		FechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return FechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		FechaFin = fechaFin;
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

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public int getIdPelicula() {
		return idPelicula;
	}

	public void setIdPelicula(int idPelicula) {
		this.idPelicula = idPelicula;
	}

	@Override
	public int hashCode() {
		return Objects.hash(FechaFin, FechaIni, espectadores, idPelicula, idSala, idSesion, peli, precio, sala);
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
		return Objects.equals(FechaFin, other.FechaFin) && Objects.equals(FechaIni, other.FechaIni)
				&& espectadores == other.espectadores && idPelicula == other.idPelicula && idSala == other.idSala
				&& idSesion == other.idSesion && Objects.equals(peli, other.peli)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio)
				&& Objects.equals(sala, other.sala);
	}

	@Override
	public String toString() {
		return "Sesion [sala=" + sala + ", peli=" + peli + ", idSesion=" + idSesion + ", FechaIni=" + FechaIni
				+ ", FechaFin=" + FechaFin + ", precio=" + precio + ", espectadores=" + espectadores + ", idSala="
				+ idSala + ", idPelicula=" + idPelicula + "]";
	}

}
