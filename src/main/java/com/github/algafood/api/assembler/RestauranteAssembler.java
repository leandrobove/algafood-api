package com.github.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.RestauranteDTO;
import com.github.algafood.domain.model.Restaurante;

@Component
public class RestauranteAssembler extends GenericModelAssembler<Restaurante, RestauranteDTO> {

}
