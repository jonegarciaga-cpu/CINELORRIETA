package gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import pojos.Compra;
import utiles.DBUtils;

public class GestosCompras {
	/**
	 * Inserta la Compra con su Cliente
	 * 
	 * @param Compra con su Cliente
	 */
	public void insertCompra(Compra compra) {

		String sql = "INSERT INTO compra (fecha_hora, precio_total, descuento, dni_cliente) " + "VALUES (?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);
				PreparedStatement ps = connection.prepareStatement(sql)) {

			Class.forName(DBUtils.DRIVER);

			ps.setDate(1, java.sql.Date.valueOf(compra.getFechaHora()));
			ps.setDouble(2, compra.getPrecioTotal());
			ps.setDouble(3, compra.getDescuento());
			ps.setString(4, compra.getCli().getDni());

			ps.executeUpdate();

		} catch (SQLException sqle) {
			System.out.println("Error con la BBDD - " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("Error genérico - " + e.getMessage());
		}
	}
}
