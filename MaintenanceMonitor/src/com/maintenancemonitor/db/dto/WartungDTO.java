package com.maintenancemonitor.db.dto;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "wartung")
@RequestScoped
public class WartungDTO implements Comparable<WartungDTO> {

	private int id;
	private String auftragNr;
	private Date faellig;
	private int prozent;
	private String mitarbeiter;
	private String info;
	private int status;

	private int anlageId;
	private int stationId;

	private Timestamp timestamp;
	private String user;

	private AnlageDTO anlage;
	private StationDTO station;

	private List<AnhangDTO> anhangList;

	public AnlageDTO getAnlage() {
		return anlage;
	}

	public int getAnlageId() {
		return anlageId;
	}

	public String getAuftragNr() {
		return auftragNr;
	}

	public Date getFaellig() {
		return faellig;
	}

	public int getId() {
		return id;
	}

	public String getInfo() {
		return info;
	}

	public String getMitarbeiter() {
		return mitarbeiter;
	}

	public int getProzent() {
		return prozent;
	}

	public StationDTO getStation() {
		return station;
	}

	public int getStationId() {
		return stationId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getUser() {
		return user;
	}

	public void setAnlage(AnlageDTO anlage) {
		this.anlage = anlage;
	}

	public void setAnlageId(int anlageId) {
		this.anlageId = anlageId;
	}

	public void setAuftragNr(String auftragNr) {
		this.auftragNr = auftragNr;
	}

	public void setFaellig(Date faellig) {
		this.faellig = faellig;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setInfo(String information) {
		this.info = information;
	}

	public void setMitarbeiter(String mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	public void setProzent(int prozent) {
		this.prozent = prozent;
	}

	public void setStation(StationDTO station) {
		this.station = station;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<AnhangDTO> getAnhangList() {
		return anhangList;
	}

	public void setAnhangList(List<AnhangDTO> anhangList) {
		this.anhangList = anhangList;
	}

	
	
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "WartungDTO [id=" + id + ", faellig=" + faellig + ", abgeschlossen=" + ", prozent=" + prozent
				+ ", anlageId=" + anlageId + ", stationId=" + stationId + ", timestamp=" + timestamp + ", user=" + user
				+ "]";
	}

	@Override
	public int compareTo(WartungDTO o) {
		// TODO Automatisch generierter Methodenstub
		return getAnlage().getName().compareTo(o.getAnlage().getName());
	}
	
	
	
	public enum EWartungArt {

		STUECKZAHL("Stückzahl"), TIME_INTERVALL("Zeitintervall");

		private String label;

		EWartungArt(String label) {
			this.label = label;

		}

		@Override
		public String toString() {
			return label;
		}

	}

}
