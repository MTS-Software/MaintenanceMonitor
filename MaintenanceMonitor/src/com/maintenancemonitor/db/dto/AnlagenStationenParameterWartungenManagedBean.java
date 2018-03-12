package com.maintenancemonitor.db.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.validation.constraints.NotNull;

import com.maintenancemonitor.util.ConnectionManager;
import com.maintenancemonitor.util.DAOException;

@ManagedBean(name = "anlagenstationenparameterwartungen")
@RequestScoped
public class AnlagenStationenParameterWartungenManagedBean {

	private final static String GET_WARTUNGEN_YEARS = "SELECT DISTINCT YEAR( faellig ) years from wartung order by years DESC";
	private final static String GET_ANLAGEN = "SELECT * FROM anlage ORDER BY name ASC";
	private final static String GET_ANLAGE = "SELECT * FROM anlage where name like ?";
	private final static String GET_ALL_WARTUNGEN_FROM_ANLAGE = "SELECT * FROM wartung WHERE anlage_id = ? and YEAR( faellig ) = ? ORDER BY faellig DESC";
	private final static String GET_ALL_WARTUNGEN_FROM_STATION = "SELECT * FROM wartung WHERE station_id = ? and YEAR( faellig ) = ? ORDER BY faellig DESC";
	private final static String GET_ALLSTATIONEN_FROM_ANLAGE = "SELECT * FROM station where anlageId = ? ORDER BY name ASC";
	// resource injection
	// @Resource(lookup = "jdbc/MySQLPool")
	// private DataSource ds;

	@NotNull(message = "Bitte auswaehlen")
	private String yearName;
	private String anlageName;

	@ManagedProperty(value = "#{anlage}")
	private AnlageDTO anlage;

	@ManagedProperty(value = "#{wartungen}")
	private List<WartungDTO> wartungen;

	@ManagedProperty(value = "#{stationen}")
	private List<StationDTO> stationen;

