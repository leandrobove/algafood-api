package com.github.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.repository.CozinhaRepository;
import com.github.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping(value = "/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepo;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@GetMapping(value = "/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam(value = "nome") String nome) {
		return cozinhaRepo.findTodasByNomeContaining(nome);
	}

	@GetMapping(value = "/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(@RequestParam(value = "nome") String nome) {
		return cozinhaRepo.findByNome(nome);
	}

	@GetMapping(value = "/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(@RequestParam BigDecimal taxaInicial,
			@RequestParam BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	@GetMapping(value = "/restaurantes/por-nome")
	public List<Restaurante> listarRestaurantesPorNomeLike(@RequestParam String nome, @RequestParam Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}

	@GetMapping(value = "/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> buscarPrimeiroPorNome(@RequestParam String nome) {
		return restauranteRepository.findFirstByNomeContaining(nome);
	}

	@GetMapping(value = "/restaurantes/dois-por-nome")
	public List<Restaurante> consultarDoisPrimeirosPorNome(@RequestParam String nome) {
		return restauranteRepository.consultarDoisPrimeirosPorNome(nome);
	}

	@GetMapping(value = "/restaurantes/quantidade")
	public Integer contarRestaurantes() {
		return restauranteRepository.countByRestauranteId();
	}

	@GetMapping(value = "/restaurantes/count-por-cozinha")
	public Integer contarCozinhas(@RequestParam(value = "cozinhaid") Long cozinhaId) {
		return restauranteRepository.countRestaurantesByCozinhaId(cozinhaId);
	}

	@GetMapping(value = "/restaurantes/por-nome-e-frete")
	public List<Restaurante> listarRestaurantesPorNomeEFrete(String nome,
			BigDecimal taxaInicial, BigDecimal taxaFinal) {

		return restauranteRepository.consultarPorNomeETaxaFrete(nome, taxaInicial, taxaFinal);
	}

	@GetMapping(value = "/restaurantes/buscar-por-cozinha")
	public List<Restaurante> buscarPorCozinhaId(@RequestParam Long cozinhaId) {
		return restauranteRepository.buscarPorCozinhaId(cozinhaId);
	}
	
	@GetMapping(value = "/restaurantes/com-frete-gratis")
	public List<Restaurante> buscarRestaurantesComFreteGratis(String nome) {
		return restauranteRepository.buscarComFreteGratisENomeSemelhante(nome);
	}
	
	@GetMapping(value = "/restaurantes/primeiro")
	public Optional<Restaurante> buscarPrimeiroRestaurante() {
		return restauranteRepository.buscarPrimeiro();
	}

}
