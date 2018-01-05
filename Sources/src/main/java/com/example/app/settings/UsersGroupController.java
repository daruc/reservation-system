package com.example.app.settings;

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
import com.example.app.core.appcontroller.AccessLevel;
import com.example.app.core.appcontroller.ApplicationController;
import com.example.app.core.repository.Dao;
import com.example.app.homepage.UserModel;

@Controller
public class UsersGroupController {
	
	private ApplicationController appController;
	private Dao dao;
	private HttpSession httpSession;
	
	@ModelAttribute
	public UsersGroupModel getUsersGroupModel() {
		return new UsersGroupModel();
	}
	
	@Autowired
	public UsersGroupController(ApplicationController appController, Dao dao, HttpSession httpSession) {
		this.appController = appController;
		this.dao = dao;
		this.httpSession = httpSession;
		
		setMinAccessLevels();
	}
	
	private void setMinAccessLevels() {
		appController.setMinAccessLevel("/settings/create_users_group", AccessLevel.ADMIN);
		appController.setMinAccessLevel("/users_group", AccessLevel.ADMIN);
		appController.setMinAccessLevel("/settings/users_groups_list", AccessLevel.ADMIN);
		appController.setMinAccessLevel("/settings/users_group_details", AccessLevel.ADMIN);
		appController.setMinAccessLevel("/settings/add_user_to_group", AccessLevel.ADMIN);
	}
	
	@GetMapping("/settings/create_users_group")
	public String createUsersGroupForm(Model model) {
		model.addAttribute("title", "Create Users Group");
		
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.addBreadcrumb("/settings/create_users_group", "Create Users Group");
		navigation.update();
		
		model.addAttribute("form_model", new UsersGroupModel());
		
		return "/settings/create_users_group";
	}
	
	@PostMapping("/users_group")
	public String createUsersGroup(@ModelAttribute UsersGroupModel model) {
		dao.createUsersGroup(model);
		return "redirect:/settings";
	}
	
	@GetMapping("/settings/users_groups_list")
	public String getUsersGroupsList(Model model) {
		model.addAttribute("title", "Users Groups List");
		
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.addBreadcrumb("/settings/users_groups_list", "Users Groups List");
		navigation.update();
		
		List<UsersGroupModel> usersGroups = dao.getAllUsersGroups();
		model.addAttribute("users_groups_list", usersGroups);
		return "/settings/users_groups_list";
	}
	
	@GetMapping("/settings/users_group_details")
	public String showUserGroupDetails(Model model, @RequestParam(name="id", required=true) int userGroupId) {
		model.addAttribute("title", "Users Group Details");
		
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.addBreadcrumb("/settings/users_groups_list", "Users Group List");
		navigation.addBreadcrumb("/settings/users_group_details", "Users Group Details");
		navigation.update();
		
		UsersGroupModel usersGroup = dao.getUserGroup(userGroupId);
		model.addAttribute("users_group", usersGroup);
		
		List<UserModel> members = dao.getUsers(userGroupId);
		model.addAttribute("users_list", members);
		
		return "/settings/user_group_details";
	}

	@GetMapping("/settings/add_user_to_group")
	public String showAddUserToGroup(Model model, @RequestParam(name="group_id", required=true) int groupId) {
		model.addAttribute("title", "Add user to group");
		
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.addBreadcrumb("/settings/users_groups_list", "Users Group List");
		navigation.addBreadcrumb("/settings/users_group_details", "Users Group Details");
		navigation.addBreadcrumb("/settings/add_user_to_group", "Add user to group");
		navigation.update();
		
		List<UserModel> users = dao.getAllUsersWithoutGroup();
		model.addAttribute("users", users);
		model.addAttribute("groupId", groupId);
		
		return "/settings/add_user_to_group";
	}
	
	@PostMapping("/settings/add_user_to_group")
	public String addUserToGroup(
			@RequestParam(name="checkboxes", required=true) List<Integer> userIds,
			@RequestParam(name="group_id", required=true) int groupId
			) {
		
		dao.addUsersToGroup(userIds, groupId);
		
		return "redirect:/settings/users_group_details?id=" + String.valueOf(groupId);
	}
}
