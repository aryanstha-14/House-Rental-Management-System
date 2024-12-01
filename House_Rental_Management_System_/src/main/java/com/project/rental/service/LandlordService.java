package com.project.rental.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.rental.Entity.Landlord;
import com.project.rental.Entity.User;
import com.project.rental.Repository.LandlordRepository;

@Service
public class LandlordService {

    @Autowired
    private LandlordRepository landlordRepository;

    // Method to retrieve all landlords from the database
    public List<Landlord> getAllLandlords() {
        return landlordRepository.findAll();
    }

    // Method to retrieve a landlord by its ID
    public Landlord getLandlordById(Long id) {
        Optional<Landlord> landlord = landlordRepository.findById(id);
        return landlord.orElse(null);
    }



    // Method to delete a landlord by its ID
    public void deleteLandlord(Long id) {
        landlordRepository.deleteById(id);
    }

	public Landlord getUserFromEmail(String email) {
	Landlord landLord=	landlordRepository.findByEmail(email);
		return landLord;
	}

	public Landlord saveLandlord(User user, String email) {
		Landlord landlord=new Landlord();
		landlord.setEmail(email);
		landlord.setUser(user);
		return landlordRepository.save(landlord);
		
	}

	public Landlord getLandlordByUser(User user) {
		return landlordRepository.findLandlordByUser(user);
	}

   	}

