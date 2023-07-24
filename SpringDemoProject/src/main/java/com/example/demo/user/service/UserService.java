package com.example.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dbTwo.model.User;
import com.example.demo.dbTwo.repository.UserRepository;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	public void addUser() {
		User user = new User();
		user.setEmail("karthick@gmail.com");
		user.setUserName("Karthick");
		user.setId(0);
		user=userRepository.save(user);
		System.out.println("Hi there from userService");
	}
	
	public Page<User> fetchAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
}
