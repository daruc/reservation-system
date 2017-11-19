package com.example.app.list;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.core.Dao;
import com.example.app.core.NavigationBarController;

@Controller
public class ListController {
	
	private Dao dao;
	private HttpSession httpSession;
	
	@Autowired
	ListController(Dao dao, HttpSession httpSession) {
		this.dao = dao;
		this.httpSession = httpSession;
	}
	
	@RequestMapping("/list")
	public String showList(Model model) {
		if (httpSession.getAttribute("login") == null) {
			return "redirect:/";
		}
		
		model.addAttribute("title", "List");
		
		NavigationBarController navigation = new NavigationBarController(httpSession, model, dao);
		navigation.addBreadcrumb("/", "Home");
		navigation.addBreadcrumb("/list", "List");
		navigation.update();
		
		return "list/list";
	}

}
