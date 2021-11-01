package com.github.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.CozinhaDTO;
import com.github.algafood.domain.model.Cozinha;

@Component
public class CozinhaAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public CozinhaDTO toDTO(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaDTO.class);
	}

	public List<CozinhaDTO> toListDTO(List<Cozinha> cozinhas) {
		return cozinhas.stream().map((cozinha) -> toDTO(cozinha)).collect(Collectors.toList());
	}

}
