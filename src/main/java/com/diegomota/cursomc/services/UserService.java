package com.diegomota.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.diegomota.cursomc.security.UserSS;

public class UserService {
	
	/***
	 * Retorna o usuario logado no sistema
	 * @return
	 */
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		
	}
}
