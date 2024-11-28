package com.project.rental.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.rental.Entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{	

}
