package com.github.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.EstadoDTO;
import com.github.algafood.domain.model.Estado;

@Component
public class EstadoAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public EstadoDTO toDTO(Estado estado) {
		return modelMapper.map(estado, EstadoDTO.class);
	}

	public List<EstadoDTO> toListDTO(List<Estado> estados) {
		return estados.stream().map((estado) -> toDTO(estado)).collect(Collectors.toList());
	}
}