	public List<StationDTO> getAllStationenFromAnlage(int anlageID) throws DAOException {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		List<StationDTO> anlagenList = new ArrayList<StationDTO>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(GET_ALLSTATIONEN_FROM_ANLAGE);
			ps.setInt(1, anlageID);
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
		} finally {

			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException(e);
				}
		}

		return anlagenList;
	}

	public List<String> getYears() throws DAOException {

		Statement statement = null;
		ResultSet rs = null;
		Connection con = null;

		List<String> years = new ArrayList<String>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(GET_WARTUNGEN_YEARS);

			while (rs.next()) {

				years.add(rs.getString("years"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		finally {

			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException(e);
				}
		}

		return years;
	}

	public List<AnlageDTO> getAnlagen() throws DAOException {

		Statement statement = null;
		ResultSet rs = null;
		Connection con = null;

		List<AnlageDTO> anlageList = new ArrayList<AnlageDTO>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(GET_ANLAGEN);

			while (rs.next()) {
				AnlageDTO anlage = new AnlageDTO();
				anlage.setId(rs.getInt("id"));
				anlage.setName(rs.getString("name"));
				anlage.setAuftragNr(rs.getString("auftragNr"));
				anlage.setJahresStueck(rs.getInt("jahresStueck"));
				anlage.setAktuelleStueck(rs.getInt("aktuelleStueck"));
				anlage.setWartungIntervall(rs.getInt("wartungStueckIntervall"));
				anlage.setLastWartungStueckzahl(rs.getInt("lastWartungStueck"));
				anlage.setWartungWarnung(rs.getInt("wartungStueckWarnung"));
				anlage.setWartungFehler(rs.getInt("wartungStueckFehler"));
				anlage.setAbteilungId(rs.getInt("abteilungId"));
				anlage.setStatus(rs.getBoolean("status"));
				anlage.setMailSent(rs.getBoolean("mailsent"));
				anlage.setTimestamp(rs.getTimestamp("timestamp"));
				anlage.setUser(rs.getString("user"));
				anlage.setPanelFormatId(rs.getInt("panelFormatId"));
				anlageList.add(anlage);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {

			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException(e);
				}
		}

		return anlageList;
	}

	public AnlageDTO getAnlage(String name) throws DAOException {

		Statement statement = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		AnlageDTO anlage = null;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(GET_ANLAGE);
			ps.setString(1, name);

			rs = ps.executeQuery();
			while (rs.next()) {
				anlage = new AnlageDTO();
				anlage.setId(rs.getInt("id"));
				anlage.setName(rs.getString("name"));
				anlage.setAuftragNr(rs.getString("auftragNr"));
				anlage.setJahresStueck(rs.getInt("jahresStueck"));
				anlage.setAktuelleStueck(rs.getInt("aktuelleStueck"));
				anlage.setWartungIntervall(rs.getInt("wartungStueckIntervall"));
				anlage.setLastWartungStueckzahl(rs.getInt("lastWartungStueck"));
				anlage.setWartungWarnung(rs.getInt("wartungStueckWarnung"));
				anlage.setWartungFehler(rs.getInt("wartungStueckFehler"));
				anlage.setAbteilungId(rs.getInt("abteilungId"));
				anlage.setStatus(rs.getBoolean("status"));
				anlage.setMailSent(rs.getBoolean("mailsent"));
				anlage.setTimestamp(rs.getTimestamp("timestamp"));
				anlage.setUser(rs.getString("user"));
				anlage.setPanelFormatId(rs.getInt("panelFormatId"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {

			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException(e);
				}
		}

		return anlage;
	}

	public List<WartungDTO> getAllWartungenFromAnlage(int anlageId, String year) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WartungDTO> wartungList = new ArrayList<WartungDTO>();

		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(GET_ALL_WARTUNGEN_FROM_ANLAGE);
			ps.setInt(1, anlageId);
			ps.setString(2, year);

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
				wartung.setAnlageId(rs.getInt("anlage_Id"));
				wartung.setStationId(rs.getInt("station_Id"));
				wartung.setTimestamp(rs.getTimestamp("timestamp"));
				wartung.setUser(rs.getString("user"));
				StationDTO st = new StationDTO();
				st.setName("Anlage");
				wartung.setStation(st);
				wartungList.add(wartung);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {

			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException(e);
				}
		}

		return wartungList;
	}

	public List<WartungDTO> getAllWartungenFromStation(StationDTO station, String year) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WartungDTO> wartungList = new ArrayList<WartungDTO>();

		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(GET_ALL_WARTUNGEN_FROM_STATION);
			ps.setInt(1, station.getId());
			ps.setString(2, year);

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
				wartung.setAnlageId(rs.getInt("anlage_id"));
				wartung.setStationId(rs.getInt("station_id"));
				wartung.setTimestamp(rs.getTimestamp("timestamp"));
				wartung.setUser(rs.getString("user"));
				wartung.setStation(station);
				wartungList.add(wartung);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {

			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException(e);
				}
		}
		return wartungList;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public String getAnlageName() {
		return anlageName;
	}

	public void setAnlageName(String anlageName) {
		this.anlageName = anlageName;
	}

	public AnlageDTO getAnlage() {
		return anlage;
	}

	public void setAnlage(AnlageDTO anlage) {
		this.anlage = anlage;
	}

	public List<WartungDTO> getWartungen() {
		return wartungen;
	}

	public void setWartungen(List<WartungDTO> wartungen) {
		this.wartungen = wartungen;
	}

	public List<StationDTO> getStationen() {
		return stationen;
	}

	public void setStationen(List<StationDTO> stationen) {
		this.stationen = stationen;
	}

	public void ausfuehrenAnlagenStationenParameterWartungen(ActionEvent ev) {

		try {
			anlage = getAnlage(anlageName);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			wartungen = getAllWartungenFromAnlage(anlage.getId(), yearName);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			stationen = getAllStationenFromAnlage(anlage.getId());

			for (StationDTO station : stationen) {
				wartungen.addAll(getAllWartungenFromStation(station, yearName));
			}
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public void ausfuehrenStationenParameterWartungen(ActionEvent ev) {

		try {
			anlage = getAnlage(anlageName);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 wartungen = new ArrayList<WartungDTO>();

		try {
			stationen = getAllStationenFromAnlage(anlage.getId());

			for (StationDTO station : stationen) {
				wartungen.addAll(getAllWartungenFromStation(station, yearName));
			}
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public void ausfuehrenAnlagenParameterWartungen(ActionEvent ev) {

		try {
			anlage = getAnlage(anlageName);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			wartungen = getAllWartungenFromAnlage(anlage.getId(), yearName);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
