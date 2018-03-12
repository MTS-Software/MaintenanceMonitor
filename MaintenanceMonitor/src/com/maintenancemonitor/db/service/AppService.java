package com.maintenancemonitor.db.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.maintenancemonitor.db.dao.AbteilungDAO;
import com.maintenancemonitor.db.dao.AnlageDAO;
import com.maintenancemonitor.db.dao.DAOFactory;
import com.maintenancemonitor.db.dao.EDAOType;
import com.maintenancemonitor.db.dao.StationDAO;
import com.maintenancemonitor.db.dao.WartungDAO;
import com.maintenancemonitor.db.dto.AnlageDTO;
import com.maintenancemonitor.db.dto.StationDTO;
import com.maintenancemonitor.db.dto.WartungDTO;
import com.maintenancemonitor.util.DAOException;

@ManagedBean(name = "appservice")
@RequestScoped
public class AppService {

	private AnlageDAO anlageDAO;
	private WartungDAO wartungDAO;
	private AbteilungDAO abteilungDAO;
	private StationDAO stationDAO;

	public AppService() {

		DAOFactory daoFactory;
		daoFactory = new DAOFactory(EDAOType.JDBC);

		anlageDAO = daoFactory.getAnlageDAO();
		wartungDAO = daoFactory.getWartungDAO();

		abteilungDAO = daoFactory.getAbteilungDAO();
		stationDAO = daoFactory.getStationDAO();

	}

	public List<AnlageDTO> getAnlagen() {

		List<AnlageDTO> anlagen = null;

		try {
			anlagen = anlageDAO.getAnlagen();
		} catch (DAOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}

		return anlagen;
	}

	public List<WartungDTO> getAnlagenWartungen() {

		List<WartungDTO> wartungen = null;

		try {
			wartungen = mapWartungAnlage(getAnlagen(), wartungDAO.getAnlagenWartungen());
		} catch (DAOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}

		return wartungen;
	}

	public List<WartungDTO> getAktuelleWartungen() {

		List<WartungDTO> wartungen = new ArrayList<WartungDTO>();

		for (WartungDTO wartung : getGesamtWartungen()) {
			if (wartung.getFaellig() == null) {
				wartungen.add(wartung);
			}
		}

		return wartungen;
	}

	public List<WartungDTO> getOffeneWartungen() {

		List<WartungDTO> wartungen = new ArrayList<WartungDTO>();

		for (WartungDTO wartung : getGesamtWartungen()) {
			if (wartung.getStatus() == 0 || wartung.getStatus() == 1) {
				wartungen.add(wartung);
			}
		}

		return wartungen;
	}

	public List<WartungDTO> getStationWartungen() {

		List<WartungDTO> wartungen = null;
		List<StationDTO> stationen;

		try {

			stationen = mapStationAnlage(stationDAO.getStationen(), anlageDAO.getAnlagen());
			wartungen = mapWartungStation(stationen, wartungDAO.getStationWartungen());

		} catch (DAOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}

		return wartungen;
	}

	public List<WartungDTO> getGesamtWartungen() {

		List<WartungDTO> wartungen = new ArrayList<WartungDTO>();

		wartungen.addAll(getAnlagenWartungen());
		wartungen.addAll(getStationWartungen());

		Collections.sort(wartungen);

		return wartungen;

	}

	private List<WartungDTO> mapWartungAnlage(List<AnlageDTO> anlagen, List<WartungDTO> wartungen) {

		StationDTO station = new StationDTO();
		station.setName("Anlage");

		for (AnlageDTO anlage : anlagen) {
			for (WartungDTO wartung : wartungen) {
				if (wartung.getAnlageId() == anlage.getId())
					wartung.setAnlage(anlage);
				else
					wartung.setStation(station);
			}
		}
		return wartungen;

	}

	private List<WartungDTO> mapWartungStation(List<StationDTO> stationen, List<WartungDTO> wartungen) {

		for (StationDTO station : stationen) {
			for (WartungDTO wartung : wartungen) {
				if (wartung.getStationId() == station.getId()) {
					wartung.setStation(station);
					wartung.setAnlage(station.getAnlage());
				}

			}
		}
		return wartungen;

	}

	private List<StationDTO> mapStationAnlage(List<StationDTO> stationen, List<AnlageDTO> anlagen) {

		for (AnlageDTO anlage : anlagen) {
			for (StationDTO station : stationen) {
				if (station.getAnlageId() == anlage.getId())
					station.setAnlage(anlage);

			}
		}
		return stationen;

	}

}
