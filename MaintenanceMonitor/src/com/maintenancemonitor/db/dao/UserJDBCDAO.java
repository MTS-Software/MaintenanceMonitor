package com.maintenancemonitor.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.maintenancemonitor.db.dto.UserDTO;
import com.maintenancemonitor.util.ConnectionManager;
import com.maintenancemonitor.util.DAOException;

public class UserJDBCDAO implements UserDAO {

	private final static String TIMESTAMP_ERROR = "Ein Benutzer hat die Daten gerade verändert.\nBitte öffnen Sie das Fenster erneut oder drücken sie die F5 Taste.";

	private final static String GET_ALL_USER = "SELECT * FROM user ORDER BY name ASC";
	private final static String SELECT_USER = "SELECT * FROM user where id = ?";
	private final static String INSERT_USER = "INSERT INTO user(name, mail, timestamp, user) VALUES (?, ?, ?, ?)";
	private final static String UPDATE_USER = "UPDATE user SET name = ?, mail = ?, timestamp = ?, user = ? WHERE id = ?";
	private final static String DELETE_USER = "DELETE FROM user WHERE id= ?";

	@Override
	public void deleteUser(UserDTO user) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(DELETE_USER);
			ps.setInt(1, user.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);

		}

	}

	@Override
	public UserDTO selectUser(int userId) throws DAOException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		UserDTO user;

		try {
			con = ConnectionManager.getInstance().getConnection();

			ps = con.prepareStatement(SELECT_USER);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			rs.next();

			user = new UserDTO();
			user.setId(new Integer(rs.getInt("id")));
			user.setName(rs.getString("name"));
			user.setMail(rs.getString("mail"));
			user.setTimestamp(rs.getTimestamp("timestamp"));
			user.setUser(rs.getString("user"));

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return user;
	}

	@Override
	public List<UserDTO> getAllUser() throws DAOException {
		Statement statement = null;
		ResultSet rs = null;
		Connection con = null;

		List<UserDTO> abteilungList = new ArrayList<UserDTO>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(GET_ALL_USER);

			while (rs.next()) {
				UserDTO user = new UserDTO();
				user.setId(new Integer(rs.getInt("id")));
				user.setName(rs.getString("name"));
				user.setMail(rs.getString("mail"));
				user.setTimestamp(rs.getTimestamp("timestamp"));
				user.setUser(rs.getString("user"));
				abteilungList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return abteilungList;
	}

	@Override
	public void insertUser(UserDTO user) throws DAOException {

		PreparedStatement ps;

		try {
			ps = ConnectionManager.getInstance().getConnection().prepareStatement(INSERT_USER);

			ps.setString(1, user.getName());
			ps.setString(2, user.getMail());
			user.setTimestamp(new Timestamp(System.currentTimeMillis() / 1000 * 1000));
			ps.setTimestamp(3, user.getTimestamp());
			ps.setString(4, System.getProperty("user.name"));
			ps.executeUpdate();

			user.setId(selectLastID());

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
	public void updateUser(UserDTO user) throws DAOException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000 * 1000);

		try {

			UserDTO userDb = selectUser(user.getId());

			if (userDb.getTimestamp().equals(user.getTimestamp())) {

				PreparedStatement ps = ConnectionManager.getInstance().getConnection().prepareStatement(UPDATE_USER);

				ps.setString(1, user.getName());
				ps.setString(2, user.getMail());
				ps.setTimestamp(3, timestamp);
				ps.setString(4, System.getProperty("user.name"));
				ps.setInt(5, user.getId());
				ps.executeUpdate();

				// Zeitstempel soll erst beschrieben werden, wenn der Befehl
				// erfolgreich ausgeführt wurde
				user.setTimestamp(timestamp);

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
