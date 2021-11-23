package com.github.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.assembler.FotoProdutoAssembler;
import com.github.algafood.api.dto.FotoProdutoDTO;
import com.github.algafood.api.dto.input.FotoProdutoInput;
import com.github.algafood.domain.model.FotoProduto;
import com.github.algafood.domain.model.Produto;
import com.github.algafood.domain.service.CadastroProdutoService;
import com.github.algafood.domain.service.CatalogoFotoProdutoService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private FotoProdutoAssembler fotoProdutoAssembler;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput) {

		Produto produto = cadastroProdutoService.buscarOuFalhar(produtoId, restauranteId);

		FotoProduto foto = new FotoProduto();

		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(fotoProdutoInput.getArquivo().getContentType());
		foto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());
		foto.setTamanho(fotoProdutoInput.getArquivo().getSize());

		FotoProduto fotoProdutoSalva = catalogoFotoProdutoService.salvar(foto);

		return fotoProdutoAssembler.toDTO(fotoProdutoSalva);

//		String nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();
//
//		Path arquivoFoto = Path.of("/Users/leand/Desktop/catalogo", nomeArquivo);
//
//		System.out.println(fotoProdutoInput.getDescricao());
//		System.out.println(arquivoFoto);
//		System.out.println(fotoProdutoInput.getArquivo().getContentType());
//		
//		try {
//			fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
	}

}
