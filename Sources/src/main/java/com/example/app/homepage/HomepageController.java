package com.example.app.homepage;

import java.util.List;
import java.util.stream.Collectors;

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
import com.example.app.reservation.AvailableReservationModel;
import com.example.app.reservation.ReservationModel;

@Controller
public class HomepageController {
	
	private ApplicationController appController;
	private Dao dao;
	private HttpSession httpSession;
	
	@Autowired
	public HomepageController(ApplicationController appController, Dao dao, HttpSession httpSession) {
		this.appController = appController;
		this.dao = dao;
		this.httpSession = httpSession;
		
		setAccessLevels();
	}
	
	private void setAccessLevels() {
		appController.setMinAccessLevel("/create_user", AccessLevel.EVERYONE);
		appController.setMinAccessLevel("/login", AccessLevel.EVERYONE);
		appController.setMinAccessLevel("/logout", AccessLevel.EVERYONE);
		appController.setMinAccessLevel("/", AccessLevel.EVERYONE);
		appController.setMinAccessLevel("/sign_up", AccessLevel.EVERYONE);
	}
	
	@ModelAttribute
	public UserModel createCreateUserModel() {
		UserModel model = new UserModel();
		return model;
	}

	@PostMapping("/create_user")
	public String createUser(@ModelAttribute UserModel createUserModel) {
		Validator validator = new CreateUserValidator(createUserModel);
		
		if (validator.isValid()) {
			dao.createUser(createUserModel);
		}
		return "redirect:/?event=" + Event.USER_CREATED.getName();
	}
	
	@ModelAttribute
	public LoginModel createLoginModel() {
		return new LoginModel(dao);
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute LoginModel loginModel) {
		if (loginModel.isCorrect()) {
			httpSession.setAttribute("login", loginModel.getLogin());
			
			UserModel userModel = dao.getUser(loginModel.getLogin());
			httpSession.setAttribute("access_level", userModel.getAccessLevel());
		}
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout() {
		httpSession.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/")
	public String showHomepage(Model model, @RequestParam(name="event", required=false) String event) {
		if (event != null) {
			model.addAttribute("event", event);
		}
		
		model.addAttribute("title", "Reservation System");
		NavigationBarController navigationBar = new NavigationBarController(httpSession, model, dao);
		navigationBar.addBreadcrumb("/", "Home");
		navigationBar.update();
		
		String login = (String) httpSession.getAttribute("login");
		if (login != null) {
			int userId = dao.getUser(login).getId();
			List<ReservationModel> reservations = dao.getReservationsForUser(userId);
			
			List<ReservationsList> reservationsLists = reservations.stream()
					.map(r -> new ReservationsList(r, dao.getMadeReservations(r.getId(), userId)))
					.filter(r -> !r.getMadeReservations().isEmpty())
					.collect(Collectors.toList());
			
			model.addAttribute("reservations", reservationsLists);
		}
		
		
		return "homepage/homepage";
	}
	
	@RequestMapping("/sign_up")
	public String showSignUp(Model model) {
		model.addAttribute("title", "Reservation System - Sign up");
		
		NavigationBarController navigationBar = new NavigationBarController(httpSession, model, dao);
		navigationBar.addBreadcrumb("/", "Home");
		navigationBar.addBreadcrumb("sign_up", "Sign up");
		navigationBar.update();
		
		model.addAttribute("form_model", new UserModel());

		return "homepage/sign_up";
	}
	
	private static class ReservationsList {
		private ReservationModel reservation;
		private List<AvailableReservationModel> madeReservations;
		
		public ReservationsList(ReservationModel reservation, List<AvailableReservationModel> madeReservations) {
			this.reservation = reservation;
			this.madeReservations = madeReservations;
		}
		
		public ReservationModel getReservation() {
			return reservation;
		}
		
		public List<AvailableReservationModel> getMadeReservations() {
			return madeReservations;
		}
	}
	
}
