package com.project.rental.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.rental.Entity.Booking;
import com.project.rental.Repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    // Retrieve all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Retrieve a booking by its ID
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    // Save a new booking
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Delete a booking by its ID
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
