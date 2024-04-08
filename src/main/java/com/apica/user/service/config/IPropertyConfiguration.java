package com.apica.user.service.config;

import com.apica.user.service.model.SqlDbCredential;

public interface IPropertyConfiguration {

	String getEnv();

	SqlDbCredential getSqlDbCredential();

}
