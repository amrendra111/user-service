package com.apica.user.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apica.user.service.response.UserResponse;
import com.apica.user.service.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("manager")
public class ManagerController {

	@Autowired
	private IUserService userService;

	@GetMapping("/get/user-list")
	public ResponseEntity<List<UserResponse>> getAllUser() {
		log.info("fetching all the users...");
		List<UserResponse> response = userService.getAllUsers();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
