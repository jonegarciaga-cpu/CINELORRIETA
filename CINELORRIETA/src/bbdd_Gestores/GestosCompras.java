package bbdd_Gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import bbdd_Pojos.Compra;
import utiles.DBUtils;

public class GestosCompras {
	/**
	 * Inserta la Compra con su Cliente
	 * 
	 * @param Compra con su Cliente
	 */
	public int insertCompra(Compra compra) {
		String sql = "INSERT INTO compra (fechaHora, precioTotal, descuento, dni) VALUES (?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			// valores de compra
			ps.setTimestamp(1, Timestamp.valueOf(compra.getFechaHora().atStartOfDay()));
			ps.setDouble(2, compra.getPrecioTotal());
			ps.setDouble(3, compra.getDescuento());
			ps.setString(4, compra.getCli().getDni());

			int filasAfectadas = ps.executeUpdate();
			if (filasAfectadas == 0) {
				throw new SQLException("No se pudo insertar la compra, ninguna fila afectada.");
			}

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					int idGenerado = rs.getInt(1);
					compra.setIdCompra(idGenerado);
					return idGenerado;
				} else {
					throw new SQLException("No se pudo obtener el ID generado.");
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return -1; // retorno de error
	}
}
