package com.github.algafood.api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.github.algafood.domain.model.Cozinha;

import lombok.Data;

@Data
@JsonRootName(value = "cozinhas")
public class CozinhasXmlRepresentationModel {

	@JacksonXmlElementWrapper(useWrapping = false)
	@JsonProperty(value = "cozinha")
	private List<Cozinha> cozinhas;

	public CozinhasXmlRepresentationModel(List<Cozinha> cozinhas) {
		this.cozinhas = cozinhas;
	}

}
