package com.example.app.reservation;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.core.Event;
import com.example.app.core.NavigationBarController;
import com.example.app.core.appcontroller.AccessLevel;
import com.example.app.core.appcontroller.ApplicationController;
import com.example.app.core.repository.Dao;
import com.example.app.homepage.UserModel;

@Controller
public class MakeReservationController {
	
	private ApplicationController appController;
	private HttpSession httpSession;
	private Dao dao;
	
	@Autowired
	public MakeReservationController(ApplicationController appController, HttpSession httpSession, Dao dao) {
		this.appController = appController;
		this.dao = dao;
		this.httpSession = httpSession;
		
		setMinAccessLevels();
	}
	
	private void setMinAccessLevels() {
		appController.setMinAccessLevel("/make_reservation", AccessLevel.LOGGED_IN);
		appController.setMinAccessLevel("/reserve", AccessLevel.LOGGED_IN);
		appController.setMinAccessLevel("/cancel_reservation", AccessLevel.LOGGED_IN);
	}
	
	@GetMapping("/make_reservation")
	public String showMakeReservation(@RequestParam(name="id", required=true) int id,
			@RequestParam(name="event", required=false) String event,
			Model model) {
		
		if (event != null) {
			model.addAttribute("event", event);
		}
		
		model.addAttribute("title", "Make reservation");
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/list", "Reservations list");
		navigation.addBreadcrumb("/make_reservation?id=" + id, "Make reservation");
		navigation.update();
		
		ReservationModel reservation = dao.getReservation(id);
		model.addAttribute("reservation", reservation);
		
		List<AvailableReservationModel> availableReservations = dao.getAvailableReservations(id);
		model.addAttribute("av_reservations", availableReservations);
		
		String login = (String) httpSession.getAttribute("login");
		UserModel user = dao.getUser(login);
		model.addAttribute("user", user);
		
		List<AvailableReservationModel> madeReservations = dao.getMadeReservations(id, user.getId());
		model.addAttribute("made_reservations", madeReservations);
		
		List<AvailableReservationModel> allMadeReservations = dao.getMadeReservations(id);
		List<MadeReservationWrapper> allMadeReservationsWithUsers = allMadeReservations.stream()
				.map(r -> new MadeReservationWrapper(dao.getUser(r.getUserId()), r))
				.collect(Collectors.toList());
		
		model.addAttribute("all_made_reservations", allMadeReservationsWithUsers);
		
		return "/reservation/make_reservation";
	}
	
	@GetMapping("/reserve")
	public String makeReservations(@RequestParam(name="id", required=true) int availableReservationId,
			Model model) {
		String login = (String) httpSession.getAttribute("login");
		int userId = dao.getUser(login).getId();
		dao.makeReservation(userId, availableReservationId);
		
		AvailableReservationModel availableReservation = dao.getMadeReservation(availableReservationId);
		return "redirect:/make_reservation?id=" + availableReservation.getReservationId()
			+ "&event=" + Event.RESERVATION_MADE.getName();
	}
	
	@GetMapping("/cancel_reservation")
	public String cancelReservations(@RequestParam(name="id", required=true) int availableReservationId,
			Model model) {
			
		dao.cancelReservation(availableReservationId);
		AvailableReservationModel availableReservation = dao.getMadeReservation(availableReservationId);
		return "redirect:/make_reservation?id=" + availableReservation.getReservationId()
			+ "&event=" + Event.RESERVATION_CANCELED.getName();
	}
	
	private static class MadeReservationWrapper {
		private UserModel user;
		private AvailableReservationModel reservation;
		
		public MadeReservationWrapper(UserModel user, AvailableReservationModel reservation) {
			this.user = user;
			this.reservation = reservation;
		}
		
		public UserModel getUser() {
			return user;
		}
		
		public AvailableReservationModel getReservation() {
			return reservation;
		}
	}

}
