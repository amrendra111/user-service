package com.apica.user.service.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apica.user.service.entity.Role;
import com.apica.user.service.entity.User;
import com.apica.user.service.exception.UserServiceApiException;
import com.apica.user.service.kafka.producer.KafkaMesage;
import com.apica.user.service.kafka.producer.KafkaMessageProducer;
import com.apica.user.service.repository.RoleRepository;
import com.apica.user.service.repository.UserRepository;
import com.apica.user.service.request.PromoteUserRequest;
import com.apica.user.service.request.UserUpsertRequest;
import com.apica.user.service.response.UserResponse;
import com.apica.user.service.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private KafkaMessageProducer producer;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public Integer addUser(UserUpsertRequest request) {
		try {
			User user = new User();
			user.setEmail(request.getEmail());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			user.setPhoneNumber(request.getPhoneNumber());
			user.setEnabled(true);
			user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			User savedItem = userRepository.save(user);
			producer.sendMessage("user-events", mapper
					.writeValueAsString(new KafkaMesage(savedItem.getId(), "create", LocalDateTime.now().toString())));
			return savedItem.getId();
		} catch (Exception e) {
			throw new UserServiceApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Integer updateUser(UserUpsertRequest request, Integer id) {
		try {
			Optional<User> dbRecord = userRepository.findById(id);
			if (dbRecord.isEmpty())
				throw new UserServiceApiException("User not found for id :" + id, HttpStatus.NOT_FOUND);
			User user = dbRecord.get();
			user.setEmail(request.getEmail());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			user.setPhoneNumber(request.getPhoneNumber());
			User savedItem = userRepository.save(user);
			producer.sendMessage("user-events",
					mapper.writeValueAsString(new KafkaMesage(id, "update", LocalDateTime.now().toString())));
			return savedItem.getId();
		} catch (Exception e) {
			throw new UserServiceApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public UserResponse getUser(Integer id) {
		try {
			Optional<User> dbRecord = userRepository.findById(id);
			if (dbRecord.isEmpty())
				throw new UserServiceApiException("User not found for id :" + id, HttpStatus.NOT_FOUND);
			UserResponse response = new UserResponse();
			BeanUtils.copyProperties(dbRecord.get(), response);
			producer.sendMessage("user-events",
					mapper.writeValueAsString(new KafkaMesage(id, "get", LocalDateTime.now().toString())));
			return response;
		} catch (Exception e) {
			throw new UserServiceApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Integer deleteUser(Integer id) {
		try {
			Optional<User> dbRecord = userRepository.findById(id);
			if (dbRecord.isEmpty())
				throw new UserServiceApiException("User not found for id :" + id, HttpStatus.NOT_FOUND);
			dbRecord.get().setRoles(Collections.emptySet());
			userRepository.delete(dbRecord.get());
			producer.sendMessage("user-events",
					mapper.writeValueAsString(new KafkaMesage(id, "delete", LocalDateTime.now().toString())));
			return id;
		} catch (Exception e) {
			throw new UserServiceApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<UserResponse> getAllUsers() {
		try {
			List<UserResponse> response = new ArrayList<>();
			userRepository.findAll().forEach(t -> {
				UserResponse user = new UserResponse();
				BeanUtils.copyProperties(t, user);
				response.add(user);
			});
			return response;
		} catch (Exception e) {
			throw new UserServiceApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public String promoteUser(PromoteUserRequest request) {
		Optional<User> user = userRepository.findById(request.getUserId());
		if (user.isEmpty())
			throw new UserServiceApiException("User not found for id :" + request.getUserId(), HttpStatus.NOT_FOUND);

		Role role = roleRepository.findByName("ROLE_" + request.getRole().name());
		user.get().setRoles(new HashSet<>(Arrays.asList(role)));
		userRepository.save(user.get());
		return request.getUserId() + "promoted successfully as " + request.getRole();
	}

}
