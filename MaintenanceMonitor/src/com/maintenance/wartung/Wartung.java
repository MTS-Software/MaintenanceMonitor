package com.maintenance.wartung;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "wartung")
@SessionScoped
public class Wartung {

	private int id;
	private String name;
	private Date faellig;
	private String mitarbeiter;
	private String info;
	private boolean tpm;
	private String user;

	private int station_id;

	private List<Station> stationen;

	public Wartung() {

	}

	public Wartung(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

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

	public Date getFaellig() {
		return faellig;
	}

	public void setFaellig(Date faellig) {
		this.faellig = faellig;
	}

	public String getMitarbeiter() {
		return mitarbeiter;
	}

	public void setMitarbeiter(String mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isTpm() {
		return tpm;
	}

	public void setTpm(boolean tpm) {
		this.tpm = tpm;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getStation_id() {
		return station_id;
	}

	public void setStation_id(int station_id) {
		this.station_id = station_id;
	}

	public List<Station> getStationen() {
		return stationen;
	}

	public void setStationen(List<Station> stationen) {
		this.stationen = stationen;
	}

}
