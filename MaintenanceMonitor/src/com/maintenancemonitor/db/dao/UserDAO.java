package com.maintenancemonitor.db.dao;

import java.util.List;

import com.maintenancemonitor.db.dto.UserDTO;
import com.maintenancemonitor.util.DAOException;

public interface UserDAO {

	public void deleteUser(UserDTO user) throws DAOException;

	public UserDTO selectUser(int userId) throws DAOException;

	public List<UserDTO> getAllUser() throws DAOException;

	public void insertUser(UserDTO user) throws DAOException;

	public void updateUser(UserDTO user) throws DAOException;

}