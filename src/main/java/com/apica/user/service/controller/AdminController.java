package com.apica.user.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apica.user.service.request.PromoteUserRequest;
import com.apica.user.service.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private IUserService userService;

	@PostMapping("/promote-user")
	public ResponseEntity<String> promoteUser(@RequestBody PromoteUserRequest request) {
		log.info("promoting user...");
		String response = userService.promoteUser(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
