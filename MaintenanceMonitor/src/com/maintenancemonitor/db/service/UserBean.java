package com.maintenancemonitor.db.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.maintenancemonitor.db.dao.DAOFactory;
import com.maintenancemonitor.db.dao.EDAOType;
import com.maintenancemonitor.db.dao.UserDAO;
import com.maintenancemonitor.db.dto.UserDTO;
import com.maintenancemonitor.util.DAOException;

@ManagedBean(name = "userBean")
@RequestScoped
public class UserBean {

	private List<UserDTO> users = new ArrayList<UserDTO>();
	private UserDAO userDAO;

	public UserBean() {

		DAOFactory daoFactory;
		daoFactory = new DAOFactory(EDAOType.JDBC);

		userDAO = daoFactory.getUserDAO();

		try {

			for (UserDTO user : userDAO.getAllUser()) {

				users.add(user);

			}

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

}
