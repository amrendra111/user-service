package com.apica.user.service.response;

import java.util.Set;

import com.apica.user.service.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Set<Role> roles;
}
