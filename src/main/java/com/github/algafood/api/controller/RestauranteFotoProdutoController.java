package com.github.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.algafood.api.assembler.FotoProdutoAssembler;
import com.github.algafood.api.dto.FotoProdutoDTO;
import com.github.algafood.api.dto.input.FotoProdutoInput;
import com.github.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.github.algafood.domain.model.FotoProduto;
import com.github.algafood.domain.model.Produto;
import com.github.algafood.domain.service.CadastroProdutoService;
import com.github.algafood.domain.service.CatalogoFotoProdutoService;
import com.github.algafood.infrastructure.service.storage.DiscoLocalFotoStorageService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private FotoProdutoAssembler fotoProdutoAssembler;

	@Autowired
	private DiscoLocalFotoStorageService discoLocalFotoStorageService;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput) throws IOException {

		Produto produto = cadastroProdutoService.buscarOuFalhar(produtoId, restauranteId);

		FotoProduto foto = new FotoProduto();

		MultipartFile arquivo = fotoProdutoInput.getArquivo();

		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		foto.setTamanho(arquivo.getSize());

		FotoProduto fotoProdutoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());

		return fotoProdutoAssembler.toDTO(fotoProdutoSalva);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

		return fotoProdutoAssembler.toDTO(fotoProduto);
	}

	@GetMapping
	public ResponseEntity<InputStreamResource> baixarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader)
			throws HttpMediaTypeNotAcceptableException {

		try {
			FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

			MediaType mediaTypeFotoProduto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaTypeFotoProduto, mediaTypesAceitas);

			InputStream inputStream = discoLocalFotoStorageService.recuperar(fotoProduto.getNomeArquivo());

			return ResponseEntity.ok().contentType(mediaTypeFotoProduto).body(new InputStreamResource(inputStream));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void excluirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {	
		catalogoFotoProdutoService.excluir(restauranteId, produtoId);
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFotoProduto, List<MediaType> mediaTypesAceitas)
			throws HttpMediaTypeNotAcceptableException {
		boolean isCompativel = mediaTypesAceitas.stream()
				.anyMatch((mediaTypeAceita) -> mediaTypeAceita.isCompatibleWith(mediaTypeFotoProduto));

		if (!isCompativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}

}
