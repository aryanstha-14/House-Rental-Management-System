package com.project.rental.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.rental.Entity.House;
import com.project.rental.service.BookingService;
import com.project.rental.service.HouseService;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    
    @Autowired
    private HouseService houseService;

    

    
}
