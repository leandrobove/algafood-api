package com.github.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.CidadeDTO;
import com.github.algafood.domain.model.Cidade;

@Component
public class CidadeAssembler extends GenericModelAssembler<Cidade, CidadeDTO> {

}
