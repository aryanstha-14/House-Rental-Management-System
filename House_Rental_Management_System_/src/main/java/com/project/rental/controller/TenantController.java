package com.project.rental.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.rental.Entity.House;
import com.project.rental.Entity.Tenant;
import com.project.rental.dto.HouseResponseDTO;
import com.project.rental.service.HouseService;
import com.project.rental.service.TenantService;

@Controller
public class TenantController {

    @Autowired
    private TenantService tenantService;

    
    @Autowired
    private HouseService houseService;
    

    @GetMapping("/tenant")
    public String bookHousePage(Model model, @ModelAttribute("tenant") Tenant tenant) {
        List<HouseResponseDTO> res = new ArrayList<>();
        List<House> houses = houseService.getAllHouses();

        for (House house : houses) {
            if (house.isAvailable()) {
                HouseResponseDTO houseDto = new HouseResponseDTO();

                // Check if the landlord is not null before accessing its properties
                if (house.getLandlord() != null) {
                    houseDto.setEmail(house.getLandlord().getEmail());
                } else {
                    houseDto.setEmail("No email available");
                }
                houseDto.setId(house.getId());
                houseDto.setAddress(house.getAddress());
                houseDto.setImage(house.getImage());
                houseDto.setName(house.getName());
                houseDto.setPrice(house.getPrice());
                houseDto.setAvailable(house.isAvailable());
                
                res.add(houseDto);
            }
        }

        model.addAttribute("houses", res);
        
        return "Tenant-Detail";
    }

    @PostMapping("/houses/booknow/{id}")
    public String bookHouse(@PathVariable("id") Long houseId, Model model) {
        System.out.println("House ID: " + houseId);  // Debugging output

        // Find the house by its ID
        House house = houseService.findHouseById(houseId);
        
        // Check if the house exists and is available
        if (house != null && house.isAvailable()) {
            house.setAvailable(false);  // Mark the house as booked
            houseService.saveHouse(house);  // Save the updated house

            model.addAttribute("message", "House successfully booked!");
        } else {
            model.addAttribute("message", "Booking failed. House not available.");
        }

        // Prepare the updated house list after booking
        List<HouseResponseDTO> res = new ArrayList<>();
        List<House> houses = houseService.getAllHouses();

        for (House h : houses) {
            HouseResponseDTO houseDto = new HouseResponseDTO();

            // Check if the landlord is not null before accessing its properties
            if (h.getLandlord() != null) {
                houseDto.setEmail(h.getLandlord().getEmail());
            } else {
                houseDto.setEmail("No email available");
            }

            houseDto.setAddress(h.getAddress());
            houseDto.setImage(h.getImage());
            houseDto.setName(h.getName());
            houseDto.setPrice(h.getPrice());
            houseDto.setAvailable(h.isAvailable());

            res.add(houseDto);
        }

        model.addAttribute("houses", res);
        return "redirect:/tenant";  // Return the updated page with house availability status
    }

    

}
