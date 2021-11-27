package com.github.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.github.algafood.domain.model.FotoProduto;
import com.github.algafood.domain.repository.ProdutoRepository;
import com.github.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FotoStorageService fotoStorageService;

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeArquivoExistente = null;

		// definir novo nome para o arquivo de foto
		String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		foto.setNomeArquivo(nomeNovoArquivo);

		// excluir foto caso já exista uma foto cadastrada no banco
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

		if (fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();

			// excluir foto no banco
			produtoRepository.delete(fotoExistente.get());
		}

		// salva a foto no banco antes de salvar no disco local para que caso haja
		// exception é realizado o rollback
		foto = produtoRepository.save(foto);
		produtoRepository.flush();

		NovaFoto novaFoto = NovaFoto.builder().nomeArquivo(foto.getNomeArquivo()).inputStream(dadosArquivo).build();

		// salva a foto no disco local
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

		return foto;
	}

	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {

		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
	}

	@Transactional
	public void excluir(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = this.buscarOuFalhar(restauranteId, produtoId);

		produtoRepository.delete(fotoProduto);
		produtoRepository.flush();

		fotoStorageService.remover(fotoProduto.getNomeArquivo());
	}

}
