package com.github.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.FormaPagamentoDTO;
import com.github.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}

	public List<FormaPagamentoDTO> toListDTO(Collection<FormaPagamento> formasPagamento) {
		return formasPagamento.stream().map((formaPagamento) -> toDTO(formaPagamento)).collect(Collectors.toList());
	}

}
