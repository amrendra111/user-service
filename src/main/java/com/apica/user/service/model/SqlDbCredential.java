package com.apica.user.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SqlDbCredential {
	private String url;
	private String userName;
	private String password;
	private String maxPoolSize;

}
