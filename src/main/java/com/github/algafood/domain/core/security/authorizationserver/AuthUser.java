package com.github.algafood.domain.core.security.authorizationserver;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.github.algafood.domain.model.Usuario;

import lombok.Getter;

@Getter
public class AuthUser extends User {

	private static final long serialVersionUID = 1L;

	private Long userId;

	private String fullName;

	public AuthUser(Usuario usuarioModel, Collection<? extends GrantedAuthority> authorities) {
		super(usuarioModel.getEmail(), usuarioModel.getSenha(), authorities);

		this.fullName = usuarioModel.getNome();
		this.userId = usuarioModel.getId();
	}

}
