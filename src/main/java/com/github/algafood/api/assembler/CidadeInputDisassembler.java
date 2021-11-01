package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.input.CidadeInput;
import com.github.algafood.domain.model.Cidade;
import com.github.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Cidade toCidade(CidadeInput cidadeDTOInput) {
		return modelMapper.map(cidadeDTOInput, Cidade.class);
	}

	public void copyToDomainObject(CidadeInput cidadeDTOInput, Cidade cidade) {
		// Para evitar esta exception
//		org.springframework.orm.jpa.JpaSystemException: identifier of an instance of com.github.algafood.domain.model.Cozinha was altered from 1 to 2; nested exception is org.hibernate.HibernateException: identifier of an instance of com.github.algafood.domain.model.Cozinha was altered from 1 to 2
		cidade.setEstado(new Estado());

		modelMapper.map(cidadeDTOInput, cidade);
	}

}
