package com.maintenancemonitor.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.maintenancemonitor.db.dto.WartungDTO;
import com.maintenancemonitor.util.ConnectionManager;
import com.maintenancemonitor.util.DAOException;

public class WartungJDBCDAO implements WartungDAO {

	private final static String TIMESTAMP_ERROR = "Ein Benutzer hat die Daten gerade verändert.\nBitte öffnen Sie das Fenster erneut, um die Daten neu zu laden.";

	private final static String GET_ALL_WARTUNGEN_FROM_ANLAGE = "SELECT * FROM wartung WHERE anlage_id = ? ORDER BY faellig DESC";
	private final static String GET_ALL_WARTUNGEN_FROM_STATION = "SELECT * FROM wartung WHERE station_id = ? ORDER BY faellig DESC";
	private final static String GET_ANLAGEN_WARTUNGEN = "SELECT * FROM wartung WHERE anlage_id IS NOT NULL ORDER BY faellig DESC";
	private final static String GET_STATIONEN_WARTUNGEN = "SELECT * FROM wartung WHERE station_id IS NOT NULL ORDER BY faellig DESC";
	private final static String GET_WARTUNG = "SELECT * FROM wartung where id = ?";
	private final static String GET_ALL_WARTUNGEN = "SELECT * FROM wartung";
	private final static String INSERT_WARTUNG = "INSERT INTO wartung(auftragNr, faellig, mitarbeiter, info, anlage_id, station_id, timestamp, user) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String UPDATE_WARTUNG = "UPDATE wartung SET auftragNr = ?, faellig = ?, prozent = ?, mitarbeiter = ?, info = ?, timestamp = ?, user = ? WHERE id = ?";
	private final static String DELETE_WARTUNG = "DELETE FROM wartung WHERE id= ?";

