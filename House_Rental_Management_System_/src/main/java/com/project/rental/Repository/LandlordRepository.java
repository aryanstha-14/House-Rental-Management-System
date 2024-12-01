package com.project.rental.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.rental.Entity.Landlord;
import com.project.rental.Entity.User;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {

	Landlord findByEmail(String email);
    // You can define custom queries here if needed

	Landlord findLandlordByUser(User user);
}
