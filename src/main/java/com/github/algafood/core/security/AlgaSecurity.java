package com.github.algafood.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecurity {
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public Long getUsuarioId() {
		
		Jwt jwt = (Jwt) this.getAuthentication().getPrincipal();
		
		return Long.valueOf(jwt.getClaim("usuario_id"));
	}

}
