package com.maintenancemonitor.db.dao;

import java.util.List;

import com.maintenancemonitor.db.dto.AbteilungDTO;
import com.maintenancemonitor.util.DAOException;

public interface AbteilungDAO {

	public void deleteAbteilung(AbteilungDTO abteilung) throws DAOException;

	public AbteilungDTO getAbteilung(int abteilungId) throws DAOException;

	public List<AbteilungDTO> getAllAbteilung() throws DAOException;

	public void insertAbteilung(AbteilungDTO abteilung) throws DAOException;

	public void updateAbteilung(AbteilungDTO abteilung) throws DAOException;

}