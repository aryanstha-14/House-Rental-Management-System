package com.project.rental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.rental.Entity.House;
import com.project.rental.Entity.Landlord;
import com.project.rental.service.HouseService;
import com.project.rental.service.LandlordService;

@Controller
public class LandlordController {

    @Autowired
    private LandlordService landlordService;
    
    @Autowired
    private HouseService houseService;

    // Show landlord form and list (HTML structure handles displaying list dynamically)
    @GetMapping("/landlord")
    public String showLandlordPage(Model model, @ModelAttribute("landlord") Landlord landlord ) {
    	

        // Add the list of landlords to the model
        model.addAttribute("landlord", landlord);
        
        System.out.println(landlord);
        return "index"; // Render landlords.html
    }

    // Display houses of a specific landlord
    @GetMapping("/landlord/{id}/houses")
    public String getLandlordHouses(@PathVariable("id") Long id, Model model) {
    	
    	System.out.println(id);
        Landlord landlord = landlordService.getLandlordById(id);
        System.out.println(landlord);
        if (landlord == null) {
            return "redirect:/landlords"; // Redirect if landlord is not found
        }
        
        
        model.addAttribute("landlord", landlord);
        model.addAttribute("houses", houseService.getHousesByLandlord(landlord));
        List<House> houses =  houseService.getHousesByLandlord(landlord);
        System.out.println(houses);
        return "landlordHouses";
    }
    
    
    // Handle form submission to add a landlord
//    @PostMapping("/landlords")
//    public String createLandlord(@ModelAttribute Landlord landlord) {
//        // Save the landlord using the service
//        landlordService.saveLandlord(landlord);
//
//        // Log the submitted data for debugging
//        System.out.println("Landlord saved: " + landlord);
//
//        // Redirect back to the same page to show the updated list
//        return "redirect:/landlords/all";
//    }
    
 // Display all landlords
    @GetMapping("/landlords/all")
    public String getAllLandlords(Model model) {
        model.addAttribute("landlords", landlordService.getAllLandlords());
        return "Landlords-Detail";
    }
    
    
   

}
