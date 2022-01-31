package com.github.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.ProdutoModel;
import com.github.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends GenericModelAssembler<Produto, ProdutoModel> {
}
