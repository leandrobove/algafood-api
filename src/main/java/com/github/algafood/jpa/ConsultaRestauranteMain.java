package com.github.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.github.algafood.AlgafoodApiApplication;
import com.github.algafood.domain.entity.Restaurante;
import com.github.algafood.domain.repository.RestauranteRepository;

public class ConsultaRestauranteMain {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		
		List<Restaurante> listaRestaurante = restauranteRepository.listar();
		
		for (Restaurante restaurante : listaRestaurante) {
			System.out.println(restaurante.getNome());
			System.out.println(restaurante.getTaxaFrete());
		}
		
		System.out.println("Finalizado.");
		System.exit(1);
	}

}
