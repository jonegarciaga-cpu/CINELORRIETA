package gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pojos.Pelicula;
import utiles.DBUtils;

public class DBpeliculas {

	/**
	 * Retorna todas las filas de la tabla Peliculas. Si la consulta no devuelve
	 * nada, retorna NULL
	 * 
	 * @return todos las peliculas o null
	 */
	public ArrayList<Pelicula> getAllPeliculas() {
		ArrayList<Pelicula> ret = null;

		// SQL que queremos lanzar
		String sql = "select * from Pelicula";

		// La conexion con BBDD
		Connection connection = null;

		// Vamos a lanzar una sentencia SQL contra la BBDD
		// Result set va a contener todo lo que devuelve la BBDD
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			// El Driver que vamos a usar
			Class.forName(DBUtils.DRIVER);

			// Abrimos la conexion con BBDD
			connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);

			// Vamos a lanzar la sentencia...
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			// Recorremos resultSet, que tiene las filas de la tabla
			while (resultSet.next()) {

				// Hay al menos una fila en el cursos, inicializamos el ArrayList
				if (null == ret)
					ret = new ArrayList<Pelicula>();

				// El Alumno
				Pelicula pelicula = new Pelicula();

				// Sacamos las columnas del resultSet
				int idPelicula = resultSet.getInt("idPelicula");
				String nombre = resultSet.getString("nombre");
				int duracion = resultSet.getInt("duracion");
				String genero = resultSet.getString("genero");
				double precio = resultSet.getDouble("precio");

				// Metemos los datos en Alumno
				pelicula.setIdPelicula(idPelicula);
				pelicula.setNombre(nombre);
				pelicula.setDuracion(duracion);
				pelicula.setGenero(genero);
				pelicula.setPrecio(precio);

				// Lo guardamos en la lista
				ret.add(pelicula);
			}
		} catch (SQLException sqle) {
			System.out.println("Error con la BBDD - " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("Error generico - " + e.getMessage());
		} finally {
			// Cerramos al reves de como las abrimos
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (Exception e) {
				// No hace falta
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				// No hace falta
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				// No hace falta
			}
		}
		return ret;
	}

}
