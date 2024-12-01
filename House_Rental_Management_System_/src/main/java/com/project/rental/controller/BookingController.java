package com.project.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.project.rental.service.BookingService;
import com.project.rental.service.HouseService;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    
    @Autowired
    private HouseService houseService;

    

    
}
