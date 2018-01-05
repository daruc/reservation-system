package com.example.app.settings;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.core.NavigationBarController;
import com.example.app.core.appcontroller.AccessLevel;
import com.example.app.core.appcontroller.ApplicationController;
import com.example.app.core.repository.Dao;

@Controller
public class SettingsController {

	private ApplicationController appController;
	private Dao dao;
	private HttpSession httpSession;
	
	public SettingsController(ApplicationController appController, Dao dao, HttpSession httpSession) {
		this.appController = appController;
		this.dao = dao;
		this.httpSession = httpSession;
		
		setMinAccessLevels();
	}
	
	private void setMinAccessLevels() {
		appController.setMinAccessLevel("/settings", AccessLevel.ADMIN);
	}
	
	@RequestMapping("/settings")
	public String showSettings(Model model) {
		if (httpSession.getAttribute("login") == null) {
			return "redirect:/";
		}
		
		model.addAttribute("title", "Settings");
		
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.update();
		
		return "settings/settings";
	}
}
