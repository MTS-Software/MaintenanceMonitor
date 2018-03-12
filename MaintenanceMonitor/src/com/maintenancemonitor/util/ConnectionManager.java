package com.maintenancemonitor.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static ConnectionManager instance;

	private Connection connection;

	
	public static ConnectionManager getInstance() {
		if (instance == null)
			instance = new ConnectionManager();
		return instance;
	}

	public void closeConnection() throws SQLException {
		connection.close();
	}

	public Connection getConnection() throws SQLException {

		String url;
		String user;
		String password;

		url = "jdbc:mysql://10.176.45.4:3306/MaintenanceManager";
		// url = "jdbc:mysql://localhost:3306/MaintenanceVisualization";

		user = "root";
		password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			if (connection == null || connection.isClosed())
				connection = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}

		return connection;
	}
}
