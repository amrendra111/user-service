package com.apica.user.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apica.user.service.request.UserUpsertRequest;
import com.apica.user.service.response.UserResponse;
import com.apica.user.service.service.IUserService;

import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("users")
@Api(tags = "User Controller")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping("/get/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable(name = "id") Integer id) {
		log.info("request to get in controller : {}", id);
		UserResponse response = userService.getUser(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Integer> registerUser(@Valid @RequestBody UserUpsertRequest request) {
		log.info("request arrived in controller : {}", request);
		Integer response = userService.addUser(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/add/{id}")
	public ResponseEntity<Integer> updateUser(@Valid @RequestBody UserUpsertRequest request,
			@PathVariable(name = "id") Integer id) {
		log.info("request to update in controller : {}", request);
		Integer response = userService.updateUser(request, id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Integer id) {
		log.info("request to delete in controller : {}", id);
		Integer response = userService.deleteUser(id);
		return new ResponseEntity<>("user " + response + " deleted", HttpStatus.OK);
	}

}
