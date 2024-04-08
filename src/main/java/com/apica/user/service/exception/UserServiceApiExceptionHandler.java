package com.apica.user.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UserServiceApiExceptionHandler {

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public ResponseEntity<Object> handleException(HttpMessageNotReadableException ex) {

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "JSON parse Error", ex.getLocalizedMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler({ NoHandlerFoundException.class })
	public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
			HttpServletRequest httpServletRequest) {
		System.out.println("No handler found exception");
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getLocalizedMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Validation Error", null);
		apiError.addValidationErrors(ex.getBindingResult().getAllErrors());

		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(value = { UserServiceApiException.class })
	public ResponseEntity<Object> handleApiException(UserServiceApiException ex) {

		ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), ex.getLocalizedMessage());
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {

		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler(value = { UserServiceApiValidationException.class })
	public ResponseEntity<Object> handleValidationException(UserServiceApiValidationException validationException) {

		ApiError apiError = new ApiError(validationException.getStatus(), validationException.getMessage(),
				validationException.getLocalizedMessage());
		apiError.addValidationErrors(validationException.getFieldErrors());
		return buildResponseEntity(apiError);
	}

}
