package com.maintenancemonitor.db.dao;

import java.util.List;

import com.maintenancemonitor.db.dto.AnlageUserDTO;
import com.maintenancemonitor.util.DAOException;

public interface AnlageUserDAO {

	public void deleteAnlageUser(AnlageUserDTO anlageUser) throws DAOException;

	public AnlageUserDTO selectAnlageUser(int anlageId, int userId)
			throws DAOException;

	public List<AnlageUserDTO> selectAnlagenUser(int anlageId) throws DAOException;

	public void insertAnlageUser(AnlageUserDTO anlageUser) throws DAOException;

	public void updateAnlageUser(AnlageUserDTO anlageUser) throws DAOException;

}