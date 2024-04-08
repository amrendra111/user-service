package com.apica.user.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.apica.user.service.exception.UserServiceApiException;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public RoleHierarchy roleHierarchy() {
//		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//		String hierarchy = "ROLE_ADMIN > ROLE_USER";
//		roleHierarchy.setHierarchy(hierarchy);
//		return roleHierarchy;
//	}
//
//	@Bean
//	public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler() {
//		DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
//		expressionHandler.setRoleHierarchy(roleHierarchy());
//		return expressionHandler;
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		try {
			return http.csrf().disable().authorizeHttpRequests().requestMatchers("/users/**").permitAll()
					.requestMatchers("/admin/**").hasAnyRole("ADMIN").requestMatchers(HttpMethod.GET, "/admin")
					.hasAuthority("READ_PRIVILEGE").requestMatchers(HttpMethod.PUT, "/admin")
					.hasAuthority("WRITE_PRIVILEGE").requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
					.requestMatchers(HttpMethod.DELETE, "/admin").hasAuthority("WRITE_PRIVILEGE").anyRequest()
					.authenticated().and().formLogin().and().build();
		} catch (Exception e) {
			throw new UserServiceApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
