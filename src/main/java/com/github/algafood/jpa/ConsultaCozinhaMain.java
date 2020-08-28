package com.github.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.github.algafood.AlgafoodApiApplication;
import com.github.algafood.domain.entity.Cozinha;
import com.github.algafood.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		List<Cozinha> listaCozinha = cozinhaRepository.listar();
		
		for (Cozinha cozinha : listaCozinha) {
			System.out.println(cozinha.getNome());
		}
		
		System.out.println("Finalizado.");
		System.exit(1);
	}

}
