package com.diegomota.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.diegomota.cursomc.services.EmailService;
import com.diegomota.cursomc.services.SmtpEmailService;

@Configuration
public class EmailConfig {
	
	@Bean
	public EmailService smtpEmailService() {
		return new SmtpEmailService();
	}

}
