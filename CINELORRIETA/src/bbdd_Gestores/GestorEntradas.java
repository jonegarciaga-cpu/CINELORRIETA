package bbdd_Gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bbdd_Pojos.Entrada;
import utiles.DBUtils;

public class GestorEntradas {

	/**
	 * Inserta la entrada con su sesión
	 * 
	 * @param entrada con su sesión
	 */
	public void insertarEntrada(Entrada entrada) {

		String sql = "INSERT INTO entrada(numPersonas, precio, idSesion, idCompra) VALUES (?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
		// Esto permite obtener el ID generado
		) {

			ps.setInt(1, entrada.getNumPersonas());
			ps.setDouble(2, entrada.getPrecio()); // O BigDecimal PERO NS LO TOCO
			ps.setInt(3, entrada.getSesion().getIdSesion());
			ps.setInt(4, entrada.getCompra().getIdCompra());

			ps.executeUpdate();

			// Recuperar el id autogenerado
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				entrada.setIdEntrada(rs.getInt(1));
			}

		} catch (SQLException e) {
			System.out.println("Error con la BBDD - " + e.getMessage());
		}
	}

}
