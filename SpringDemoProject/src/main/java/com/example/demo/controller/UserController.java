package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dbTwo.model.ResponseText;
import com.example.demo.dbTwo.model.User;
import com.example.demo.dbTwo.service.UserService;

@RestController
@RequestMapping(path="/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/userList",method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedModel<User>> get(Pageable pageable,PagedResourcesAssembler assembler){
		Page<User> users = userService.fetchAllUsers(pageable); 
		return ResponseEntity.ok(assembler.toModel(users));
	}
	
	@GetMapping(path="/userList/{id}", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<User> getUser(@PathVariable("id") User user){
		return ResponseEntity.ok(user);
	}
	
	@RequestMapping(path="/addUser", method=RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user){
		return ResponseEntity.ok(userService.addUser(user));
	}
	
	@GetMapping(value="/content", produces=MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public ResponseText getDiffResponse(){
		return new ResponseText("user fetched");
	}
	
	@RequestMapping(path="/sortingPaging", method= RequestMethod.GET)
	public ResponseEntity<PagedModel<User>> getSortedUsers(PagedResourcesAssembler assembler,@SortDefault(sort="userName", direction=Sort.Direction.ASC)Pageable pageable){
		Page<User> users=userService.fetchAllUsers(pageable);
		return ResponseEntity.ok(assembler.toModel(users));
	}
	
	@RequestMapping(path="/Paging", method= RequestMethod.GET)
	public ResponseEntity<PagedModel<User>> getPagedUsers(PagedResourcesAssembler assembler,@PageableDefault(value=2, page=0)Pageable pageable){
		Page<User> users=userService.fetchAllUsers(pageable);
		return ResponseEntity.ok(assembler.toModel(users));
	}
}
