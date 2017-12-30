package com.example.app.reservation;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.core.NavigationBarController;
import com.example.app.core.repository.Dao;

@Controller
public class MakeReservationController {
	
	private HttpSession httpSession;
	private Dao dao;
	
	@Autowired
	public MakeReservationController(HttpSession httpSession, Dao dao) {
		this.dao = dao;
		this.httpSession = httpSession;
	}
	
	@GetMapping("/make_reservation")
	public String showMakeReservation(@RequestParam(name="id", required=true) int id,
			Model model) {
		
		model.addAttribute("title", "Make reservation");
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/list", "Reservations list");
		navigation.addBreadcrumb("/make_reservation", "Make reservation");
		navigation.update();
		
		ReservationModel reservation = dao.getReservation(id);
		model.addAttribute("reservation", reservation);
		
		List<AvailableReservationModel> availableReservations = dao.getAvailableReservations(id);
		model.addAttribute("av_reservations", availableReservations);
		
		return "/reservation/make_reservation";
	}
	
	@GetMapping("/reserve")
	public String makeReservations(@RequestParam(name="id", required=true) int availableReservationId,
			Model model) {
		String login = (String) httpSession.getAttribute("login");
		int userId = dao.getUser(login).getId();
		dao.makeReservation(userId, availableReservationId);
		return "redirect:/list";
	}

}
