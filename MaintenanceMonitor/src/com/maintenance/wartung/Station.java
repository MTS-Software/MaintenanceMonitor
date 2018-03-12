package com.maintenance.wartung;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "station")
@SessionScoped
public class Station {

	private int id;
	private String name;
	private int anlage_id;

	private Anlage anlage;

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

	public int getAnlage_id() {
		return anlage_id;
	}

	public void setAnlage_id(int anlage_id) {
		this.anlage_id = anlage_id;
	}

	public Anlage getAnlage() {
		return anlage;
	}

	public void setAnlage(Anlage anlage) {
		this.anlage = anlage;
	}

}
