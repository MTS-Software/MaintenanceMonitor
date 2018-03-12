package com.maintenancemonitor.db.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class AbteilungDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
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
		return name;
	}

}