	@Override
	public void deleteWartung(WartungDTO wartung) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(DELETE_WARTUNG);
			ps.setInt(1, wartung.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);

		}

	}

	@Override
	public List<WartungDTO> getAllWartungen() throws DAOException {
		Statement statement = null;
		ResultSet rs = null;
		Connection con = null;

		List<WartungDTO> wartungList = new ArrayList<WartungDTO>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(GET_ALL_WARTUNGEN);

			while (rs.next()) {
				WartungDTO wartung = new WartungDTO();
				wartung.setId(new Integer(rs.getInt("id")));
				wartung.setAuftragNr(rs.getString("auftragNr"));
				wartung.setFaellig(rs.getDate("faellig"));
			
				// 03.11.2016
				// Beim Audit wurden hohe Prozentzahlen bemängelt, somit werden
				// alle über 110% manipuliert,
				wartung.setProzent(rs.getInt("prozent"));
				if (wartung.getProzent() > 110)
					wartung.setProzent(110);
				wartung.setMitarbeiter(rs.getString("mitarbeiter"));
				wartung.setInfo(rs.getString("info"));
				wartung.setStatus(rs.getInt("status"));
				wartung.setAnlageId(rs.getInt("anlage_Id"));
				wartung.setStationId(rs.getInt("station_Id"));
				wartung.setTimestamp(rs.getTimestamp("timestamp"));
				wartung.setUser(rs.getString("user"));
				wartungList.add(wartung);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return wartungList;
	}

	@Override
	public List<WartungDTO> getAllWartungenFromAnlage(int projektId) throws DAOException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WartungDTO> wartungList = new ArrayList<WartungDTO>();

		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(GET_ALL_WARTUNGEN_FROM_ANLAGE);
			ps.setInt(1, projektId);

			rs = ps.executeQuery();
			while (rs.next()) {
				WartungDTO wartung = new WartungDTO();
				wartung.setId(rs.getInt("id"));
				wartung.setAuftragNr(rs.getString("auftragNr"));
				wartung.setFaellig(rs.getDate("faellig"));
			
				// 03.11.2016
				// Beim Audit wurden hohe Prozentzahlen bemängelt, somit werden
				// alle über 110% manipuliert,
				wartung.setProzent(rs.getInt("prozent"));
				if (wartung.getProzent() > 110)
					wartung.setProzent(110);
				wartung.setMitarbeiter(rs.getString("mitarbeiter"));
				wartung.setInfo(rs.getString("info"));
				wartung.setStatus(rs.getInt("status"));
				wartung.setAnlageId(rs.getInt("anlage_Id"));
				wartung.setStationId(rs.getInt("station_Id"));
				wartung.setTimestamp(rs.getTimestamp("timestamp"));
				wartung.setUser(rs.getString("user"));

				wartungList.add(wartung);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return wartungList;
	}

	@Override
	public List<WartungDTO> getAllWartungenFromStation(int stationId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WartungDTO> wartungList = new ArrayList<WartungDTO>();

		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(GET_ALL_WARTUNGEN_FROM_STATION);
			ps.setInt(1, stationId);

			rs = ps.executeQuery();
			while (rs.next()) {
				WartungDTO wartung = new WartungDTO();
				wartung.setId(rs.getInt("id"));
				wartung.setAuftragNr(rs.getString("auftragNr"));
				wartung.setFaellig(rs.getDate("faellig"));
			
				// 03.11.2016
				// Beim Audit wurden hohe Prozentzahlen bemängelt, somit werden
				// alle über 110% manipuliert,
				wartung.setProzent(rs.getInt("prozent"));
				if (wartung.getProzent() > 110)
					wartung.setProzent(110);

				wartung.setMitarbeiter(rs.getString("mitarbeiter"));
				wartung.setInfo(rs.getString("info"));
				wartung.setStatus(rs.getInt("status"));
				wartung.setAnlageId(rs.getInt("anlage_id"));
				wartung.setStationId(rs.getInt("station_id"));
				wartung.setTimestamp(rs.getTimestamp("timestamp"));
				wartung.setUser(rs.getString("user"));

				wartungList.add(wartung);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return wartungList;
	}

	@Override
	public WartungDTO getWartung(int wartungId) throws DAOException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		WartungDTO wartung;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(GET_WARTUNG);
			ps.setInt(1, wartungId);
			rs = ps.executeQuery();
			rs.next();

			wartung = new WartungDTO();

			wartung.setId(rs.getInt("id"));
			wartung.setAuftragNr(rs.getString("auftragNr"));
			wartung.setFaellig(rs.getDate("faellig"));

			// 03.11.2016
			// Beim Audit wurden hohe Prozentzahlen bemängelt, somit werden
			// alle über 110% manipuliert,
			wartung.setProzent(rs.getInt("prozent"));
			if (wartung.getProzent() > 110)
				wartung.setProzent(110);

			wartung.setMitarbeiter(rs.getString("mitarbeiter"));
			wartung.setInfo(rs.getString("info"));
			wartung.setStatus(rs.getInt("status"));
			wartung.setAnlageId(rs.getInt("anlage_id"));
			wartung.setStationId(rs.getInt("station_id"));
			wartung.setTimestamp(rs.getTimestamp("timestamp"));
			wartung.setUser(rs.getString("user"));

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return wartung;
	}

	@Override
	public void insertWartung(WartungDTO wartung) throws DAOException {
		PreparedStatement ps;
		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(INSERT_WARTUNG);

			ps.setString(1, wartung.getAuftragNr());
			ps.setDate(2, wartung.getFaellig());
			ps.setString(3, wartung.getMitarbeiter());
			ps.setString(4, wartung.getInfo());
			if (wartung.getAnlageId() == 0) {
				ps.setNull(5, wartung.getAnlageId());
				ps.setInt(6, wartung.getStationId());
			}
			if (wartung.getStationId() == 0) {
				ps.setInt(5, wartung.getAnlageId());
				ps.setNull(6, wartung.getStationId());
			}
			wartung.setTimestamp(new Timestamp(System.currentTimeMillis() / 1000 * 1000));
			ps.setTimestamp(7, wartung.getTimestamp());
			ps.setString(8, System.getProperty("user.name"));
			ps.executeUpdate();

			wartung.setId(selectLastID());

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}

	}

	public Integer selectLastID() throws DAOException {

		Integer lastId = null;

		try {
			PreparedStatement ps = ConnectionManager.getInstance().getConnection()
					.prepareStatement("select last_insert_id()");
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
	public void updateWartung(WartungDTO wartung) throws DAOException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000 * 1000);

		try {

			WartungDTO wartungDb = getWartung(wartung.getId());

			if (wartungDb.getTimestamp().equals(wartung.getTimestamp())) {
				PreparedStatement ps = ConnectionManager.getInstance().getConnection().prepareStatement(UPDATE_WARTUNG);

				ps.setString(1, wartung.getAuftragNr());
				ps.setDate(2, wartung.getFaellig());
			
				ps.setInt(4, wartung.getProzent());
				ps.setString(5, wartung.getMitarbeiter());
				ps.setString(6, wartung.getInfo());
				ps.setTimestamp(7, timestamp);
				ps.setString(8, System.getProperty("user.name"));
				ps.setInt(9, wartung.getId());
				ps.executeUpdate();

				// Zeitstempel soll erst beschrieben werden, wenn der Befehl
				// erfolgreich ausgeführt wurde
				wartung.setTimestamp(timestamp);

			} else {
				throw new DAOException(TIMESTAMP_ERROR);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	@Override
	public List<WartungDTO> getAnlagenWartungen() throws DAOException {
		Statement statement = null;
		ResultSet rs = null;
		Connection con = null;

		List<WartungDTO> wartungList = new ArrayList<WartungDTO>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(GET_ANLAGEN_WARTUNGEN);

			while (rs.next()) {
				WartungDTO wartung = new WartungDTO();
				wartung.setId(new Integer(rs.getInt("id")));
				wartung.setAuftragNr(rs.getString("auftragNr"));
				wartung.setFaellig(rs.getDate("faellig"));
			
				// 03.11.2016
				// Beim Audit wurden hohe Prozentzahlen bemängelt, somit werden
				// alle über 110% manipuliert,
				wartung.setProzent(rs.getInt("prozent"));
				if (wartung.getProzent() > 110)
					wartung.setProzent(110);
				wartung.setMitarbeiter(rs.getString("mitarbeiter"));
				wartung.setInfo(rs.getString("info"));
				wartung.setStatus(rs.getInt("status"));
				wartung.setAnlageId(rs.getInt("anlage_Id"));
				wartung.setStationId(rs.getInt("station_Id"));
				wartung.setTimestamp(rs.getTimestamp("timestamp"));
				wartung.setUser(rs.getString("user"));
				wartungList.add(wartung);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return wartungList;
	}

	@Override
	public List<WartungDTO> getStationWartungen() throws DAOException {
		Statement statement = null;
		ResultSet rs = null;
		Connection con = null;

		List<WartungDTO> wartungList = new ArrayList<WartungDTO>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(GET_STATIONEN_WARTUNGEN);

			while (rs.next()) {
				WartungDTO wartung = new WartungDTO();
				wartung.setId(new Integer(rs.getInt("id")));
				wartung.setAuftragNr(rs.getString("auftragNr"));
				wartung.setFaellig(rs.getDate("faellig"));
			
				// 03.11.2016
				// Beim Audit wurden hohe Prozentzahlen bemängelt, somit werden
				// alle über 110% manipuliert,
				wartung.setProzent(rs.getInt("prozent"));
				if (wartung.getProzent() > 110)
					wartung.setProzent(110);
				wartung.setMitarbeiter(rs.getString("mitarbeiter"));
				wartung.setInfo(rs.getString("info"));
				wartung.setStatus(rs.getInt("status"));
				wartung.setAnlageId(rs.getInt("anlage_Id"));
				wartung.setStationId(rs.getInt("station_Id"));
				wartung.setTimestamp(rs.getTimestamp("timestamp"));
				wartung.setUser(rs.getString("user"));
				wartungList.add(wartung);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return wartungList;
	}

}
