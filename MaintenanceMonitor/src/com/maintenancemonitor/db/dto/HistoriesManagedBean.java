package com.maintenancemonitor.db.dto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.maintenancemonitor.util.ConnectionManager;
import com.maintenancemonitor.util.DAOException;

@ManagedBean(name = "histories")
@RequestScoped
public class HistoriesManagedBean {

	private final static String GET_HISTORY = "SELECT * FROM history ORDER BY timestamp DESC";

	// resource injection
	// @Resource(lookup = "jdbc/MySQLPool")
	// private DataSource ds;

	public List<History> getHistory() throws DAOException {
		Statement statement = null;
		ResultSet rs = null;
		Connection con = null;

		List<History> abteilungList = new ArrayList<History>();

		try {
			con = ConnectionManager.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(GET_HISTORY);

			while (rs.next()) {
				History abteilung = new History();
				abteilung.setId(new Integer(rs.getInt("id")));
				abteilung.setTimestamp(rs.getTimestamp("timestamp"));
				abteilung.setUser(rs.getString("user"));
				if (!abteilung.getUser().contains("thaler"))
					abteilungList.add(abteilung);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			try {
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException(e);
			}
		}

		return abteilungList;
	}

}
