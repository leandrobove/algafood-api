package com.github.algafood.domain.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.algafood.api.dto.EnderecoDTO;
import com.github.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		enderecoToEnderecoDTOTypeMap.<String>addMapping((cidadeSource) -> cidadeSource.getCidade().getEstado().getNome(),
				(enderecoDTODest, value) -> enderecoDTODest.getCidade().setEstado(value));

		return modelMapper;
	}

}
