package com.example.app.settings;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.core.NavigationBarController;
import com.example.app.core.appcontroller.AccessLevel;
import com.example.app.core.appcontroller.ApplicationController;
import com.example.app.core.repository.Dao;
import com.example.app.homepage.UserModel;

@Controller
public class UsersController {
	
	private ApplicationController appController;
	private Dao dao;
	private HttpSession httpSession;
	
	public UsersController(ApplicationController appController, Dao dao, HttpSession httpSession) {
		this.appController = appController;
		this.dao = dao;
		this.httpSession = httpSession;
		
		setMinAccessLevels();
	}
	
	private void setMinAccessLevels() {
		appController.setMinAccessLevel("/settings/users_list", AccessLevel.ADMIN);
		appController.setMinAccessLevel("/settings/user_details", AccessLevel.ADMIN);
		appController.setMinAccessLevel("/delete_user", AccessLevel.ADMIN);
	}
	
	@RequestMapping("/settings/users_list")
	public String showUsersList(Model model) {
		model.addAttribute("title", "Users List");
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.addBreadcrumb("/settings/users_list", "Users List");
		navigation.update();
		
		
		List<UserModel> users = dao.getAllUsers();
		
		model.addAttribute("users", users);
		return "/settings/users_list";
	}
	
	@RequestMapping("/settings/user_details")
	public String showUserDetails(@RequestParam(name="id", required=true) int userId, Model model) {
		model.addAttribute("title", "User Details");
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.addBreadcrumb("/settings/users_list", "Users List");
		navigation.addBreadcrumb("/settings/user_details?id=" + userId, "User Details");
		navigation.update();
		
		UserModel user = dao.getUser(userId);
		model.addAttribute("user", user);
		
		return "/settings/user_details";
	}
	
	@GetMapping("/delete_user")
	public String deleteUser(@RequestParam(name="id", required=true) int userId, Model model) {
		dao.deleteUser(userId);
		return "redirect:/settings/users_list";
	}

}
