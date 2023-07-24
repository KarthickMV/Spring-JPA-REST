package com.example.demo.dbTwo.service;

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
	
	public User addUser(User user) {
		user = userRepository.save(user);
		return user;
	}
	
	public Page<User> fetchAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
}
