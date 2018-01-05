package com.example.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.app.core.appcontroller.ApplicationController;
import com.example.app.core.appcontroller.AuthenticationInterceptor;
import com.example.app.core.repository.Dao;
import com.example.app.core.repository.Repository;

@Configuration
public class BeansConfiguration {

	@Bean
	@Autowired
	Dao getDato(DataSource dataSource) {
		return new Dao(dataSource);
	}
	
	@Bean
	@Autowired
	Repository getRepository(DataSource dataSource) {
		return new Repository(dataSource);
	}
	
}
