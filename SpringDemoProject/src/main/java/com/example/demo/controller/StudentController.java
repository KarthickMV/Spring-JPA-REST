package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dbOne.service.StudentService;

@RestController
public class StudentController {

	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping("/")
	public String hello() {
		studentService.whenCreatingStudent();
		return "Hello buddy";
	}
}
