package com.example.app.reservation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.core.Event;
import com.example.app.core.NavigationBarController;
import com.example.app.core.Validator;
import com.example.app.core.appcontroller.AccessLevel;
import com.example.app.core.appcontroller.ApplicationController;
import com.example.app.core.repository.Dao;
import com.example.app.homepage.UserModel;
import com.example.app.settings.ResourceModel;
import com.example.app.settings.UsersGroupModel;

@Controller
public class ReservationController {
	
	private ApplicationController appController;
	private Dao dao;
	private HttpSession httpSession;
	
	@Autowired
	ReservationController(ApplicationController appController, Dao dao, HttpSession httpSession) {
		this.dao = dao;
		this.httpSession = httpSession;
		this.appController = appController;
		
		setMinAccessLevels();
	}
	
	private void setMinAccessLevels() {
		appController.setMinAccessLevel("/list", AccessLevel.LOGGED_IN);
		appController.setMinAccessLevel("/create_reservation", AccessLevel.LOGGED_IN);
		appController.setMinAccessLevel("/add_available_reservation", AccessLevel.LOGGED_IN);
		appController.setMinAccessLevel("/add_available_reservations", AccessLevel.LOGGED_IN);
		appController.setMinAccessLevel("/delete_reservation", AccessLevel.ADMIN);
	}
	
	
	@RequestMapping("/list")
	public String showList(Model model, @RequestParam(name="event", required=false) String event) {
		String login = (String) httpSession.getAttribute("login");
		
		if (event != null) {
			model.addAttribute("event", event);
		}
		
		model.addAttribute("title", "Reservations list");
		
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/list", "Reservations list");
		navigation.update();
		
		int userId = dao.getUser(login).getId();
		List<ReservationModel> reservations = dao.getReservationsForUser(userId);
		model.addAttribute("reservations", reservations);
		
		return "/reservation/list";
	}
	
	@GetMapping("/create_reservation")
	public String showCreateReservation(Model model) {
		model.addAttribute("title", "Create a new Reservation");
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/list", "Reservations list");
		navigation.addBreadcrumb("/create_reservation", "Add a new Reservation");
		navigation.update();
		
		model.addAttribute("reservation_model", new ReservationModel());
		
		List<ResourceModel> resources = dao.getAllResources();
		model.addAttribute("resources", resources);
		
		String login = (String) httpSession.getAttribute("login");
		int userId = dao.getUser(login).getId();
		List<UsersGroupModel> usersGroups = dao.getAllUsersGroupsAssignedToUser(userId);
		model.addAttribute("users_groups", usersGroups);
		
		return "/reservation/create_reservation";
	}
	
	@PostMapping("/create_reservation")
	public String createReservation(@ModelAttribute ReservationModel reservation) {
		
		String login = (String) httpSession.getAttribute("login");
		UserModel user = dao.getUser(login);
		reservation.setAuthorId(user.getId());
		
		Validator validator = new AddReservationValidator(reservation);
		
		if (validator.isValid()) {
			dao.createReservation(reservation);
			return "redirect:/add_available_reservation?id=" + reservation.getId()
					+ "&size=" + reservation.getQuantity();
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/add_available_reservation")
	public String showAddAvailableReservation(@RequestParam(name="id", required=true) int id,
			@RequestParam(name="size", required=true) int size,
			Model model) {
		
		model.addAttribute("title", "Add available reservations");
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/list", "Reservation list");
		navigation.addBreadcrumb("/create_reservation", "Add a new Reservation");
		navigation.addBreadcrumb("/add_available_reservation", "Add available reservation");
		navigation.update();
		
		ArrayList<AvailableReservationModel> availableReservations = new ArrayList<>(size);
		for (int i = 1; i <= size; ++i) {
			availableReservations.add(new AvailableReservationModel());
		}
		AvailableReservationsWrapper wrapper = new AvailableReservationsWrapper();
		wrapper.setAvailableReservations(availableReservations);
		model.addAttribute("available_reservations", wrapper);
		model.addAttribute("reservation_id", id);
		
		return "/reservation/add_available_reservation";
	}
	
	@PostMapping("/add_available_reservations")
	public String addAvailableReservation(
			@RequestParam(name="reservation_id", required=true) int reservationId,
			@ModelAttribute AvailableReservationsWrapper reservations) {
		reservations.getAvailableReservations().forEach(obj -> obj.setReservationId(reservationId));
		reservations.getAvailableReservations().forEach(dao::createAvailableReservation);
		
		return "redirect:/list?event=" + Event.RESERVATION_CREATED.getName();
	}
	
	@GetMapping("/delete_reservation")
	public String deleteReservation(@RequestParam(name="id", required=true) int reservationId) {
		
		dao.deleteReservation(reservationId);
		return "redirect:/list?event=" + Event.RESERVATION_DELETED.getName();
	}
}
