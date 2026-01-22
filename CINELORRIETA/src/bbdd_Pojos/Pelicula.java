package bbdd_Pojos;

import java.util.Objects;

public class Pelicula {
	public int idPelicula = 0;
	private String nombre = null;
	private int duracion = 0;
	private String genero = null;
	private double precio = 0;

	public int getIdPelicula() {
		return idPelicula;
	}

	public void setIdPelicula(int idPelicula) {
		this.idPelicula = idPelicula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(duracion, genero, idPelicula, nombre, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pelicula other = (Pelicula) obj;
		return duracion == other.duracion && Objects.equals(genero, other.genero) && idPelicula == other.idPelicula
				&& Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio);
	}

	@Override
	public String toString() {
		return "Pelicula [idPelicula=" + idPelicula + ", nombre=" + nombre + ", duracion=" + duracion + ", genero="
				+ genero + ", precio=" + precio + "]";
	}

	public String toStringSimple() {
		return "  Pelicula [idPelicula=" + idPelicula + ", nombre=" + nombre + ", precio=" + precio + "]";
	}

}
