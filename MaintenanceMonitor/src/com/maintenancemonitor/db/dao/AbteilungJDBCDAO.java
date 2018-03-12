package com.maintenancemonitor.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.maintenancemonitor.db.dto.AbteilungDTO;
import com.maintenancemonitor.util.ConnectionManager;
import com.maintenancemonitor.util.DAOException;

public class AbteilungJDBCDAO implements AbteilungDAO {

	private final static String TIMESTAMP_ERROR = "Ein Benutzer hat die Daten gerade verändert.\nBitte öffnen Sie das Fenster erneut oder drücken sie die F5 Taste.";

	private final static String GET_ALL_ABTEILUNG = "SELECT * FROM abteilung ORDER BY name ASC";
	private final static String GET_ABTEILUNG = "SELECT * FROM abteilung where id = ?";
	private final static String INSERT_ABTEILUNG = "INSERT INTO abteilung(name, timestamp, user) VALUES (?, ?, ?)";
	private final static String UPDATE_ABTEILUNG = "UPDATE abteilung SET name = ?, timestamp = ?, user = ? WHERE id = ?";
	private final static String DELETE_ABTEILUNG = "DELETE FROM abteilung WHERE id= ?";

	@Override
	public void deleteAbteilung(AbteilungDTO abteilung) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(DELETE_ABTEILUNG);
			ps.setInt(1, abteilung.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);

		}

	}

	@Override
	public AbteilungDTO getAbteilung(int wartungId) throws DAOException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		AbteilungDTO abteilung;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(GET_ABTEILUNG);
			ps.setInt(1, wartungId);
			rs = ps.executeQuery();
			rs.next();

			abteilung = new AbteilungDTO();
			abteilung.setId(new Integer(rs.getInt("id")));
			abteilung.setName(rs.getString("name"));
			abteilung.setTimestamp(rs.getTimestamp("timestamp"));
			abteilung.setUser(rs.getString("user"));

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return abteilung;
	}

	@Override
	public List<AbteilungDTO> getAllAbteilung() throws DAOException {
		Statement statement = null;
		ResultSet rs = null;
		Connection con = null;

		List<AbteilungDTO> abteilungList = new ArrayList<AbteilungDTO>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			System.out.println(con.getCatalog());

			statement = con.createStatement();
			rs = statement.executeQuery(GET_ALL_ABTEILUNG);

			while (rs.next()) {
				AbteilungDTO abteilung = new AbteilungDTO();
				abteilung.setId(new Integer(rs.getInt("id")));
				abteilung.setName(rs.getString("name"));
				abteilung.setTimestamp(rs.getTimestamp("timestamp"));
				abteilung.setUser(rs.getString("user"));
				abteilungList.add(abteilung);
			}

			System.out.println(abteilungList);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return abteilungList;
	}

	@Override
	public void insertAbteilung(AbteilungDTO abteilung) throws DAOException {

		PreparedStatement ps;

		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(INSERT_ABTEILUNG);

			ps.setString(1, abteilung.getName());
			abteilung.setTimestamp(new Timestamp(System.currentTimeMillis() / 1000 * 1000));
			ps.setTimestamp(2, abteilung.getTimestamp());
			ps.setString(3, System.getProperty("user.name"));
			ps.executeUpdate();

			abteilung.setId(selectLastID());

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}

	}

	public Integer selectLastID() throws DAOException {

		Integer lastId = null;

		try {
			PreparedStatement ps = ConnectionManager.getInstance().getConnection().prepareStatement("select last_insert_id()");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				lastId = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return lastId;
	}

	@Override
	public void updateAbteilung(AbteilungDTO abteilung) throws DAOException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000 * 1000);

		try {

			AbteilungDTO abteilungDb = getAbteilung(abteilung.getId());

			if (abteilungDb.getTimestamp().equals(abteilung.getTimestamp())) {

				PreparedStatement ps = ConnectionManager.getInstance().getConnection().prepareStatement(UPDATE_ABTEILUNG);

				ps.setString(1, abteilung.getName());
				ps.setTimestamp(2, timestamp);
				ps.setString(3, System.getProperty("user.name"));
				ps.setInt(4, abteilung.getId());
				ps.executeUpdate();

				// Zeitstempel soll erst beschrieben werden, wenn der Befehl
				// erfolgreich ausgeführt wurde
				abteilung.setTimestamp(timestamp);

			} else {
				throw new DAOException(TIMESTAMP_ERROR);
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

	}

}
