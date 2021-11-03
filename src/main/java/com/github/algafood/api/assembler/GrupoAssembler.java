package com.github.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.GrupoDTO;
import com.github.algafood.domain.model.Grupo;

@Component
public class GrupoAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public GrupoDTO toDTO(Grupo grupo) {
		return modelMapper.map(grupo, GrupoDTO.class);
	}

	public List<GrupoDTO> toListDTO(List<Grupo> grupos) {
		return grupos.stream().map((grupo) -> this.toDTO(grupo)).collect(Collectors.toList());
	}
}
