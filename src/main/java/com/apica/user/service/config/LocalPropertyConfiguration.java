package com.apica.user.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

import com.apica.user.service.model.SqlDbCredential;

@Profile("local")
//@Configuration
public class LocalPropertyConfiguration implements IPropertyConfiguration {

	@Value("${sql.url}")
	private String sqlUrl;

	@Value("${sql.user}")
	private String sqlUser;

	@Value("${sql.password}")
	private String sqlPassword;

	@Value("${sql.max.pool.size}")
	private String sqlMaxPoolSize;

	@Override
	public String getEnv() {
		return "local";
	}

	@Override
	public SqlDbCredential getSqlDbCredential() {
		return new SqlDbCredential(sqlUrl, sqlUser, sqlPassword, sqlMaxPoolSize);
	}
}
