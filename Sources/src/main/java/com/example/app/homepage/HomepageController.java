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

import com.example.app.core.NavigationBarController;
import com.example.app.core.repository.Dao;
import com.example.app.reservation.AvailableReservationModel;
import com.example.app.reservation.ReservationModel;

@Controller
public class HomepageController {
	
	private Dao dao;
	private HttpSession httpSession;
	
	@Autowired
	public HomepageController(Dao dao, HttpSession httpSession) {
		this.dao = dao;
		this.httpSession = httpSession;
	}
	
	@ModelAttribute
	public UserModel createCreateUserModel() {
		UserModel model = new UserModel();
		return model;
	}

	@PostMapping("create_user")
	public String createUser(@ModelAttribute UserModel createUserModel) {
		dao.createUser(createUserModel);
		return "redirect:/";
	}
	
	@ModelAttribute
	public LoginModel createLoginModel() {
		return new LoginModel(dao);
	}
	
	@PostMapping("login")
	public String login(@ModelAttribute LoginModel loginModel) {
		if (loginModel.isCorrect()) {
			httpSession.setAttribute("login", loginModel.getLogin());
		}
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String logout() {
		httpSession.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/")
	public String showHomepage(Model model) {
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
