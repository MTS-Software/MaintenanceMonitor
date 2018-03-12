package com.maintenancemonitor.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.maintenancemonitor.db.dto.AnlageUserDTO;
import com.maintenancemonitor.util.ConnectionManager;
import com.maintenancemonitor.util.DAOException;

public class AnlageUserJDBCDAO implements AnlageUserDAO {

	private final static String TIMESTAMP_ERROR = "Ein Benutzer hat die Daten gerade verändert.\nBitte öffnen Sie das Fenster erneut oder drücken sie die F5 Taste.";

	private final static String SELECT_ANLAGE_USER = "SELECT * FROM anlage_user where anlageid = ? AND userid = ?";
	private final static String SELECT_ANLAGEN_USER = "SELECT * FROM anlage_user where anlageid = ?";

	private final static String INSERT_ANLAGE_USER = "INSERT INTO anlage_user(anlageid, userid, timestamp, user) VALUES (?, ?, ?, ?)";
	private final static String UPDATE_ANLAGE_USER = "UPDATE anlage_user SET anlageid = ?, userId = ?, timestamp = ?, user = ? WHERE anlageid = ? AND  userid = ?";
	private final static String DELETE_ANLAGE_USER = "DELETE FROM anlage_user WHERE anlageid = ? AND  userid = ?";

	@Override
	public void deleteAnlageUser(AnlageUserDTO anlageUser) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(DELETE_ANLAGE_USER);
			ps.setInt(1, anlageUser.getAnlageId());
			ps.setInt(2, anlageUser.getUserId());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);

		}

	}

	@Override
	public AnlageUserDTO selectAnlageUser(int anlageId, int userId) throws DAOException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		AnlageUserDTO anlageUser;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(SELECT_ANLAGE_USER);
			ps.setInt(1, anlageId);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			rs.next();

			anlageUser = new AnlageUserDTO();
			anlageUser.setAnlageId(new Integer(rs.getInt("anlageid")));
			anlageUser.setUserId(new Integer(rs.getInt("userid")));

			anlageUser.setTimestamp(rs.getTimestamp("timestamp"));
			anlageUser.setUser(rs.getString("user"));

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return anlageUser;
	}

	@Override
	public void insertAnlageUser(AnlageUserDTO anlageUser) throws DAOException {

		PreparedStatement ps;

		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(INSERT_ANLAGE_USER);

			ps.setInt(1, anlageUser.getAnlageId());
			ps.setInt(2, anlageUser.getUserId());
			anlageUser.setTimestamp(new Timestamp(System.currentTimeMillis() / 1000 * 1000));
			ps.setTimestamp(3, anlageUser.getTimestamp());
			ps.setString(4, System.getProperty("user.name"));
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}

	}

	@Override
	public void updateAnlageUser(AnlageUserDTO anlageUser) throws DAOException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000 * 1000);

		try {

			AnlageUserDTO anlageUserDb = selectAnlageUser(anlageUser.getAnlageId(), anlageUser.getUserId());

			if (anlageUserDb.getTimestamp().equals(anlageUser.getTimestamp())) {

				PreparedStatement ps = ConnectionManager.getInstance().getConnection()
						.prepareStatement(UPDATE_ANLAGE_USER);

				ps.setTimestamp(1, timestamp);
				ps.setString(2, System.getProperty("user.name"));
				ps.setInt(3, anlageUser.getAnlageId());
				ps.setInt(4, anlageUser.getUserId());
				ps.executeUpdate();

				// Zeitstempel soll erst beschrieben werden, wenn der Befehl
				// erfolgreich ausgeführt wurde
				anlageUser.setTimestamp(timestamp);

			} else {
				throw new DAOException(TIMESTAMP_ERROR);
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

	}

	@Override
	public List<AnlageUserDTO> selectAnlagenUser(int anlageId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AnlageUserDTO> anlageUserList = new ArrayList<AnlageUserDTO>();

		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(SELECT_ANLAGEN_USER);
			ps.setInt(1, anlageId);

			rs = ps.executeQuery();
			while (rs.next()) {
				AnlageUserDTO anlageUser = new AnlageUserDTO();

				anlageUser.setAnlageId(rs.getInt("anlageId"));
				anlageUser.setUserId(rs.getInt("userId"));

				anlageUser.setTimestamp(rs.getTimestamp("timestamp"));
				anlageUser.setUser(rs.getString("user"));

				anlageUserList.add(anlageUser);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return anlageUserList;
	}

}
