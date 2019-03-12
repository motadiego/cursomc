package com.diegomota.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.diegomota.cursomc.domain.Pedido;

public interface EmailService {
	
	 void sendOrderConfirmationEmail(Pedido obj);
	 
	 void sendEmail(SimpleMailMessage msg);
	
}
