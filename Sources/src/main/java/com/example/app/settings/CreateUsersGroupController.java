package com.example.app.settings;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.core.Dao;
import com.example.app.core.NavigationBarController;

@Controller
public class CreateUsersGroupController {
	
	private Dao dao;
	private HttpSession httpSession;
	
	@ModelAttribute
	public UsersGroupModel getUsersGroupModel() {
		return new UsersGroupModel(dao);
	}
	
	@Autowired
	public CreateUsersGroupController(Dao dao, HttpSession httpSession) {
		this.dao = dao;
		this.httpSession = httpSession;
	}
	
	@GetMapping("/settings/create_users_group")
	public String createUsersGroupForm(Model model) {
		model.addAttribute("title", "Create Users Group");
		
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.addBreadcrumb("/settings/create_users_group", "Create Users Group");
		navigation.update();
		
		model.addAttribute("form_model", new UsersGroupModel(dao));
		
		return "settings/create_users_group";
	}
	
	@PostMapping("users_group")
	public String createUsersGroup(@ModelAttribute UsersGroupModel model) {
		model.create();
		return "redirect:/settings";
	}

}
