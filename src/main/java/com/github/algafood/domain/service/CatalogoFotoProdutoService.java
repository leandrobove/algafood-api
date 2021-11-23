package com.github.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.algafood.domain.model.FotoProduto;
import com.github.algafood.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public FotoProduto salvar(FotoProduto foto) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();

		// excluir foto caso já exista uma foto cadastrada
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if (fotoExistente.isPresent()) {		
			// excluir foto
			produtoRepository.delete(fotoExistente.get());
		}

		return produtoRepository.save(foto);
	}

}
