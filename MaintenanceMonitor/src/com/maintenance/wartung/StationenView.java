package com.maintenance.wartung;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "stationenView")
@SessionScoped
public class StationenView {

	private int count;

	@ManagedProperty(value = "#{stationService}")
	private StationService service;

	@ManagedProperty(value = "#{station}")
	private Station station;

	private List<Station> stationen;

	@PostConstruct
	public void init() {

		System.out.println("init");

		stationen = service.getStationen();
	}

	public String select() {

		System.out.println("select: " + station.getName());

		return "tpmWartung";

	}

	public List<Station> getStationen() {
		return stationen;
	}

	public void setService(StationService service) {
		this.service = service;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public void countUp() {

		count++;
		System.out.println(count);
	}

}