package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.input.RestauranteDTOInput;
import com.github.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toRestaurante(RestauranteDTOInput restauranteDTOInput) {

		return modelMapper.map(restauranteDTOInput, Restaurante.class);
	}
}
