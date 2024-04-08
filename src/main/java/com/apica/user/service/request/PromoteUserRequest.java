package com.apica.user.service.request;

import com.apica.user.service.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromoteUserRequest {
	private Integer userId;
	private Role role;
}
