package com.project.rental.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.rental.Entity.House;
import com.project.rental.Entity.Tenant;
import com.project.rental.service.HouseService;
import com.project.rental.service.TenantService;

@Controller
public class TenantController {

    @Autowired
    private TenantService tenantService;

    
    @Autowired
    private HouseService houseService;
    

    
    @GetMapping("/tenant")
    public String bookHousePage(Model model) {
	   List<HouseResponseDTO> res=new ArrayList<>();
	   List<House> houses=houseService.getAllHouses();
	   for(House house:houses) {
		   if(house.isAvailable()) {
		   HouseResponseDTO houseDto=new HouseResponseDTO();
		   houseDto.setEmail(house.getLandlord().getEmail());
		   houseDto.setAddress(house.getAddress());
		   houseDto.setImage(house.getImage());
		   houseDto.setName(house.getName());
		   houseDto.setPrice(house.getPrice());
		   houseDto.setAvailable(house.isAvailable());
		   res.add(houseDto);
		   }
	   }
	   model.addAttribute("houses",res);
	   return "Tenant-Detail";
	   
   }
    
    
    
    

    

}
