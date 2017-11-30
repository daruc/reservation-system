package com.example.app.reservation;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.core.Dao;
import com.example.app.core.NavigationBarController;

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
		
		return "/reservation/make_reservation";
	}

}
