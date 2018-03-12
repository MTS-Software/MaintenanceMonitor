package com.maintenancemonitor.db.dto;

import java.sql.Timestamp;

public class AnlageUserDTO {

	private int anlageId;
	private int userId;

	private Timestamp timestamp;
	private String user;

	private AnlageDTO anlage;
	private UserDTO benutzer;

	public int getAnlageId() {
		return anlageId;
	}

	public void setAnlageId(int anlageId) {
		this.anlageId = anlageId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public AnlageDTO getAnlage() {
		return anlage;
	}

	public void setAnlage(AnlageDTO anlage) {
		this.anlage = anlage;
	}

	public UserDTO getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(UserDTO benutzer) {
		this.benutzer = benutzer;
	}
	
	
	

}
