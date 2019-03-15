package com.diegomota.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.diegomota.cursomc.services.DBService;
import com.diegomota.cursomc.services.EmailService;
import com.diegomota.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	
	@Bean
	public boolean instatiateDataBase() {
		
		if(!"create".equals(strategy)) {
			return false;
		}
	
		dbService.instantiateTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService smtpEmailService() {
		return new SmtpEmailService();
	}
	
}
