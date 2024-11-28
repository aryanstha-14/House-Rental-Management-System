package com.project.rental.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.rental.Entity.Landlord;
import com.project.rental.Entity.Tenant;
import com.project.rental.Entity.User;
import com.project.rental.Enum.Role;
import com.project.rental.Repository.UserRepository;
import com.project.rental.service.LandlordService;
import com.project.rental.service.TenantService;
import com.project.rental.service.UserService;
import com.project.rental.utils.SessionGenerator;

import jakarta.servlet.http.HttpServletResponse;

//@RestController
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private LandlordService landlordService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private SessionGenerator sessionGenerator;

	// Get all users
	@GetMapping("/api/users/")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	// Get user by ID
	@GetMapping("/api/users/{id}")
	public Optional<User> getUserById(@PathVariable("id") Long id) {
		return userService.getUserById(id);
	}

	// Changes
	// Create a new user
	@PostMapping("/register")
	public String createUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) throws Exception {
		Optional<User> isExistUser = userService.findByEmail(user.getEmail());
		if (isExistUser.isPresent())
			throw new Exception("Email already exist");

		User savedUser = userService.saveUser(user);

		if (savedUser == null) {
			throw new Exception("User cannot be register");
		}
		

		
		if (user.getRole().equals(Role.LANDLORD)) {
			Landlord landlord = landlordService.saveLandlord(user, user.getEmail());
			if (landlord == null)
				throw new Exception("Landlord cannot be register");
			redirectAttributes.addFlashAttribute("landlord", landlord);
			return "redirect:/landlord";
		} else if (user.getRole().equals(Role.TENANT)) {
			Tenant tenant = tenantService.saveTenant(user);
			if (tenant == null)
				throw new Exception("Tenant cannot be register");
			return "redirect:/tenant";
		}

		return "index";
	}

	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";

	}

//    @PostMapping("/api/users/register")
//    public String showRegi(@ModelAttribute User user, Model model) {
//    	try {
//    		User newuser = userService.saveUser(user);
//    	}catch(Exception e) {
//    		model.addAttribute("errorMessage", "Failed to create new User!");
//    	}
//    	return "index";
//    }

	@GetMapping
	public String login() {

		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

//	@PostMapping("/login")
//	public String login(UserLoginDTO dto, Model model, HttpServletResponse resp) throws Exception {
//
////		System.out.println("email = " + dto.getEmail());
////		System.out.println("password = " + dto.getPassword());
//
//		// verify that user id & password is correct
//		// if so; create a cookie (session) and send it to client; redirect to dash board
//		// else show error message
//
//
//		Optional<User> optionalUser = userService.findByEmail(dto.getEmail());
//		System.out.println("Before Succesfully log in");
//		Optional<User> authenticatedUser = userService.authenticateUser(optionalUser);
//		if(authenticatedUser == null) {
//			
//			System.out.println();
//		}
//		if (optionalUser.isPresent()) {
//			User user = optionalUser.get();
//			if (user.getPassword().equals(dto.getPassword())) { // You should compare a hashed password here
//				
//				model.addAttribute("message", "Login successful!");
//
//				resp.addCookie(sessionGenerator.generateNewCookie(user));
//
//				if (user.getRole() == Role.LANDLORD) {
//					System.out.println("user is lanlord");
//					return "redirect:/landlord";
//					
//				} else if (user.getRole() == Role.TENANT) {
//					System.out.println("user is tenant");
//					
//					return "redirect:/tenant";
//				}
//				
//			}
//		}
//		System.out.println("Home Succesfully log in");
//		model.addAttribute("error", "Invalid email or password.");
//		return "redirect:/login"; // Return to login page with error message
//	}

	// Update user details
	@PutMapping("/api/users/{id}")
	@DeleteMapping("/api/users/{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
	}
	

}
