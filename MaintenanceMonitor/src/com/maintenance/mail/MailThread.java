package com.maintenance.mail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.maintenancemonitor.db.dao.AnlageDAO;
import com.maintenancemonitor.db.dao.AnlageUserDAO;
import com.maintenancemonitor.db.dao.DAOFactory;
import com.maintenancemonitor.db.dao.EDAOType;
import com.maintenancemonitor.db.dao.StationDAO;
import com.maintenancemonitor.db.dao.UserDAO;
import com.maintenancemonitor.db.dto.AnlageDTO;
import com.maintenancemonitor.db.dto.AnlageUserDTO;
import com.maintenancemonitor.db.dto.StationDTO;
import com.maintenancemonitor.db.dto.UserDTO;
import com.maintenancemonitor.util.DAOException;

public class MailThread implements Runnable {

	private Session mailSession;

	private AnlageDAO anlageDAO;
	private StationDAO stationDAO;
	private UserDAO userDAO;
	private AnlageUserDAO anlageUserDAO;

	public MailThread(Session mailSession) {
		this.mailSession = mailSession;

		DAOFactory daoFactory;
		daoFactory = new DAOFactory(EDAOType.JDBC);

		anlageDAO = daoFactory.getAnlageDAO();
		stationDAO = daoFactory.getStationDAO();
		userDAO = daoFactory.getUserDAO();
		anlageUserDAO = daoFactory.getAnlageUserDAO();

	}

	@Override
	public void run() {

		long hour = 1;

		long sleep = 3600000 * hour; // Stunden in Millisekungen umrechnen

		// sleep = 60000; // 1 Minute

		while (!Thread.currentThread().isInterrupted()) {

			try {
				System.out.println(
						"MailThread: " + Thread.currentThread().getName() + ", Id: " + Thread.currentThread().getId());

				Calendar cal = Calendar.getInstance();
				hour = cal.get(Calendar.HOUR_OF_DAY);
				int min = cal.get(Calendar.MINUTE);

				if (hour == 6) {
					System.out.println("Anlagen und Stationen werden auf durchzufuehrende Wartungen durchsucht.");

					getAnlagen();

					String text = "";
					try {
						text = "Benutzerliste: " + userDAO.getAllUser();
					} catch (DAOException e) {
						sendEmail("markus.thaler@magna.com", "MaintenanceMonitor", e.getMessage());
						e.printStackTrace();
					}
					sendEmail("markus.thaler@magna.com", "MaintenanceMonitor",
							"Mails versenden wird ausgeführt\n" + text);

				}
				Thread.sleep(sleep);
			} catch (InterruptedException e) {

				Thread.currentThread().interrupt();
				e.printStackTrace();
			}

		}

	}

	private void sendEmail(String adress, String subject, String body) {
		Message message = new MimeMessage(mailSession);
		try {

			message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(adress));
			message.setSubject(subject);
			message.setSentDate(new Date());
			message.setText(body);

			Transport.send(message);

		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}

