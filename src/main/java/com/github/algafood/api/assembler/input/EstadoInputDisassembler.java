package com.github.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.input.EstadoInput;
import com.github.algafood.domain.model.Estado;

@Component
public class EstadoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Estado toEstado(EstadoInput estadoDTOInput) {
		return modelMapper.map(estadoDTOInput, Estado.class);
	}

	public void copyToDomainObject(EstadoInput estadoInput, Estado estadoAtual) {
		modelMapper.map(estadoInput, estadoAtual);
	}

}
