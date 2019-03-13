package com.diegomota.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.diegomota.cursomc.services.DBService;
import com.diegomota.cursomc.services.EmailService;
import com.diegomota.cursomc.services.MockEmailService;
import com.diegomota.cursomc.services.SmtpEmailSerice;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	
	@Bean
	public boolean instatiateDataBase() {
		dbService.instantiateTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();	
	}
	
	@Bean
	public EmailService smtpEmailService() {
		return new SmtpEmailSerice();
	}
}
