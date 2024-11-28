package com.project.rental.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.rental.Entity.User;
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
