package com.maintenancemonitor.db.dao;

import java.util.List;

import com.maintenancemonitor.db.dto.AnlageDTO;
import com.maintenancemonitor.util.DAOException;

public interface AnlageDAO {

	public List<AnlageDTO> getAnlagen() throws DAOException;

	public void updateMailSent(AnlageDTO anlage) throws DAOException;

}