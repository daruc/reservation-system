package com.example.app.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomepageController {

	@RequestMapping("/")
	public String showHomepage(Model model) {
		model.addAttribute("title", "nowy tytuł");
		return "homepage/homepage";
	}
	
	@RequestMapping("sign_up")
	public String showSignUp() {
		return "homepage/sign_up";
	}
	
	@RequestMapping("reserve/time")
	public String showReservationTime(Model model) {
		model.addAttribute("title", "Reserve time");
		return "reservation/time";
	}
}
