package com.maintenancemonitor.db.dao;

import java.util.List;

import com.maintenancemonitor.db.dto.WartungDTO;
import com.maintenancemonitor.util.DAOException;

public interface WartungDAO {

	public void deleteWartung(WartungDTO wartung) throws DAOException;

	public List<WartungDTO> getAllWartungen() throws DAOException;
	
	public List<WartungDTO> getAnlagenWartungen() throws DAOException;
	
	public List<WartungDTO> getStationWartungen() throws DAOException;

	public List<WartungDTO> getAllWartungenFromAnlage(int anlageId) throws DAOException;

	public List<WartungDTO> getAllWartungenFromStation(int stationId) throws DAOException;

	public WartungDTO getWartung(int wartungId) throws DAOException;

	public void insertWartung(WartungDTO wartung) throws DAOException;

	public void updateWartung(WartungDTO wartung) throws DAOException;

}