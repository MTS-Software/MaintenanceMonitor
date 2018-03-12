package com.maintenancemonitor.db.dto;

import java.sql.Timestamp;

public class History {

	private int id;

	private Timestamp timestamp;
	private String user;

	public int getId() {
		return id;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getUser() {
		return user;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return user + " " + timestamp;
	}

}
