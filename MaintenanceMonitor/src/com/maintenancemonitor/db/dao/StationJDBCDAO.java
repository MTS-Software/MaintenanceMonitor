package com.maintenancemonitor.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.maintenancemonitor.db.dto.AnlageDTO;
import com.maintenancemonitor.db.dto.StationDTO;
import com.maintenancemonitor.db.dto.WartungDTO;
import com.maintenancemonitor.util.ConnectionManager;
import com.maintenancemonitor.util.DAOException;

public class StationJDBCDAO implements StationDAO {

	private final static String TIMESTAMP_ERROR = "Ein Benutzer hat die Daten gerade verändert.\nBitte öffnen Sie das Fenster erneut oder drücken sie die F5 Taste.";

	private final static String GET_ALLSTATIONEN_FROM_ANLAGE = "SELECT * FROM station where anlageId = ? ORDER BY name ASC";
	private final static String GET_STATION = "SELECT * FROM station WHERE id = ?";
	private final static String UPDATE_STATION = "UPDATE station SET name = ?, auftragNr = ?, wartungIntervall = ?, lastWartungStueck = ?, wartungWarnung = ?, wartungFehler = ?, auswertung = ?, lastWartungId = ?, timestamp = ?, user = ?, status = ? WHERE id = ?";
	private final static String UPDATE_STATIONSTATUS = "UPDATE station SET status = ? WHERE id = ?";
	private final static String INSERT_STATION = "INSERT INTO station(name, auftragNr, wartungStueckIntervall, lastWartungStueck, wartungStueckWarnung, wartungStueckFehler, auswertung, timestamp, user, status, anlageId, panelFormatId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String DELETE_STATION = "DELETE FROM station WHERE id= ?";

	private final static String GET_STATIONEN = "SELECT * FROM station";

	private final static String GET_WARTUNGENSTATION_DATE = "SELECT * FROM wartung WHERE (faellig BETWEEN ? AND ?) AND (station_id = ?) ";

	private final static String UPDATE_MAILSENT = "UPDATE station SET mailSent = ? WHERE id = ?";

