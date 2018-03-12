package com.maintenancemonitor.db.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.maintenancemonitor.db.dao.AnlageDAO;
import com.maintenancemonitor.db.dao.DAOFactory;
import com.maintenancemonitor.db.dao.EDAOType;
import com.maintenancemonitor.db.dao.StationDAO;
import com.maintenancemonitor.db.dao.WartungDAO;
import com.maintenancemonitor.db.dto.AnlageDTO;
import com.maintenancemonitor.db.dto.StationDTO;
import com.maintenancemonitor.db.dto.WartungDTO;
import com.maintenancemonitor.util.DAOException;

@ManagedBean(name = "filterTableBean")
@RequestScoped
public class FilterTableBean implements Serializable {

	private AnlageDAO anlageDAO;
	private WartungDAO wartungDAO;
	private StationDAO stationDAO;

	private String anlageName;

	private List<AnlageDTO> anlagen;

	@ManagedProperty(value = "#{filteredWartungen}")
	private WartungenManagedBean wartungFilter;

	public FilterTableBean() {

		DAOFactory daoFactory;
		daoFactory = new DAOFactory(EDAOType.JDBC);

		anlageDAO = daoFactory.getAnlageDAO();
		wartungDAO = daoFactory.getWartungDAO();
		stationDAO = daoFactory.getStationDAO();

		anlagen = getAnlagen();

	}

	public void ausfuehren(ActionEvent ev) {

		List<WartungDTO> wartungen = new ArrayList<WartungDTO>();

		if (ev.getComponent().getId().equals("ausfuehren")) {

		}
		// erg = op1 + op2;
		// if (ev.getComponent().getId().equals("minus"))
		// erg = op1 - op2;
		// if (ev.getComponent().getId().equals("mal"))
		// erg = op1 * op2;
		// if (ev.getComponent().getId().equals("div"))
		// if (op2 != 0)
		// erg = op1 / op2;
		//
		// history.addString(op1 + " " + ev.getComponent().getId() + " " + op2 + " = " + erg);

		for (WartungDTO wartung : getGesamtWartungen()) {
			if (wartung.getAnlage().getName().equalsIgnoreCase(anlageName)) {

				wartungen.add(wartung);
			}
		}

		wartungFilter.setWartungen(wartungen);

	}

	public List<WartungDTO> getGesamtWartungen() {

		List<WartungDTO> wartungen = new ArrayList<WartungDTO>();

		wartungen.addAll(getAnlagenWartungen());
		wartungen.addAll(getStationWartungen());

		Collections.sort(wartungen);

		return wartungen;

	}

	public List<AnlageDTO> getAnlagen() {

		try {
			anlagen = anlageDAO.getAnlagen();
		} catch (DAOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
		return anlagen;
	}

	public void setAnlagen(List<AnlageDTO> anlagen) {
		this.anlagen = anlagen;
	}

	public String getAnlageName() {
		return anlageName;
	}

	public WartungenManagedBean getWartungFilter() {
		return wartungFilter;
	}

	public void setWartungFilter(WartungenManagedBean wartungFilter) {
		this.wartungFilter = wartungFilter;
	}

	public void setAnlageName(String anlageName) {
		this.anlageName = anlageName;
	}

	public void valueChanged(ValueChangeEvent ev) {

		// List<WartungDTO> wartungen = new ArrayList<WartungDTO>();
		//
		// System.out.println("ValueChangeEvent" + anlageName);
		//
		// if (ev.getComponent().getId().equals("ausfuehren")) {
		//
		// }
		// // erg = op1 + op2;
		// // if (ev.getComponent().getId().equals("minus"))
		// // erg = op1 - op2;
		// // if (ev.getComponent().getId().equals("mal"))
		// // erg = op1 * op2;
		// // if (ev.getComponent().getId().equals("div"))
		// // if (op2 != 0)
		// // erg = op1 / op2;
		// //
		// // history.addString(op1 + " " + ev.getComponent().getId() + " " + op2 + " = " + erg);
		//
		// for (WartungDTO wartung : getGesamtWartungen()) {
		// if (wartung.getAnlage().getName().equals(anlageName)) {
		// wartungen.add(wartung);
		// }
		// }
		//
		// wartungFilter.setWartungen(wartungen);

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
