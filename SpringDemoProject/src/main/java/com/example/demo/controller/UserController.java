package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dbTwo.model.User;
import com.example.demo.user.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/userList",method = RequestMethod.GET)
	public ResponseEntity<PagedModel<User>> get(Pageable pageable,	PagedResourcesAssembler assembler){
		Page<User> users = userService.fetchAllUsers(pageable); 
		return ResponseEntity.ok(assembler.toModel(users));
	}
	
	@PostMapping(path="/usersList/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") User user){
		return ResponseEntity.ok(user);
	}
	
}
