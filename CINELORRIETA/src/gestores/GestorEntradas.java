package gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import pojos.Entrada;
import utiles.DBUtils;

public class GestorEntradas {

	/**
	 * Inserta la entrada con su sesión
	 * 
	 * @param entrada con su sesión
	 */
	public void insertarEntrada(Entrada entrada) {

		Connection connection = null;
		Statement statement = null;

		try {
			Class.forName(DBUtils.DRIVER);
			connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);
			statement = connection.createStatement();

			String sql = "INSERT INTO entrada(numPersonas,precio,idSesion)VALUES(" + entrada.getNumPersonas() + ", "
					+ entrada.getPrecio() + ", " + entrada.getSesion().getIdSesion() + " )";

			statement.executeUpdate(sql);

		} catch (SQLException sqle) {
			System.out.println("Error con la BBDD - " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("Error generico - " + e.getMessage());
		} finally {
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
	}

}
