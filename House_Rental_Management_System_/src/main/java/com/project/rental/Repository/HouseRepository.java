package com.project.rental.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.rental.Entity.House;
import com.project.rental.Entity.Landlord;

public interface HouseRepository extends JpaRepository<House, Long>{

	List<House> getHousesByLandlord(Landlord landlord);

}
