package com.project.rental.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.rental.Entity.User;
import com.project.rental.Enum.Role;
import com.project.rental.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Create or update a user with a default role if not provided
    public User saveUser(User user) {
        return userRepository.save(user);	
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> authenticateUser(User savedUser) {
		
		String email = savedUser.getEmail();
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}



}
