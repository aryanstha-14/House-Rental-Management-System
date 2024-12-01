package com.project.rental.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.rental.Entity.House;
import com.project.rental.Entity.Landlord;
import com.project.rental.Entity.Tenant;
import com.project.rental.Repository.HouseRepository;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;

    
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    public House getHouseById(Long id) {
        return houseRepository.findById(id).orElse(null);
    }

    public House findById(Long id) {
        return houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found with ID: " + id));
    }
    
    public House saveHouse(House house) {
        return houseRepository.save(house);
    }

    public void deleteHouse(Long id) {
        houseRepository.deleteById(id);
    }

	public List<House> getHousesByLandlord(Landlord landlord) {
		return houseRepository.getHousesByLandlord(landlord);
	}

	public House findHouseById(Long houseId) {
		return houseRepository.findHouseById(houseId);

	}

}
