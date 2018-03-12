package com.maintenancemonitor.db.dto;

import java.sql.Timestamp;

public class LeerflaecheDTO {

	private int id;
	private String name;

	private Timestamp timestamp;
	private String user;

	private int panelFormatId;

	private PanelFormatDTO panelFormat;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public PanelFormatDTO getPanelFormat() {
		return panelFormat;
	}

	public int getPanelFormatId() {
		return panelFormatId;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setPanelFormat(PanelFormatDTO panelFormat) {
		this.panelFormat = panelFormat;
	}

	public void setPanelFormatId(int panelFormatId) {
		this.panelFormatId = panelFormatId;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "DummyProjektDTO [id=" + id + ", bez=" + name + ", panelFormatId=" + panelFormatId + "]";
	}

}
