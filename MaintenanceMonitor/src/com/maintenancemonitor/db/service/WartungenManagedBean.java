package com.maintenancemonitor.db.service;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.maintenancemonitor.db.dto.WartungDTO;

@ManagedBean(name = "filteredWartungen")
@SessionScoped
public class WartungenManagedBean {

	List<WartungDTO> wartungen;

	public WartungenManagedBean() {

	}

	public List<WartungDTO> getWartungen() {
		return wartungen;
	}

	public void setWartungen(List<WartungDTO> wartungen) {
		this.wartungen = wartungen;
	}

}
