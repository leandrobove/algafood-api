package com.github.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.github.algafood.AlgafoodApiApplication;
import com.github.algafood.domain.entity.Cozinha;
import com.github.algafood.domain.repository.CozinhaRepository;

public class AtualizacaoCozinhaMain {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha1 = new Cozinha(1L, "Brasileira");

		cozinha1 = cozinhaRepository.salvar(cozinha1);
		
		System.out.println("Atualizada: " + cozinha1.getNome());
		
		System.out.println("Finalizado.");
		System.exit(1);
	}

}
