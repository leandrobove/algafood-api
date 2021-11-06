package com.github.algafood.domain.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.algafood.api.dto.EnderecoDTO;
import com.github.algafood.api.dto.input.ItemPedidoInput;
import com.github.algafood.domain.model.Endereco;
import com.github.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		enderecoToEnderecoDTOTypeMap.<String>addMapping(
				(cidadeSource) -> cidadeSource.getCidade().getEstado().getNome(),
				(enderecoDTODest, value) -> enderecoDTODest.getCidade().setEstado(value));

		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
				.addMappings(mapper -> mapper.skip(ItemPedido::setId));

		return modelMapper;
	}

}
