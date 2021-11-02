package com.github.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.input.CozinhaInput;
import com.github.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toCozinha(CozinhaInput cozinhaInput) {
		return modelMapper.map(cozinhaInput, Cozinha.class);
	}

	public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinhaAtual) {
		modelMapper.map(cozinhaInput, cozinhaAtual);
	}

}
