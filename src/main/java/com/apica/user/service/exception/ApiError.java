package com.apica.user.service.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ApiError {
	private HttpStatus status;
	private String message;
	private String debugMessage;
	private List<ApiSubError> subErrors;

	void addSubError(ApiSubError subError) {
		if (subErrors == null) {
			subErrors = new ArrayList<>();
		}
		subErrors.add(subError);

	}

	ApiError(HttpStatus status, String message, String debugMessage) {
		this.status = status;
		this.message = message;
		this.debugMessage = debugMessage;
	}

	public void addValidationErrors(List<? extends ObjectError> fieldErrors) {
		fieldErrors.forEach(a -> this.addValidationErrors((FieldError) a));

	}

	void addValidationErrors(FieldError fieldError) {
		this.addSubError(
				new ApiSubError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()));
	}

}
