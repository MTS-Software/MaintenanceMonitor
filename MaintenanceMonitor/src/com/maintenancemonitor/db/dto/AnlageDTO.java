package com.maintenancemonitor.db.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "anlage")
@RequestScoped
public class AnlageDTO {

	private int id;

	private String name;
	private String auftragNr;
	private int jahresStueck;
	private int aktuelleStueck;
	private int wartungIntervall;
	private int lastWartungStueckzahl;
	private boolean auswertung;
	private int wartungWarnung;
	private int wartungFehler;
	private boolean status;
	private String produkte;
	private boolean mailSent;
	private String wartungsplaeneLink;

	// Foreign keys
	private int panelFormatId;
	private int abteilungId;

	private Timestamp timestamp;
	private String user;

	private PanelFormatDTO panelFormat;
	private AbteilungDTO abteilung;

	@ManagedProperty(value = "#{wartungen}")
	private List<WartungDTO> wartungen;

	private List<StationDTO> stationen;

	public List<WartungDTO> getWartungen() {
		return wartungen;
	}

	public void setWartungen(List<WartungDTO> wartungen) {
		this.wartungen = wartungen;
	}

	public AbteilungDTO getAbteilung() {
		return abteilung;
	}

	public int getAbteilungId() {
		return abteilungId;
	}

	public int getAktuelleStueck() {
		return aktuelleStueck;
	}

	public String getAuftragNr() {
		return auftragNr;
	}

	public int getId() {
		return id;
	}

	public int getJahresStueck() {
		return jahresStueck;
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

	public boolean getStatus() {
		return status;
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

	public int getWartungWarnung() {
		return wartungWarnung;
	}

	public boolean isAuswertung() {
		return auswertung;
	}

	public void setAbteilung(AbteilungDTO abteilung) {
		this.abteilung = abteilung;
	}

	public void setAbteilungId(int abteilungId) {
		this.abteilungId = abteilungId;
	}

	public void setAktuelleStueck(int aktuelleStueck) {
		this.aktuelleStueck = aktuelleStueck;
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

	public void setJahresStueck(int jahresStueck) {
		this.jahresStueck = jahresStueck;
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

	public void setWartungWarnung(int wartungWarnung) {
		this.wartungWarnung = wartungWarnung;
	}

	public String getProdukte() {
		return produkte;
	}

	public void setProdukte(String produkte) {
		this.produkte = produkte;
	}

	public boolean isMailSent() {
		return mailSent;
	}

	public void setMailSent(boolean mailSent) {
		this.mailSent = mailSent;
	}

	public List<StationDTO> getStationen() {
		return stationen;
	}

	public void setStationen(List<StationDTO> stationen) {
		this.stationen = stationen;
	}

	public String getWartungsplaeneLink() {
		return wartungsplaeneLink;
	}

	public void setWartungsplaeneLink(String wartungsplaeneLink) {
		this.wartungsplaeneLink = wartungsplaeneLink;
	}

	@Override
	public String toString() {
		return name;
	}

}
