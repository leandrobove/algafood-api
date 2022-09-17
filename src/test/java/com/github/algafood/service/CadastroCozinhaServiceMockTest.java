package com.github.algafood.service;

import static com.github.algafood.builders.CozinhaBuilder.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.repository.CozinhaRepository;
import com.github.algafood.domain.service.CadastroCozinhaService;

@ExtendWith(MockitoExtension.class)
public class CadastroCozinhaServiceMockTest {

	@Mock
	private CozinhaRepository cozinhaRepository;

	@InjectMocks
	private CadastroCozinhaService cadastroCozinhaService;

	@Test
	public void cadastrarCozinha() {
		// given
		Cozinha cozinha = umaCozinhaComIdNulo().build();

		// when
		Cozinha cozinhaRetorno = umaCozinha().build();

		Mockito.when(cozinhaRepository.save(cozinha)).thenReturn(cozinhaRetorno);

		Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinha);

		// then
		assertAll(() -> {
			assertEquals(1L, cozinhaSalva.getId());
			assertEquals("Japonesa", cozinhaSalva.getNome());
		});
	}

	@Test
	public void buscarCozinha() {
		// given
		Cozinha cozinha = umaCozinha().build();

		Optional<Cozinha> cozinhaOptional = Optional.of(cozinha);

		Mockito.when(cozinhaRepository.findById(1L)).thenReturn(cozinhaOptional);

		// when
		Cozinha cozinhaRetorno = cadastroCozinhaService.buscarOuFalhar(1L);

		// then
		verify(cozinhaRepository).findById(1L); // verifica se o método foi executado uma vez
		assertAll(() -> {
			assertEquals(1L, cozinhaRetorno.getId());
			assertEquals("Japonesa", cozinhaRetorno.getNome());
		});
	}

	@Test
	public void excluirCozinha() {
		// given
		Long cozinhaId = 1L;

		// when
		cadastroCozinhaService.excluir(cozinhaId);

		// then
		verify(cozinhaRepository, times(1)).deleteById(eq(cozinhaId));
	}

}
