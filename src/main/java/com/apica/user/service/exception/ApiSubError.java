package com.apica.user.service.exception;

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
public class ApiSubError {
	private String object;
	private String field;
	private Object rejectedValue;
	private String messsage;

	ApiSubError(String object, String field, String message) {
		this.object = object;
		this.field = field;
		this.messsage = message;

	}

}
