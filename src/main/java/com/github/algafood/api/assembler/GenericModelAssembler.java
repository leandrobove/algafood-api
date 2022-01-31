package com.github.algafood.api.assembler;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;

/*
 * Classe Genérica para Transformar uma Model em ModelDTO
 * E - Classe Model
 * M - Classe DTO
 */

@Getter
public abstract class GenericModelAssembler<E, M> {

	@Autowired
	private ModelMapper modelMapper;

	private final Class<E> model;
	private final Class<M> modelDTO;

	@SuppressWarnings("unchecked")
	public GenericModelAssembler() {
		this.model = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.modelDTO = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	public M toModel(E model) {
		return modelMapper.map(model, this.getModelDTO());
	}

	public List<M> toCollectionModel(Collection<E> modelList) {
		return modelList.stream().map((m) -> this.toModel(m)).collect(Collectors.toList());
	}
}
