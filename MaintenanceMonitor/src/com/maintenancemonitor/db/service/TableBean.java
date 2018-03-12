package com.maintenancemonitor.db.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

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

@ManagedBean(name = "tableBean")
@RequestScoped
public class TableBean implements Serializable {

	private List<AnlageDTO> anlagen;

	private AnlageDAO anlageDAO;
	private WartungDAO wartungDAO;
	private AbteilungDAO abteilungDAO;
	private StationDAO stationDAO;

	public TableBean() {

		DAOFactory daoFactory;
		daoFactory = new DAOFactory(EDAOType.JDBC);

		anlageDAO = daoFactory.getAnlageDAO();
		wartungDAO = daoFactory.getWartungDAO();

		abteilungDAO = daoFactory.getAbteilungDAO();
		stationDAO = daoFactory.getStationDAO();

		anlagen = getAnlagenSubTable();

	}

	public List<AnlageDTO> getAnlagenSubTable() {

		List<AnlageDTO> anlagen = null;

		List<StationDTO> stationen = null;

		try {
			anlagen = mapAnlageWartung(anlageDAO.getAnlagen(), wartungDAO.getAllWartungen());
			stationen = getStationWartungen();

			for (AnlageDTO anlage : anlagen) {

				for (StationDTO station : stationen) {
					if (anlage.getId() == station.getAnlageId()) {
						anlage.getWartungen().addAll(station.getWartungList());
					}

				}

			}

		} catch (DAOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
		return anlagen;
	}

	public List<StationDTO> getStationWartungen() {

		List<StationDTO> stationen = null;

		try {

			stationen = mapStationAnlage(stationDAO.getStationen(), anlageDAO.getAnlagen());
			stationen = mapStationWartung(stationen, wartungDAO.getStationWartungen());

		} catch (DAOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}

		return stationen;
	}

	private List<StationDTO> mapStationWartung(List<StationDTO> stationen, List<WartungDTO> wartungen) {

		List<WartungDTO> wartungenList = new ArrayList<WartungDTO>();

		for (StationDTO station : stationen) {
			wartungenList = new ArrayList<WartungDTO>();
			for (WartungDTO wartung : wartungen) {
				if (wartung.getStationId() == station.getId()) {
					wartung.setStation(station);
					wartungenList.add(wartung);
				}

			}
			station.setWartungList(wartungenList);
		}
		return stationen;

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

	private List<AnlageDTO> mapAnlageWartung(List<AnlageDTO> anlagen, List<WartungDTO> wartungen) {

		List<WartungDTO> wartungenList = new ArrayList<WartungDTO>();

		StationDTO station = new StationDTO();
		station.setName("Anlage");

		for (AnlageDTO anlage : anlagen) {
			wartungenList = new ArrayList<WartungDTO>();
			for (WartungDTO wartung : wartungen) {
				if (wartung.getAnlageId() == anlage.getId()) {
					wartung.setAnlage(anlage);
					wartungenList.add(wartung);
				} else
					wartung.setStation(station);
			}
			anlage.setWartungen(wartungenList);
		}
		return anlagen;

	}

	public List<AnlageDTO> getAnlagen() {
		return anlagen;
	}

	public void setAnlagen(List<AnlageDTO> anlagen) {
		this.anlagen = anlagen;
	}

}
