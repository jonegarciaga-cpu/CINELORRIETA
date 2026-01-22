package bbdd_Pojos;

import java.util.Objects;

public class Sala {

	public int idSala = 0;
	private String nombre = null;
	private Sesion sesion = new Sesion();

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Sesion getSesion() {
		return sesion;
	}

	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSala, nombre, sesion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sala other = (Sala) obj;
		return idSala == other.idSala && Objects.equals(nombre, other.nombre) && Objects.equals(sesion, other.sesion);
	}

	@Override
	public String toString() {
		return "Sala [idSala=" + idSala + ", nombre=" + nombre + ", sesion=" + sesion + "]";
	}

}
