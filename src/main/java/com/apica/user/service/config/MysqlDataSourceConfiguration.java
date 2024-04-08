package com.apica.user.service.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.apica.user.service.model.SqlDbCredential;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

//@Configuration
@Slf4j
public class MysqlDataSourceConfiguration {

	@Autowired
	IPropertyConfiguration property;

	@Bean
	public DataSource dataSource() {
		SqlDbCredential creds = property.getSqlDbCredential();

		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(creds.getUrl());
		config.setUsername(creds.getUserName());
		config.setPassword(creds.getPassword());
		config.setMaximumPoolSize(Integer.valueOf(creds.getMaxPoolSize()));
		log.info("MYSQL Url {}", creds.getUrl());

		return new HikariDataSource(config);
	}

}
