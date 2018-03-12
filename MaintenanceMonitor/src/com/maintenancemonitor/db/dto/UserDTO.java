package com.maintenancemonitor.db.dto;

import java.sql.Timestamp;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "user")
public class UserDTO {

	private int id;
	private String name;
	private String mail;

	private Timestamp timestamp;
	private String user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", mail=" + mail + "]";
	}

}
