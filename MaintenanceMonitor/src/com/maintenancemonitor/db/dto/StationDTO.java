package com.maintenancemonitor.db.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "station")
@SessionScoped
public class StationDTO {

	private int id;
	private String name;
	private String auftragNr;
	private int wartungIntervall;
	private int lastWartungStueckzahl;
	private boolean auswertung;
	private int wartungWarnung;
	private int wartungFehler;
	private boolean status;
	private boolean mailSent;
	private boolean tpm;

	private int wartungArt;

	private int wartungStueckIntervall;
	private int wartungDateIntervall;
	private Date lastWartungDate;
	private Date createDate;
	private int intervallDateUnit;

	private int wartungStueckWarnung;
	private int wartungStueckFehler;
	private int wartungDateWarnung;
	private int warnungDateUnit;

	private int anlageId;
	private int panelFormatId;

	private Timestamp timestamp;
	private String user;

	private PanelFormatDTO panelFormat;
	private AnlageDTO anlage;
	private List<WartungDTO> wartungList = new ArrayList<WartungDTO>();

	private List<AnhangDTO> anhangList;

	public List<AnhangDTO> getAnhangList() {
		return anhangList;
	}

	public void setAnhangList(List<AnhangDTO> anhangList) {
		this.anhangList = anhangList;
	}

	public boolean isMailSent() {
		return mailSent;
	}

	public int getIntervallDateUnit() {
		return intervallDateUnit;
	}

	public void setIntervallDateUnit(int intervallDateUnit) {
		this.intervallDateUnit = intervallDateUnit;
	}

	public void setMailSent(boolean mailSent) {
		this.mailSent = mailSent;
	}

	public AnlageDTO getAnlage() {
		return anlage;
	}

	public int getWarnungDateUnit() {
		return warnungDateUnit;
	}

	public void setWarnungDateUnit(int warnungDateUnit) {
		this.warnungDateUnit = warnungDateUnit;
	}

	public int getAnlageId() {
		return anlageId;
	}

	public String getAuftragNr() {
		return auftragNr;
	}

	public int getWartungArt() {
		return wartungArt;
	}

	public void setWartungArt(int wartungArt) {
		this.wartungArt = wartungArt;
	}

	public int getId() {
		return id;
	}

	public int getLastWartungStueckzahl() {
		return lastWartungStueckzahl;
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

	public int getWartungFehler() {
		return wartungFehler;
	}

	public int getWartungIntervall() {
		return wartungIntervall;
	}

	public List<WartungDTO> getWartungList() {
		return wartungList;
	}

	public int getWartungWarnung() {
		return wartungWarnung;
	}

	public boolean isAuswertung() {
		return auswertung;
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

	public void setAuswertung(boolean auswertung) {
		this.auswertung = auswertung;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLastWartungStueckzahl(int lastWartungStueckzahl) {
		this.lastWartungStueckzahl = lastWartungStueckzahl;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setWartungFehler(int wartungFehler) {
		this.wartungFehler = wartungFehler;
	}

	public void setWartungIntervall(int wartungIntervall) {
		this.wartungIntervall = wartungIntervall;
	}

	public void setWartungList(List<WartungDTO> wartungList) {
		this.wartungList = wartungList;
	}

	public void setWartungWarnung(int wartungWarnung) {
		this.wartungWarnung = wartungWarnung;
	}

	public boolean isTpm() {
		return tpm;
	}

	public void setTpm(boolean tpm) {
		this.tpm = tpm;
	}

	public int getWartungStueckIntervall() {
		return wartungStueckIntervall;
	}

	public void setWartungStueckIntervall(int wartungStueckIntervall) {
		this.wartungStueckIntervall = wartungStueckIntervall;
	}

	public int getWartungDateIntervall() {
		return wartungDateIntervall;
	}

	public void setWartungDateIntervall(int wartungDateIntervall) {
		this.wartungDateIntervall = wartungDateIntervall;
	}

	public Date getLastWartungDate() {
		return lastWartungDate;
	}

	public void setLastWartungDate(Date lastWartungDate) {
		this.lastWartungDate = lastWartungDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getWartungStueckWarnung() {
		return wartungStueckWarnung;
	}

	public void setWartungStueckWarnung(int wartungStueckWarnung) {
		this.wartungStueckWarnung = wartungStueckWarnung;
	}

	public int getWartungStueckFehler() {
		return wartungStueckFehler;
	}

	public void setWartungStueckFehler(int wartungStueckFehler) {
		this.wartungStueckFehler = wartungStueckFehler;
	}

	public int getWartungDateWarnung() {
		return wartungDateWarnung;
	}

	public void setWartungDateWarnung(int wartungDateWarnung) {
		this.wartungDateWarnung = wartungDateWarnung;
	}

	@Override
	public String toString() {
		return "StationDTO [id=" + id + ", name=" + name + ", timestamp=" + timestamp + ", user=" + user
				+ ", panelFormat=" + panelFormat + ", anlage=" + anlage + "]";
	}

}
