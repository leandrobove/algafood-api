package com.github.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.input.RestauranteInput;
import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toRestaurante(RestauranteInput restauranteDTOInput) {

		return modelMapper.map(restauranteDTOInput, Restaurante.class);
	}

	public void copyToDomainObject(RestauranteInput restauranteDTOInput, Restaurante restaurante) {
		// Para evitar esta exception
//		org.springframework.orm.jpa.JpaSystemException: identifier of an instance of com.github.algafood.domain.model.Cozinha was altered from 1 to 2; nested exception is org.hibernate.HibernateException: identifier of an instance of com.github.algafood.domain.model.Cozinha was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());

		modelMapper.map(restauranteDTOInput, restaurante);
	}
}
