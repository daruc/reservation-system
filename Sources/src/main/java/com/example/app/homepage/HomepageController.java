package com.example.app.homepage;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.core.Link;
import com.example.app.core.NavigationBarController;
import com.example.app.core.repository.Dao;
import com.example.app.core.repository.Repository;

@Controller
public class HomepageController {
	
	private Repository repository;
	private Dao dao;
	private HttpSession httpSession;
	
	@Autowired
	public HomepageController(Repository repository, Dao dao, HttpSession httpSession) {
		this.repository = repository;
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
	
	//==============================================================

	@RequestMapping("reserve/time")
	public String showReservationTime(Model model) {
		model.addAttribute("title", "Reserve time");
		model.addAttribute("logged", true);
		Link home = new Link();
		home.address = "/addr/to/home";
		home.description = "Home";
		List<Link> navigation = Arrays.asList(home, home, home);
		model.addAttribute("navigation", navigation);
		User user = new User();
		user.name = "Dariusz";
		user.surname = "Ruchała";
		model.addAttribute("user", user);
		
		return "reservation/time";
	}
	
	public class User {
		public String name;
		public String surname;
		public String getFullname() {
			return name + ' ' + surname;
		}
	}
	
}
