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

import com.example.app.core.Dao;

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
	public CreateUserModel createCreateUserModel() {
		CreateUserModel model = new CreateUserModel(dao);
		return model;
	}

	@PostMapping("create_user")
	public String createUser(@ModelAttribute CreateUserModel createUserModel) {
		createUserModel.createUser();
		return "redirect:/";
	}
	
	@ModelAttribute
	public LoginModel createLoginModel() {
		return new LoginModel(dao);
	}
	
	@PostMapping("login")
	public String login(@ModelAttribute LoginModel loginModel) {
		if (loginModel.isCorrect()) {
			httpSession.setAttribute("is_logged", true);
			httpSession.setAttribute("login", loginModel.getLogin());
		}
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String logout() {
		httpSession.removeAttribute("is_logged");
		httpSession.removeAttribute("login");
		return "redirect:/";
	}
	
	//==============================================================
	@RequestMapping("/")
	public String showHomepage(Model model) {
		model.addAttribute("title", "Reserve time");
		Object isLogged = httpSession.getAttribute("is_logged");
		if (isLogged != null) {
			model.addAttribute("logged", (Boolean) isLogged);
		} else {
			model.addAttribute("logged", false);
		}
		Link home = new Link();
		home.address = "/addr/to/home";
		home.description = "Home";
		List<Link> navigation = Arrays.asList(home, home, home);
		model.addAttribute("navigation", navigation);
		User user = new User();
		user.name = (String) httpSession.getAttribute("login");
		user.surname = "surname error!";
		model.addAttribute("user", user);
		model.addAttribute("login_form_model", new LoginModel(dao));
		return "homepage/homepage";
	}
	
	@RequestMapping("/sign_up")
	public String showSignUp(Model model) {
		model.addAttribute("title", "Reserve time");
		model.addAttribute("logged", false);
		Link home = new Link();
		home.address = "/addr/to/home";
		home.description = "Home";
		List<Link> navigation = Arrays.asList(home, home, home);
		model.addAttribute("navigation", navigation);
		User user = new User();
		user.name = "Dariusz";
		user.surname = "Ruchała";
		model.addAttribute("user", user);
		model.addAttribute("form_model", new CreateUserModel(dao));
		model.addAttribute("login_form_model", new LoginModel(dao));

		return "homepage/sign_up";
	}
	
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
	
	public class Link {
		public String address;
		public String description;
	}
	
	public class User {
		public String name;
		public String surname;
		public String getFullname() {
			return name + ' ' + surname;
		}
	}
	
}
