package com.apica.user.service.exception;

import org.springframework.http.HttpStatus;

public class UserServiceApiException extends RuntimeException {

	private static final long serialVersionUID = -2667164554068478071L;
	private HttpStatus status;

	public UserServiceApiException(String message, HttpStatus status) {
		super(message);
		this.setStatus(status);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
