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
import com.example.app.core.repository.Dao;

@Controller
public class ResourceController {
	
	private HttpSession httpSession;
	private Dao dao;
	
	@Autowired
	public ResourceController(HttpSession httpSession, Dao dao) {
		this.httpSession = httpSession;
		this.dao = dao;
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
		dao.createResource(resource);
		return "redirect:/settings";
	}
	
	@GetMapping("/settings/resources_list")
	public String showResources(Model model) {
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
		navigation.addBreadcrumb("/settings/resource_details", "Resource details");
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
		return "redirect:/settings/resources_list";
	}
}
