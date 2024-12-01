package com.project.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.rental.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
//	@GetMapping("/")
//	public String initialPage(Model model) {
//		model.addAttribute("user", new User());
//		return "redirect:/login";
//	}
	
    
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
//  @GetMapping("/login")
//  public String showLogin(Model model) {
//  	model.addAttribute("user", new User());
//		return "Login";
//  	
//  }
//	
	@GetMapping("/get-landlords")
	public String landlords() {
		return "landlords";
	}
	
}
