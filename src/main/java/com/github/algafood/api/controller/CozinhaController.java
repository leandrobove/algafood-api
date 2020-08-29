package com.github.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.entity.CozinhasXmlRepresentationModel;
import com.github.algafood.domain.entity.Cozinha;
import com.github.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.listar();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlRepresentationModel listarXml() {
		return new CozinhasXmlRepresentationModel(cozinhaRepository.listar());
	}

	@GetMapping(value = "/{id}")
	public Cozinha buscar(@PathVariable(value = "id") Long id) {
		return cozinhaRepository.buscar(id);
	}

}
