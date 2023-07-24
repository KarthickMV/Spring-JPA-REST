package com.example.demo.dbOne.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dbOne.model.Student;
import com.example.demo.dbOne.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository userRepository;
	
	public void whenCreatingStudent() {
        Student student = new Student();
        student.setName("John");
        student.setEmail("john@test.com");
        student.setAge(20);
        student = userRepository.save(student);

        System.out.println("Hello there");

	}
	
}
