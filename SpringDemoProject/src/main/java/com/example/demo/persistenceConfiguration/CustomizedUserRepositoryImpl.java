package com.example.demo.persistenceConfiguration;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dbTwo.model.User;

import jakarta.persistence.EntityManager;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository{
	
	@Autowired
	private EntityManager em;
	
	@Override
	public void someUserManipulation(User user) {
		// TODO Auto-generated method stub
		
	}

}
