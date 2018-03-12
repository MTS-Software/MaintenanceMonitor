package com.maintenancemonitor.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.maintenancemonitor.db.dto.AnlageDTO;
import com.maintenancemonitor.util.ConnectionManager;
import com.maintenancemonitor.util.DAOException;

public class AnlageJDBCDAO implements AnlageDAO {

	private final static String GET_ANLAGEN = "SELECT * FROM anlage ORDER BY name ASC";
	private final static String UPDATE_MAILSENT = "UPDATE anlage SET mailSent = ? WHERE id = ?";

	@Override
	public List<AnlageDTO> getAnlagen() throws DAOException {

		Statement statement = null;
		ResultSet rs = null;
		Connection con = null;

		List<AnlageDTO> projektList = new ArrayList<AnlageDTO>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(GET_ANLAGEN);

			while (rs.next()) {
				AnlageDTO projekt = new AnlageDTO();
				projekt.setId(rs.getInt("id"));
				projekt.setName(rs.getString("name"));
				projekt.setAuftragNr(rs.getString("auftragNr"));
				projekt.setJahresStueck(rs.getInt("jahresStueck"));
				projekt.setAktuelleStueck(rs.getInt("aktuelleStueck"));
				projekt.setWartungIntervall(rs.getInt("wartungStueckIntervall"));
				projekt.setLastWartungStueckzahl(rs.getInt("lastWartungStueck"));
				projekt.setWartungWarnung(rs.getInt("wartungStueckWarnung"));
				projekt.setWartungFehler(rs.getInt("wartungStueckFehler"));
				projekt.setAbteilungId(rs.getInt("abteilungId"));
				projekt.setStatus(rs.getBoolean("status"));
				projekt.setMailSent(rs.getBoolean("mailsent"));
				projekt.setTimestamp(rs.getTimestamp("timestamp"));
				projekt.setUser(rs.getString("user"));
				projekt.setPanelFormatId(rs.getInt("panelFormatId"));
				projektList.add(projekt);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return projektList;
	}

	@Override
	public void updateMailSent(AnlageDTO anlage) throws DAOException {

		try {

			PreparedStatement ps = ConnectionManager.getInstance().getConnection().prepareStatement(UPDATE_MAILSENT);

			ps.setBoolean(1, anlage.isMailSent());
			ps.setInt(2, anlage.getId());
			ps.executeUpdate();

		}

		catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

	}

}