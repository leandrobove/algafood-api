package com.github.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.GrupoDTO;
import com.github.algafood.domain.model.Grupo;

@Component
public class GrupoAssembler extends GenericModelAssembler<Grupo, GrupoDTO> {
}
