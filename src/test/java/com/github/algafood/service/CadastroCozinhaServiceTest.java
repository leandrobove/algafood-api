package com.github.algafood.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.github.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.github.algafood.domain.exception.EntidadeEmUsoException;
import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.repository.CozinhaRepository;
import com.github.algafood.domain.repository.RestauranteRepository;
import com.github.algafood.domain.service.CadastroCozinhaService;
import com.github.algafood.util.DatabaseCleaner;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
class CadastroCozinhaServiceTest {

	private static final Long ID_COZINHA_INEXISTENTE = 100L;
	
	private static Long ID_COZINHA_EXISTENTE;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@BeforeEach
	public void setup() {
		databaseCleaner.clearTables(); // limpar db sempre antes de executar cada teste
		this.prepararDados();
	}
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha(null, "Tailandesa", null);
		Cozinha cozinha2 = new Cozinha(null, "Indiana", null);
		
		cozinhaRepository.save(cozinha1);
		Cozinha cozinha2Salva = cozinhaRepository.save(cozinha2);
		
		ID_COZINHA_EXISTENTE = cozinha2Salva.getId();
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome("Restaurante Ipiranga");
		restaurante.setTaxaFrete(new BigDecimal(12.00));
		restaurante.setCozinha(cozinha1);
		
		restauranteRepository.save(restaurante);
	}

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
			cadastroCozinhaService.excluir(ID_COZINHA_INEXISTENTE);
		});

	}

	@Test
	public void deveFalharAoBuscarOuFalharPorId_QuandoIdNaoEncontrado() {

		assertThrows(CozinhaNaoEncontradaException.class,
				() -> cadastroCozinhaService.buscarOuFalhar(ID_COZINHA_INEXISTENTE));

	}
	
	@Test
	public void deveExcluirCozinhaComSucesso() {
		
		cadastroCozinhaService.excluir(ID_COZINHA_EXISTENTE);
		
		assertThrows(CozinhaNaoEncontradaException.class,
				() -> cadastroCozinhaService.buscarOuFalhar(ID_COZINHA_EXISTENTE));
	}
}
