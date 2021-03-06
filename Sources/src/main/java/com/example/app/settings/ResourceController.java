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

import com.example.app.core.Event;
import com.example.app.core.NavigationBarController;
import com.example.app.core.Validator;
import com.example.app.core.appcontroller.AccessLevel;
import com.example.app.core.appcontroller.ApplicationController;
import com.example.app.core.repository.Dao;

@Controller
public class ResourceController {
	
	private ApplicationController appController;
	private HttpSession httpSession;
	private Dao dao;
	
	@Autowired
	public ResourceController(ApplicationController appController, HttpSession httpSession, Dao dao) {
		this.appController = appController;
		this.httpSession = httpSession;
		this.dao = dao;
		
		setMinAccessLevels();
	}
	
	private void setMinAccessLevels() {
		appController.setMinAccessLevel("/settings/create_resource", AccessLevel.ADMIN);
		appController.setMinAccessLevel("/settings/resources_list", AccessLevel.ADMIN);
		appController.setMinAccessLevel("/settings/resource_details", AccessLevel.ADMIN);
		appController.setMinAccessLevel("/delete_resource", AccessLevel.ADMIN);
	}
	
	@GetMapping("/settings/create_resource")
	public String showCreateResource(Model model) {
		model.addAttribute("title", "Create Resource");
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.addBreadcrumb("/settings/create_resource", "Create Resource");
		navigation.update();
		
		model.addAttribute("resource", new ResourceModel());
		
		return "/settings/create_resource";
	}
	
	@PostMapping("/settings/create_resource")
	public String createResource(@ModelAttribute ResourceModel resource) {
		Validator validator = new CreateResourceValidator(resource);
		
		if (validator.isValid()) {
			dao.createResource(resource);
			return "redirect:/settings?event=" + Event.RESOURCE_CREATED.getName();
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/settings/resources_list")
	public String showResources(Model model, @RequestParam(name="event", required=false) String event) {
		if (event != null) {
			model.addAttribute("event", event);
		}
		
		model.addAttribute("title", "Resources list");
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.addBreadcrumb("/settings/resources_list", "Resources list");
		navigation.update();
		
		List<ResourceModel> resources = dao.getAllResources();
		model.addAttribute("resources_list", resources);
		
		return "/settings/resources_list";
	}
	
	@GetMapping("/settings/resource_details")
	public String showResourceDetails(@RequestParam(name="id", required=true) int id, Model model) {
		model.addAttribute("title", "Resource details");
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/settings", "Settings");
		navigation.addBreadcrumb("/settings/resources_list", "Resources list");
		navigation.addBreadcrumb("/settings/resource_details?id=" + id, "Resource details");
		navigation.update();
		
		ResourceModel resource = dao.getResource(id);
		model.addAttribute("id", id);
		model.addAttribute("name", resource.getName());
		model.addAttribute("description", resource.getDescription());
		
		return "/settings/resource_details";
	}
	
	@GetMapping("/delete_resource")
	public String deleteResource(@RequestParam(name="id", required=true) int id) {
		
		dao.deleteResource(id);
		return "redirect:/settings/resources_list?event=" + Event.RESOURCE_DELETED.getName();
	}
}
