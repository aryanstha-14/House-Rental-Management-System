package com.project.rental.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.rental.Entity.Landlord;
import com.project.rental.Entity.Tenant;
import com.project.rental.Entity.User;
import com.project.rental.Enum.Role;
import com.project.rental.Repository.UserRepository;
import com.project.rental.dto.UserLoginDTO;
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


	//display register form
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";

	}
	
	//check if email and password exists or not and 
	//save user based on roles
	@PostMapping("/register")
	public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) throws Exception {
	    
	    if (user == null) {
	        redirectAttributes.addFlashAttribute("error", "User cannot be registered. Please fill out the form correctly.");
	        return "redirect:/register";
	    }

	    // Check if email already exists
	    Optional<User> isExistUser = userService.findByEmail(user.getEmail());
	    if (isExistUser.isPresent()) {
	        redirectAttributes.addFlashAttribute("error", "Email already exists. Please use a different email.");
	        return "redirect:/register";
	    }

	    // Save user
	    userService.saveUser(user);

	    // Save based on roles
	    if (user.getRole().equals(Role.LANDLORD)) {
	        Landlord landlord = landlordService.saveLandlord(user, user.getEmail());
	        if (landlord == null) {
	            redirectAttributes.addFlashAttribute("error", "Landlord cannot be registered.");
	            return "redirect:/register";
	        }
	        redirectAttributes.addFlashAttribute("landlord", landlord);
	        return "redirect:/landlord";
	        
	    } else if (user.getRole().equals(Role.TENANT)) {
	        Tenant tenant = tenantService.saveTenant(user);
	        if (tenant == null) {
	            redirectAttributes.addFlashAttribute("error", "Tenant cannot be registered.");
	            return "redirect:/register";
	        }
	        redirectAttributes.addFlashAttribute("tenant", tenant);
	        return "redirect:/tenant";
	        
	    }

	    // Default case
	    redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
	    return "redirect:/register";
	}



	//Logging system
	
	@GetMapping("/")
	public String login() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute UserLoginDTO loginDto, Model model,
			HttpServletResponse resp,
			RedirectAttributes redirectAttributes) throws Exception {
	    
		// Step 1: Check if the user exists by email
	    Optional<User> optionalUser = userService.findByEmail(loginDto.getEmail());
	    if (optionalUser.isPresent()) {
	    	
	        User user = optionalUser.get();// Retrieves the User object if present
	        
	        // Step 2: Verify the password 
	        if (user.getPassword().equals(loginDto.getPassword())) {
	        	
	            // Step 3: Set success message and create a session cookie
	            resp.addCookie(sessionGenerator.generateNewCookie(user));
	            //redirectAttributes.addFlashAttribute("message", "Login successful!");

	            // Step 4: Redirect based on the user role
	            if (Role.LANDLORD.equals(user.getRole())) {
	            	 Landlord landlord = landlordService.getLandlordByUser(user);
	            	redirectAttributes.addFlashAttribute("landlord", landlord);
	                return "redirect:/landlord";
	                
	            } else if (Role.TENANT.equals(user.getRole())) {
	            	  Tenant tenant = tenantService.getTenantByUser(user);
	            	redirectAttributes.addFlashAttribute("tenant", tenant);
	                return "redirect:/tenant";
	                
	            } else {
	                redirectAttributes.addFlashAttribute("error", "Unknown user role.");
	                return "redirect:/login";
	            }
	        }
	    }

	    // Step 5: If user is not found or password is incorrect
	    redirectAttributes.addFlashAttribute("error", "Invalid email or password.");
	    return "redirect:/login"; // Redirect back to login page with error message
	}


	
//some ideas//
//	// Get all users
//	@GetMapping("/api/users/")
//	public List<User> getAllUsers() {
//		return userService.getAllUsers();
//	}
//
//	// Get user by ID
//	@GetMapping("/api/users/{id}")
//	public Optional<User> getUserById(@PathVariable("id") Long id) {
//		return userService.getUserById(id);
//	}
//  @PostMapping("/api/users/register")
//  public String showRegi(@ModelAttribute User user, Model model) {
//  	try {
//  		User newuser = userService.saveUser(user);
//  	}catch(Exception e) {
//  		model.addAttribute("errorMessage", "Failed to create new User!");
//  	}
//  	return "index";
//  }
//	// Update user details
//	@PutMapping("/api/users/{id}")
//	@DeleteMapping("/api/users/{id}")
//	public void deleteUser(@PathVariable("id") Long id) {
//		userService.deleteUser(id);
//	}
	
}