	@Override
	public void deleteStation(StationDTO station) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(DELETE_STATION);
			ps.setInt(1, station.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);

		}

	}

	@Override
	public List<StationDTO> getAllStationenFromAnlage(AnlageDTO anlage) throws DAOException {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		List<StationDTO> anlagenList = new ArrayList<StationDTO>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(GET_ALLSTATIONEN_FROM_ANLAGE);
			ps.setInt(1, anlage.getId());
			rs = ps.executeQuery();

			while (rs.next()) {
				StationDTO station = new StationDTO();
				station.setId(rs.getInt("id"));
				station.setName(rs.getString("name"));
				station.setAuftragNr(rs.getString("auftragNr"));
				station.setWartungIntervall(rs.getInt("wartungStueckIntervall"));
				station.setLastWartungStueckzahl(rs.getInt("lastWartungStueck"));
				station.setWartungWarnung(rs.getInt("wartungStueckWarnung"));
				station.setWartungFehler(rs.getInt("wartungStueckFehler"));
				station.setAuswertung(rs.getBoolean("auswertung"));

				station.setStatus(rs.getBoolean("status"));
				station.setTimestamp(rs.getTimestamp("timestamp"));
				station.setUser(rs.getString("user"));
				station.setPanelFormatId(rs.getInt("panelFormatId"));
				station.setAnlageId(rs.getInt("anlageId"));

				anlagenList.add(station);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return anlagenList;
	}

	@Override
	public StationDTO getStation(int projektId) throws DAOException {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		StationDTO station;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(GET_STATION);
			ps.setInt(1, projektId);
			rs = ps.executeQuery();
			rs.next();

			station = new StationDTO();
			station.setId(rs.getInt("id"));
			station.setName(rs.getString("name"));
			station.setAuftragNr(rs.getString("auftragNr"));
			station.setWartungIntervall(rs.getInt("wartungStueckIntervall"));
			station.setLastWartungStueckzahl(rs.getInt("lastWartungStueck"));
			station.setWartungWarnung(rs.getInt("wartungStueckWarnung"));
			station.setWartungFehler(rs.getInt("wartungStueckFehler"));
			station.setAuswertung(rs.getBoolean("auswertung"));
			station.setTpm(rs.getBoolean("tpm"));

			station.setStatus(rs.getBoolean("status"));
			station.setMailSent(rs.getBoolean("mailSent"));
			station.setTimestamp(rs.getTimestamp("timestamp"));
			station.setUser(rs.getString("user"));
			station.setPanelFormatId(rs.getInt("panelFormatId"));
			station.setAnlageId(rs.getInt("anlageId"));

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return station;
	}

	@Override
	public List<WartungDTO> getWartungenStationDate(StationDTO station, Date start, Date end) throws DAOException {
		ResultSet rs = null;
		PreparedStatement ps = null;

		List<WartungDTO> wartungList = new ArrayList<WartungDTO>();

		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(GET_WARTUNGENSTATION_DATE);

			ps.setDate(1, new java.sql.Date(start.getTime()));
			ps.setDate(2, new java.sql.Date(end.getTime()));
			ps.setInt(3, station.getId());

			rs = ps.executeQuery();

			while (rs.next()) {
				WartungDTO wartung = new WartungDTO();
				wartung.setId(rs.getInt("id"));
				wartung.setAuftragNr(rs.getString("auftragNr"));
				wartung.setFaellig(rs.getDate("faellig"));
				wartung.setProzent(rs.getInt("prozent"));
				wartung.setMitarbeiter(rs.getString("mitarbeiter"));
				wartung.setInfo(rs.getString("info"));
				wartung.setTimestamp(rs.getTimestamp("timestamp"));
				wartung.setUser(rs.getString("user"));
				wartung.setAnlageId(rs.getInt("anlage_id"));
				wartung.setStationId(rs.getInt("station_id"));

				station = getStation(wartung.getStationId());
				wartung.setStation(station);

				if (station.isAuswertung())
					wartungList.add(wartung);

			}

		} catch (SQLException e) {

			throw new DAOException(e);
		}

		return wartungList;
	}

	@Override
	public void insertStation(StationDTO station) throws DAOException {
		PreparedStatement ps;
		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(INSERT_STATION);

			ps.setString(1, station.getName());
			ps.setString(2, station.getAuftragNr());
			ps.setInt(3, station.getWartungIntervall());
			ps.setInt(4, station.getLastWartungStueckzahl());
			ps.setInt(5, station.getWartungWarnung());
			ps.setInt(6, station.getWartungFehler());
			ps.setBoolean(7, station.isAuswertung());
			station.setTimestamp(new Timestamp(System.currentTimeMillis() / 1000 * 1000));
			ps.setTimestamp(8, station.getTimestamp());
			ps.setString(9, System.getProperty("user.name"));
			// ps.setInt(10, station.getLastWartungId());
			ps.setBoolean(10, station.isStatus());
			ps.setInt(11, station.getAnlageId());
			ps.setInt(12, station.getPanelFormatId());

			ps.executeUpdate();

			station.setId(selectLastID());

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
	public void updateStation(StationDTO station) throws DAOException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000 * 1000);

		try {
			StationDTO projektDb = getStation(station.getId());

			if (projektDb.getTimestamp().equals(station.getTimestamp())) {
				PreparedStatement ps = ConnectionManager.getInstance().getConnection().prepareStatement(UPDATE_STATION);

				ps.setString(1, station.getName());
				ps.setString(2, station.getAuftragNr());
				ps.setInt(3, station.getWartungIntervall());
				ps.setInt(4, station.getLastWartungStueckzahl());
				ps.setInt(5, station.getWartungWarnung());
				ps.setInt(6, station.getWartungFehler());
				ps.setBoolean(7, station.isAuswertung());

				ps.setTimestamp(9, timestamp);
				ps.setString(10, System.getProperty("user.name"));
				ps.setBoolean(11, station.isStatus());
				ps.setInt(12, station.getId());

				ps.executeUpdate();

				// Zeitstempel soll erst beschrieben werden, wenn der Befehl erfolgreich
				// ausgeführt wurde
				station.setTimestamp(timestamp);

			} else
				throw new DAOException(TIMESTAMP_ERROR);
		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	@Override
	public void updateStationStatus(StationDTO station) throws DAOException {
		try {
			PreparedStatement ps = ConnectionManager.getInstance().getConnection()
					.prepareStatement(UPDATE_STATIONSTATUS);

			ps.setBoolean(1, station.isStatus());
			ps.setInt(2, station.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	@Override
	public List<StationDTO> getStationen() throws DAOException {
		ResultSet rs = null;
		Connection con = null;
		Statement statement = null;

		List<StationDTO> anlagenList = new ArrayList<StationDTO>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(GET_STATIONEN);

			while (rs.next()) {
				StationDTO station = new StationDTO();
				station.setId(rs.getInt("id"));
				station.setName(rs.getString("name"));
				station.setAuftragNr(rs.getString("auftragNr"));
				station.setWartungIntervall(rs.getInt("wartungStueckIntervall"));
				station.setLastWartungStueckzahl(rs.getInt("lastWartungStueck"));
				station.setWartungWarnung(rs.getInt("wartungStueckWarnung"));
				station.setWartungFehler(rs.getInt("wartungStueckFehler"));
				station.setAuswertung(rs.getBoolean("auswertung"));
				station.setStatus(rs.getBoolean("status"));
				station.setTpm(rs.getBoolean("tpm"));
				station.setMailSent(rs.getBoolean("mailSent"));
				station.setTimestamp(rs.getTimestamp("timestamp"));
				station.setUser(rs.getString("user"));
				station.setPanelFormatId(rs.getInt("panelFormatId"));
				station.setAnlageId(rs.getInt("anlageId"));

				station.setWartungArt(rs.getInt("wartungArt"));
				station.setWartungStueckIntervall(rs.getInt("wartungStueckIntervall"));

				station.setWartungDateIntervall(rs.getInt("wartungDateIntervall"));
				station.setIntervallDateUnit(rs.getInt("intervallDateUnit"));
				station.setCreateDate(rs.getDate("createDate"));
				station.setWarnungDateUnit(rs.getInt("warnungDateUnit"));
				station.setWartungDateWarnung(rs.getInt("wartungDateWarnung"));

				station.setLastWartungStueckzahl(rs.getInt("lastWartungStueck"));
				station.setLastWartungDate(rs.getDate("lastWartungDate"));

				station.setLastWartungStueckzahl(rs.getInt("lastWartungStueck"));
				station.setLastWartungDate(rs.getDate("lastWartungDate"));

				anlagenList.add(station);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return anlagenList;
	}

	@Override
	public void updateMailSent(StationDTO station) throws DAOException {

		try {

			PreparedStatement ps = ConnectionManager.getInstance().getConnection().prepareStatement(UPDATE_MAILSENT);

			ps.setBoolean(1, station.isMailSent());
			ps.setInt(2, station.getId());
			ps.executeUpdate();

		}

		catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

	}

}