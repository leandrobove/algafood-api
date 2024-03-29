package com.github.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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

import com.github.algafood.api.assembler.FotoProdutoModelAssembler;
import com.github.algafood.api.dto.FotoProdutoModel;
import com.github.algafood.api.dto.input.FotoProdutoInput;
import com.github.algafood.api.openapi.controller.RestauranteFotoProdutoControllerOpenApi;
import com.github.algafood.core.security.CheckSecurity;
import com.github.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.github.algafood.domain.model.FotoProduto;
import com.github.algafood.domain.model.Produto;
import com.github.algafood.domain.service.CadastroProdutoService;
import com.github.algafood.domain.service.CatalogoFotoProdutoService;
import com.github.algafood.domain.service.FotoStorageService;
import com.github.algafood.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFotoProdutoController implements RestauranteFotoProdutoControllerOpenApi {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private FotoProdutoModelAssembler fotoProdutoAssembler;

	@Autowired
	private FotoStorageService fotoStorageService;

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
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

		return fotoProdutoAssembler.toModel(fotoProdutoSalva);
	}

	@Override
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

		return fotoProdutoAssembler.toModel(fotoProduto);
	}

	@Override
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> baixarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader)
			throws HttpMediaTypeNotAcceptableException {

		try {
			FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

			MediaType mediaTypeFotoProduto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaTypeFotoProduto, mediaTypesAceitas);

			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			//verifica se a foto está armazenada na amazon s3
			if(fotoRecuperada.temUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}

			return ResponseEntity.ok().contentType(mediaTypeFotoProduto).body(new InputStreamResource(fotoRecuperada.getInputStream()));
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
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