	private List<AnlageUserDTO> getAnlagenUser(int anlageId) {

		List<AnlageUserDTO> anlagenUser;
		List<UserDTO> users;

		List<AnlageUserDTO> mergedAnlagenUser = new ArrayList<AnlageUserDTO>();

		try {
			anlagenUser = anlageUserDAO.selectAnlagenUser(anlageId);

			users = userDAO.getAllUser();

			for (AnlageUserDTO anlageUser : anlagenUser) {
				for (UserDTO user : users) {
					if (user.getId() == anlageUser.getUserId()) {
						anlageUser.setBenutzer(user);
					}
				}
				mergedAnlagenUser.add(anlageUser);

			}

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mergedAnlagenUser;
	}

	private void getAnlagen() {

		int prozent;
		List<AnlageDTO> anlagen;
		String text = "Warnung: ";

		try {
			anlagen = mapStationAnlage(anlageDAO.getAnlagen(), stationDAO.getStationen());

			for (AnlageDTO anlage : anlagen) {

				if (anlage.getStatus()) {

					prozent = calcProzent(anlage);
					if (prozent < anlage.getWartungWarnung()) {
						if (anlage.isMailSent()) {
							anlage.setMailSent(false);
							anlageDAO.updateMailSent(anlage);
						}
					}

					if (prozent >= anlage.getWartungWarnung()) {
						if (!anlage.isMailSent()) {

							for (AnlageUserDTO anlagenUser : getAnlagenUser(anlage.getId())) {

								sendEmail(anlagenUser.getBenutzer().getMail(),
										"MaintenanceMonitor: " + anlage.getName(),
										"Anlage: " + anlage.getName() + "\n" + text + prozent + "%");
								System.out.println(anlage.getName());
								System.out.println(anlagenUser.getBenutzer().getMail());

							}

							anlage.setMailSent(true);
							anlageDAO.updateMailSent(anlage);
						}
					}

					for (StationDTO station : anlage.getStationen()) {

						if (station.isStatus()) {

							prozent = calcProzent(station);

							// if (station.getAktuelleWartungId() != 0 || prozent <
							// station.getWartungWarnung()) {
							// if (station.isMailSent()) {
							// station.setMailSent(false);
							// stationDAO.updateMailSent(station);
							// }
							// }
							//
							// if (prozent >= station.getWartungWarnung() && station.getAktuelleWartungId()
							// == 0) {
							// if (!station.isMailSent()) {
							//
							// for (AnlageUserDTO anlagenUser : getAnlagenUser(anlage.getId())) {
							// System.out.println(station.getAnlage() + ": " + station.getName());
							// System.out.println(anlagenUser.getBenutzer().getMail());
							// sendEmail(anlagenUser.getBenutzer().getMail(),
							// "MaintenanceMonitor: " + anlage.getName(),
							// "Anlage: " + anlage.getName() + "\nStation / Komponente: "
							// + station.getName() + "\n" + text + prozent + "%");
							//
							// }
							//
							// station.setMailSent(true);
							// stationDAO.updateMailSent(station);
							// }
							// }
						}

					}
				}
			}

		} catch (DAOException e) {
			sendEmail("markus.thaler@magna.com", "MaintenanceMonitor", e.getMessage());
			e.printStackTrace();
		}

	}

	private List<AnlageDTO> mapStationAnlage(List<AnlageDTO> anlagen, List<StationDTO> stationen) {

		List<StationDTO> anlagenStationen;

		for (AnlageDTO anlage : anlagen) {
			anlagenStationen = new ArrayList<StationDTO>();

			for (StationDTO station : stationen) {
				if (anlage.getId() == station.getAnlageId()) {
					station.setAnlage(anlage);
					anlagenStationen.add(station);
				}

			}
			anlage.setStationen(anlagenStationen);
		}

		return anlagen;

	}

	private int calcProzent(AnlageDTO anlage) {

		float prozent = 0;
		float prozentUeberproduktion = 0;
		int produziert;
		int ueberproduktion;

		produziert = anlage.getAktuelleStueck() - anlage.getLastWartungStueckzahl();

		prozent = (float) 100 * produziert / anlage.getWartungIntervall();

		ueberproduktion = produziert - anlage.getWartungIntervall();
		prozentUeberproduktion = (float) 100 * ueberproduktion / anlage.getWartungIntervall();

		if (prozent >= 100) {
			prozent = 100 + prozentUeberproduktion;
		}

		if (prozent >= 200) {
			prozent = 200 + prozentUeberproduktion;
		}

		return (int) prozent;
	}

	private int calcProzent(StationDTO station) {

		float prozent = 0;
		float prozentUeberproduktion = 0;
		int produziert;
		int ueberproduktion;

		// if (station.getLastWartungStueckzahl() != 0) {

		produziert = station.getAnlage().getAktuelleStueck() - station.getLastWartungStueckzahl();

		prozent = (float) 100 * produziert / station.getWartungIntervall();

		ueberproduktion = produziert - station.getWartungIntervall();
		prozentUeberproduktion = (float) 100 * ueberproduktion / station.getWartungIntervall();

		if (prozent >= 100) {
			prozent = 100 + prozentUeberproduktion;
		}

		if (prozent >= 200) {
			prozent = 200 + prozentUeberproduktion;
		}
		// }

		return (int) prozent;
	}

}
