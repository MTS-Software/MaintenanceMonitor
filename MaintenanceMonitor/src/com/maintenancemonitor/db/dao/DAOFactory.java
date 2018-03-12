package com.maintenancemonitor.db.dao;

public class DAOFactory {

	private AnlageDAO anlageDAO;
	private WartungDAO wartungDAO;
	private AbteilungDAO abteilungDAO;
	private StationDAO stationDAO;
	private UserDAO userDAO;
	private AnlageUserDAO anlageUserDAO;

	public DAOFactory(EDAOType eDAOType) {

		if (eDAOType == EDAOType.JDBC) {

			anlageDAO = new AnlageJDBCDAO();
			wartungDAO = new WartungJDBCDAO();
			abteilungDAO = new AbteilungJDBCDAO();
			stationDAO = new StationJDBCDAO();
			userDAO = new UserJDBCDAO();
			anlageUserDAO = new AnlageUserJDBCDAO();
		}

		if (eDAOType == EDAOType.HIBERNATE) {

		}

		if (eDAOType == EDAOType.MEMORY) {

		}

	}

	public AbteilungDAO getAbteilungDAO() {
		return abteilungDAO;
	}

	public AnlageDAO getAnlageDAO() {

		return anlageDAO;
	}

	public StationDAO getStationDAO() {
		return stationDAO;
	}

	public WartungDAO getWartungDAO() {
		return wartungDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public AnlageUserDAO getAnlageUserDAO() {
		return anlageUserDAO;
	}

}
