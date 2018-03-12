package com.maintenance.wartung;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "stationService")
@ApplicationScoped
public class StationService {
	
	private List<Station> stationen;

	private final static String[] colors;

	private final static String[] brands;

	static {
		colors = new String[10];
		colors[0] = "Black";
		colors[1] = "White";
		colors[2] = "Green";
		colors[3] = "Red";
		colors[4] = "Blue";
		colors[5] = "Orange";
		colors[6] = "Silver";
		colors[7] = "Yellow";
		colors[8] = "Brown";
		colors[9] = "Maroon";

		brands = new String[3];
		brands[0] = "CTC 50 Linie 1";
		brands[1] = "ITC Next Gen";
		brands[2] = "ATC350";

	}

	public List<Wartung> createWartungen(int size) {
		List<Wartung> list = new ArrayList<Wartung>();
		for (int i = 0; i < size; i++) {
			list.add(new Wartung(i, getRandomBrand()));
		}

		return list;
	}

	private String getRandomId() {
		return UUID.randomUUID().toString().substring(0, 8);
	}

	private int getRandomYear() {
		return (int) (Math.random() * 50 + 1960);
	}

	private String getRandomColor() {
		return colors[(int) (Math.random() * 10)];
	}

	private String getRandomBrand() {
		return brands[(int) (Math.random() * 3)];
	}

	public int getRandomPrice() {
		return (int) (Math.random() * 100000);
	}

	public boolean getRandomSoldState() {
		return (Math.random() > 0.5) ? true : false;
	}

	public List<String> getColors() {
		return Arrays.asList(colors);
	}

	public List<String> getBrands() {
		return Arrays.asList(brands);
	}

	public List<Station> getStationen() {
		return stationen;
	}
	
	
}