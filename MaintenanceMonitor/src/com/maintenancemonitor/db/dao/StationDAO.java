package com.maintenancemonitor.db.dao;

import java.util.Date;
import java.util.List;

import com.maintenancemonitor.db.dto.AnlageDTO;
import com.maintenancemonitor.db.dto.StationDTO;
import com.maintenancemonitor.db.dto.WartungDTO;
import com.maintenancemonitor.util.DAOException;

public interface StationDAO {

	public void deleteStation(StationDTO station) throws DAOException;

	public List<StationDTO> getAllStationenFromAnlage(AnlageDTO anlage) throws DAOException;
	
	public List<StationDTO> getStationen() throws DAOException;

	public StationDTO getStation(int stationId) throws DAOException;

	public List<WartungDTO> getWartungenStationDate(StationDTO station, Date start, Date end) throws DAOException;

	public void insertStation(StationDTO station) throws DAOException;

	public void updateStation(StationDTO station) throws DAOException;

	public void updateStationStatus(StationDTO station) throws DAOException;
	
	public void updateMailSent(StationDTO station) throws DAOException;

}