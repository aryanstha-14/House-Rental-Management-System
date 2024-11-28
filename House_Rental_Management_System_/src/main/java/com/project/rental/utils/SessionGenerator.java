package com.project.rental.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.rental.Entity.User;
import com.project.rental.Repository.UserRepository;

import jakarta.servlet.http.Cookie;

@Service
public class SessionGenerator {

	@Autowired
	private UserRepository userRepository;

	private static String sampleSpace = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	private static int LEN = 20;

	public Cookie generateNewCookie(User user) {
		String session = generateSession();

		Cookie cookie = new Cookie("session", session);
		cookie.setPath("/"); // mysterious
		
		//store session to user's data
		
		user.setSession(session);
		userRepository.save(user);
		
		return cookie;
	}

	public String generateSession() {

		// step 1
		// generate a session randomly
		// check if that session already exists in the database
		// if already exist; collision occurred; goto step 1
		// give that session to client as cookie

		boolean isUnique = false;
		Random random = new Random();
		String session = "";

		while (!isUnique) {

			session = "";
			for (int i = 1; i <= LEN; i++) {
				int index = random.nextInt(sampleSpace.length());
				char c = sampleSpace.charAt(index);
				session = session + c;
			}

			if (!userRepository.existsBySession(session)) {
				isUnique = true;
			}

		}
		
		return session;
	}

}
