package com.github.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.ProdutoDTO;
import com.github.algafood.domain.model.Produto;

@Component
public class ProdutoAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoDTO toDTO(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}

	public List<ProdutoDTO> toListDTO(List<Produto> produtos) {
		return produtos.stream().map((produto) -> toDTO(produto)).collect(Collectors.toList());
	}
}
