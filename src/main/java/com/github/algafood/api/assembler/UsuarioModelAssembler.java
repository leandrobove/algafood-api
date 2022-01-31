package com.github.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.UsuarioModel;
import com.github.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler extends GenericModelAssembler<Usuario, UsuarioModel> {

}
