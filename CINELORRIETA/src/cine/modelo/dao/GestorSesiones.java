package cine.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cine.modelo.pojos.Pelicula;
import cine.modelo.pojos.Sesion;
import cine.modelo.utils.DBUtils;

public class GestorSesiones {

	/**
	 * 
	 * @Retorna todas las sesiones de una película. Devuelve una lista vacía si no
	 *          hay sesiones.
	 */
	public ArrayList<Sesion> getAllSesiones(int pelicula) {
		ArrayList<Sesion> ret = new ArrayList<>();

		String sql = """
				SELECT idSesion, FechaIni, precio, idSala, idPelicula
				FROM Sesion
				WHERE idPelicula = ?
				ORDER BY FechaIni ASC
				""";
		try {
			Class.forName(DBUtils.DRIVER);

			try (Connection connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);
					PreparedStatement ps = connection.prepareStatement(sql)) {

				ps.setInt(1, pelicula);

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Sesion sesion = new Sesion();

						sesion.setIdSesion(rs.getInt("idSesion"));
						sesion.setFechaIni(rs.getTimestamp("FechaIni"));
						sesion.setPrecio(rs.getDouble("precio"));

						Pelicula peli = new Pelicula();
						peli.setIdPelicula(rs.getInt("idPelicula"));
						sesion.setPeli(peli);

						ret.add(sesion);
					}
				}
			}

		} catch (SQLException e) {
			System.out.println("Error con la BBDD - " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Driver no encontrado - " + e.getMessage());
		}

		return ret;
	}
}