package com.github.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.github.algafood.domain.exception.EntidadeEmUsoException;
import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest
class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Test
	public void deveCadastrarCozinhaComSucesso() {
		// cenário
		Cozinha novaCozinha = new Cozinha(null, "Chinesa", null);

		// ação
		Cozinha cozinhaSalva = cadastroCozinhaService.salvar(novaCozinha);

		// validação
		assertThat(cozinhaSalva).isNotNull();
		assertThat(cozinhaSalva.getId()).isNotNull();
	}

	@Test
	public void deveFalharAoCadastrarCozinha_QuandoSemNome() {

		Cozinha novaCozinha = new Cozinha(null, null, null);

		assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinhaService.salvar(novaCozinha);
		});
	}

	@Test
	public void deveFalharAoExcluirCozinha_QuandoEmUso() {

		assertThrows(EntidadeEmUsoException.class, () -> {
			cadastroCozinhaService.excluir(1L);
		});

	}

	@Test
	public void deveFalharAoExcluirCozinha_QuandoIdNaoEncontrado() {

		assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cadastroCozinhaService.excluir(100L);
		});

	}
}
