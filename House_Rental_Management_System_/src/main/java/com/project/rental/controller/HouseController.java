package com.project.rental.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.rental.Entity.House;
import com.project.rental.Entity.Landlord;
import com.project.rental.service.HouseService;
import com.project.rental.service.LandlordService;

@Controller

public class HouseController {
	
	public String imageuploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/images";


    @Autowired
    private HouseService houseService;
    
    
    @Autowired
    private LandlordService landlordSevice;

    @GetMapping("/house")
    public List<House> getAllHouses() {
        return houseService.getAllHouses();
    }

//    @GetMapping("/{id}")
//    public House getHouseById(@PathVariable Long id) {
//        return houseService.getHouseById(id);
//    }
    // Method to get all tenants
    // Handle request to display all tenants

    
    @GetMapping("/addNewHouse/{id}")
    public String showAddHouseForm(Model model,@PathVariable("id")Long id) {
        model.addAttribute("house", new House());
        Landlord landlord = landlordSevice.getLandlordById(id);
        model.addAttribute("landlord", landlord);

        return "addNewHouse";
    }



    @PostMapping("/addNewHouse")
    public String createHouse(@RequestParam ("address") String  address,
    						 @RequestParam ("name") String  name,
    						 @RequestParam("image") MultipartFile file,
    						 @RequestParam ("price") double  price,
    						 @RequestParam ("id") Long id) throws Exception {
    	
    	Landlord landlord=landlordSevice.getLandlordById(id);
    	if(landlord==null) throw new Exception("landlord is empty");
    	 // Handle file upload
        String originalFilename = file.getOriginalFilename();
        String filename = System.currentTimeMillis() + "_" + originalFilename;
        Path fileNameAndPath = Paths.get(imageuploadDir, filename);
        
        // Save the file
        Files.write(fileNameAndPath, file.getBytes());
        House house=new House();
        house.setAddress(address);
        house.setImage(filename);
        house.setAvailable(true);
        house.setName(name);
        house.setPrice(price);
        house.setLandlord(landlord);
        
        houseService.saveHouse(house);
      
        return "redirect:/addNewHouse/" + id;
    	
    }

    @DeleteMapping("/{id}")
    public void deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
    }
}
