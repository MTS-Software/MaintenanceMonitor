package com.maintenance.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.maintenancemonitor.db.dto.StationDTO;
import com.maintenancemonitor.db.dto.WartungDTO.EWartungArt;

public class ProzentCalc {

	public static float calcProzent(StationDTO station) {

		float prozent = 0;
		float prozentUeberproduktion = 0;
		int produziert;
		int ueberproduktion;

		produziert = station.getAnlage().getAktuelleStueck() - station.getLastWartungStueckzahl();

		prozent = (float) 100 * produziert / station.getWartungStueckIntervall();

		ueberproduktion = produziert - station.getWartungStueckIntervall();
		prozentUeberproduktion = (float) 100 * ueberproduktion / station.getWartungStueckIntervall();

		if (prozent >= 100) {
			prozent = 100 + prozentUeberproduktion;
		}

		if (prozent >= 200) {
			prozent = 200 + prozentUeberproduktion;
		}

		// if (prozent > 100.0f)
		// prozent = 100.0f;
		//
		if (prozent < 0.0f)
			prozent = 0.0f;

		return Math.round(prozent);
	}

	public static float calcProzent(long lastWartung, long nextWartung) {

		float prozent = 0;
		long diff;
		long act;

		diff = nextWartung - lastWartung;
		act = Calendar.getInstance().getTimeInMillis();

		long d = nextWartung - act;

		if (diff != 0)
			prozent = 100 * d / diff;

		if (prozent > 100.0f)
			prozent = 100.0f;

		if (prozent < 0.0f)
			prozent = 0.0f;

		return Math.round(100.0 - prozent);

	}

	public static Date calcNextWartungDate(Date lastWartungDate, int dateUnit, int intervall) {

		long intervallMillis;

		Date nextWartungDate = new Date();

		Calendar calLastWartung = Calendar.getInstance();
		Calendar calNextWartung = Calendar.getInstance();

		calLastWartung.setTime(lastWartungDate);
		calNextWartung.setTime(calLastWartung.getTime());

		if (dateUnit == EIntervallDateUnit.DAY.ordinal()) {
			calNextWartung.add(Calendar.DAY_OF_YEAR, intervall);
		}

		if (dateUnit == EIntervallDateUnit.WEEK.ordinal()) {
			calNextWartung.add(Calendar.WEEK_OF_YEAR, intervall);
		}

		if (dateUnit == EIntervallDateUnit.MONTH.ordinal()) {
			calNextWartung.add(Calendar.MONTH, intervall);
		}

		if (dateUnit == EIntervallDateUnit.YEAR.ordinal()) {
			calNextWartung.add(Calendar.YEAR, intervall);
		}

		intervallMillis = calNextWartung.getTimeInMillis() - calLastWartung.getTimeInMillis();
		nextWartungDate.setTime(calLastWartung.getTime().getTime() + intervallMillis);

		return nextWartungDate;

	}

	public static Date calcNextCalendarWartungDate(Date lastWartungDate, int intervall) {

		long intervallMillis;

		Date nextWartungDate = new Date();

		Calendar calLastWartung = Calendar.getInstance();
		Calendar calNextWartung = Calendar.getInstance();

		calLastWartung.setTime(lastWartungDate);
		calNextWartung.setTime(calLastWartung.getTime());

		intervallMillis = calNextWartung.getTimeInMillis() - calLastWartung.getTimeInMillis();
		nextWartungDate.setTime(calLastWartung.getTime().getTime() + intervallMillis);

		return nextWartungDate;

	}

	public static Date calcNextCalendarWarnungDate(Date lastWartungDate, Date nextWartungDate, int intervall) {

		Date nextWarnungDate = new Date();
		Calendar calIntervall = Calendar.getInstance();

		calIntervall.setTimeInMillis(nextWartungDate.getTime());

		nextWarnungDate.setTime(calIntervall.getTimeInMillis());

		return nextWarnungDate;

	}

	public static Date calcNextWarnungDate(int dateUnit, Date lastWartungDate, Date nextWartungDate, int intervall) {

		Date nextWarnungDate = new Date();
		Calendar calIntervall = Calendar.getInstance();

		calIntervall.setTimeInMillis(nextWartungDate.getTime());

		if (dateUnit == EIntervallDateUnit.DAY.ordinal()) {
			calIntervall.add(Calendar.DAY_OF_YEAR, -intervall);
		}

		if (dateUnit == EIntervallDateUnit.WEEK.ordinal()) {
			calIntervall.add(Calendar.WEEK_OF_YEAR, -intervall);
		}

		if (dateUnit == EIntervallDateUnit.MONTH.ordinal()) {
			calIntervall.add(Calendar.MONTH, -intervall);
		}

		if (dateUnit == EIntervallDateUnit.YEAR.ordinal()) {
			calIntervall.add(Calendar.YEAR, -intervall);
		}

		nextWarnungDate.setTime(calIntervall.getTimeInMillis());

		return nextWarnungDate;

	}

	public static boolean isTPMStationWarning(List<StationDTO> stationen) {

		for (StationDTO station : stationen) {

			if (station.isTpm())
				if (station.isStatus()) {

					float prozent = ProzentCalc.calcProzent(station);

					if (station.getWartungArt() == EWartungArt.STUECKZAHL.ordinal())
						if (prozent >= station.getWartungStueckWarnung()
								&& prozent < station.getWartungStueckFehler()) {
							return true;
						}

					if (station.getWartungArt() == EWartungArt.TIME_INTERVALL.ordinal()) {
						Date nextWarnungDate = null;
						Date nextWartungDate;
						Date lastWartungDate;

						if (station.getLastWartungDate() != null)
							lastWartungDate = station.getLastWartungDate();
						else
							lastWartungDate = station.getCreateDate();

						nextWartungDate = ProzentCalc.calcNextWartungDate(lastWartungDate,
								station.getIntervallDateUnit(), station.getWartungDateIntervall());

						nextWarnungDate = ProzentCalc.calcNextWarnungDate(station.getWarnungDateUnit(), lastWartungDate,
								nextWartungDate, station.getWartungDateWarnung());

						if (Calendar.getInstance().getTime().after(nextWarnungDate)
								&& Calendar.getInstance().getTime().before(nextWartungDate)) {
							return true;
						}

					}

				}

		}

		return false;

	}

	public static boolean isTPMStationFehler(List<StationDTO> stationen) {

		for (StationDTO station : stationen) {

			if (station.isTpm())
				if (station.isStatus()) {

					float prozent = ProzentCalc.calcProzent(station);

					if (station.getWartungArt() == EWartungArt.STUECKZAHL.ordinal())
						if (prozent >= station.getWartungStueckFehler()) {
							return true;
						}

					if (station.getWartungArt() == EWartungArt.TIME_INTERVALL.ordinal()) {

						Date nextWartungDate;
						Date lastWartungDate;

						if (station.getLastWartungDate() != null)
							lastWartungDate = station.getLastWartungDate();
						else
							lastWartungDate = station.getCreateDate();

						nextWartungDate = ProzentCalc.calcNextWartungDate(lastWartungDate,
								station.getIntervallDateUnit(), station.getWartungDateIntervall());

						if (Calendar.getInstance().getTime().after(nextWartungDate)) {
							return true;
						}

					}

				}

		}

		return false;

	}

}
