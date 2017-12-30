package com.example.app.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.example.app.core.repository.Dao;
import com.example.app.homepage.LoginModel;
import com.example.app.homepage.UserModel;


public class NavigationBarController {
	private HttpSession httpSession;
	private Model model;
	private Dao dao;
	
	private List<Link> navigationPath = new ArrayList<>();
	
	public NavigationBarController(HttpSession httpSession, Model model, Dao dao) {
		this.httpSession = httpSession;
		this.model = model;
		this.dao = dao;
	}
	
	public void addBreadcrumb(String address, String description) {
		navigationPath.add(new Link(address, description));
	}
	
	public void update() {
		Object login = httpSession.getAttribute("login");
		if (login != null) {
			model.addAttribute("logged", true);
		} else {
			model.addAttribute("logged", false);
			model.addAttribute("login_form_model", new LoginModel(dao));
		}
		
		UserModel user = dao.getUser((String) login);
		model.addAttribute("user", user);
		model.addAttribute("navigation", navigationPath);
	}
}
