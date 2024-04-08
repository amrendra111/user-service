package com.apica.user.service.service;

import java.util.List;

import com.apica.user.service.request.PromoteUserRequest;
import com.apica.user.service.request.UserUpsertRequest;
import com.apica.user.service.response.UserResponse;

public interface IUserService {

	Integer addUser(UserUpsertRequest request);

	Integer updateUser(UserUpsertRequest request, Integer id);

	UserResponse getUser(Integer id);

	Integer deleteUser(Integer id);

	List<UserResponse> getAllUsers();

	String promoteUser(PromoteUserRequest request);

}
