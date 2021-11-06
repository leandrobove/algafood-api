package com.github.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.ProdutoDTO;
import com.github.algafood.domain.model.Produto;

@Component
public class ProdutoAssembler extends GenericModelAssembler<Produto, ProdutoDTO> {
}
