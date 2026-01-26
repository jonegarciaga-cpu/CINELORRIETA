package cine.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cine.modelo.pojos.Pelicula;
import cine.modelo.utils.DBUtils;

public class GestorPeliculas {

	/**
	 * Retorna todas las filas de la tabla Peliculas. Si la consulta no devuelve
	 * nada, retorna NULL
	 * 
	 * @return todos las peliculas o null
	 */
	public ArrayList<Pelicula> getAllPeliculas() {
		ArrayList<Pelicula> ret = null;
		String sql = "SELECT DISTINCT p.idPelicula, p.nombre, p.duracion, p.genero, p.precio FROM Pelicula p JOIN Sesion s ON p.idPelicula = s.idPelicula ORDER BY s.fechaIni ASC; ";
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			Class.forName(DBUtils.DRIVER);
			connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				if (null == ret)
					ret = new ArrayList<Pelicula>();
				Pelicula pelicula = new Pelicula();

				int idPelicula = resultSet.getInt("idPelicula");
				String nombre = resultSet.getString("nombre");
				int duracion = resultSet.getInt("duracion");
				String genero = resultSet.getString("genero");
				double precio = resultSet.getDouble("precio");

				pelicula.setIdPelicula(idPelicula);
				pelicula.setNombre(nombre);
				pelicula.setDuracion(duracion);
				pelicula.setGenero(genero);
				pelicula.setPrecio(precio);

				ret.add(pelicula);
			}
		} catch (SQLException sqle) {
			System.out.println("Error con la BBDD - " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("Error generico - " + e.getMessage());
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (Exception e) {
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}
		return ret;
	}

}
