package com.github.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.UsuarioDTO;
import com.github.algafood.domain.model.Usuario;

@Component
public class UsuarioAssembler extends GenericModelAssembler<Usuario, UsuarioDTO> {

}
