package com.github.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.input.UsuarioComSenhaInput;
import com.github.algafood.api.dto.input.UsuarioSemSenhaInput;
import com.github.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Usuario toUsuario(UsuarioComSenhaInput usuarioComSenhaInput) {
		return modelMapper.map(usuarioComSenhaInput, Usuario.class);
	}

	public void copyToDomainObject(UsuarioSemSenhaInput usuarioSemSenhaInput, Usuario usuario) {
		modelMapper.map(usuarioSemSenhaInput, usuario);
	}

}
