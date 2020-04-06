package com.example.cursomodelagemconceitual.services;

import org.springframework.mail.SimpleMailMessage;

import com.example.cursomodelagemconceitual.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
		
	void sendEmail(SimpleMailMessage msg);
	
}
