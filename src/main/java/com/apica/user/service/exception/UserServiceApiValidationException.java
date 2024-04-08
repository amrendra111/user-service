package com.apica.user.service.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceApiValidationException extends Exception {

	private static final long serialVersionUID = 2243242796923417410L;
	private HttpStatus status;
	private String message;
	private List<FieldError> fieldErrors = new ArrayList<>();

}
