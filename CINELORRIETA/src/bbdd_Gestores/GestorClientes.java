package bbdd_Gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bbdd_Pojos.Cliente;
import utiles.DBUtils;

public class GestorClientes {
	/**
	 * Inserta un cliente en la tabla clientes
	 * 
	 * @param cliente El cliente a insertar
	 */
	public void insertCliente(Cliente cliente) {

		String sql = "INSERT INTO cliente (dni, nombre, apellidos, email, password) VALUES (?, ?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);
				PreparedStatement ps = connection.prepareStatement(sql)) {

			Class.forName(DBUtils.DRIVER);

			ps.setString(1, cliente.getDni());
			ps.setString(2, cliente.getNombre());
			ps.setString(3, cliente.getApellidos());
			ps.setString(4, cliente.getEmail());
			ps.setString(5, cliente.getPassword()); // El trigger se encargará de cifrar la contraseña

			ps.executeUpdate();

			System.out.println("Cliente insertado correctamente.");

		} catch (SQLException sqle) {
			System.out.println("Error con la BBDD - " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("Error generico - " + e.getMessage());
		}
	}

	/**
	 * Retorna todas las filas de la tabla Clientes. Si la consulta no devuelve
	 * nada, retorna NULL
	 */
	public ArrayList<Cliente> getAllClientes() {
		ArrayList<Cliente> ret = new ArrayList<>();

		String sql = "SELECT dni, nombre, apellidos, email, AES_DECRYPT(password, 'mi_clave_secreta') AS password_plain FROM Cliente";

		try (Connection connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			Class.forName(DBUtils.DRIVER);

			while (resultSet.next()) {
				Cliente cliente = new Cliente();

				cliente.setDni(resultSet.getString("dni"));
				cliente.setNombre(resultSet.getString("nombre"));
				cliente.setApellidos(resultSet.getString("apellidos"));
				cliente.setEmail(resultSet.getString("email"));
				cliente.setPassword(resultSet.getString("password_plain")); // Descifrada

				ret.add(cliente);
			}

		} catch (SQLException sqle) {
			System.out.println("Error con la BBDD - " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("Error generico - " + e.getMessage());
		}

		return ret;
	}

}
