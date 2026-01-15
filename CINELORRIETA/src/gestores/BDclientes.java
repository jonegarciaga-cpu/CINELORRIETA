package gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pojos.Cliente;
import utiles.DBUtils;

public class BDclientes {
	/**
	 * Inserta un alumno en la tabla t_alumno
	 * 
	 * @param cliente El cliente a insertar
	 */
	public void insertEjemplo(Cliente cliente) {

		// La conexion con BBDD
		Connection connection = null;

		// Vamos a lanzar una sentencia SQL contra la BBDD
		Statement statement = null;

		try {
			// El Driver que vamos a usar
			Class.forName(DBUtils.DRIVER);

			// Abrimos la conexion con BBDD
			connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);

			// Vamos a lanzar la sentencia...
			statement = connection.createStatement();

			// Montamos la SQL. Esta es una forma simple de hacerlo, hay otra mejor...
			String sql = "insert into cliente (dni ,nombre, apellidos, email, password) VALUES ('" + cliente.getDni()
					+ "', '" + cliente.getNombre() + "', '" + cliente.getApellidos() + "', '" + cliente.getEmail()
					+ "', '" + cliente.getPassword() + "')";

			statement.executeUpdate(sql);

		} catch (SQLException sqle) {
			System.out.println("Error con la BBDD - " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("Error generico - " + e.getMessage());
		} finally {
			// Cerramos al reves de como las abrimos
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

	/**
	 * Retorna todas las filas de la tabla Clientes. Si la consulta no devuelve
	 * nada, retorna NULL
	 */
	public ArrayList<Cliente> getAllClientes() {
		ArrayList<Cliente> ret = null;

		// SQL que queremos lanzar
		String sql = "select * from Cliente";

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
					ret = new ArrayList<Cliente>();

				// El Alumno
				Cliente cliente = new Cliente();

				// Sacamos las columnas del resultSet
				String dni = resultSet.getString("dni");
//				String nombre = resultSet.getString("nombre");
//				String apellidos = resultSet.getString("apellidos");
//				String email = resultSet.getString("email");
				String password = resultSet.getString("password");

				// Metemos los datos en Alumno
				cliente.setDni(dni);
//				cliente.setNombre(nombre);
//				cliente.setApellidos(apellidos);
//				cliente.setEmail(email);
				cliente.setPassword(password);

				// Lo guardamos en la lista
				ret.add(cliente);
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
