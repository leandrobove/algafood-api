package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

	private Long id;

	private String nome;

	private String email;

}
