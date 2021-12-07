package com.github.algafood.domain.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket configureApiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.github.algafood.api"))
				.paths(PathSelectors.any())
				.build()
					.apiInfo(this.adicionarInformacoesDaApi());
	}
	
	public ApiInfo adicionarInformacoesDaApi() {
		return new ApiInfoBuilder()
				.title("Algafood RestAPI")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Algaworks", "https://algaworks.com", "contact@algaworks.com"))
				.build();
	}
	
}
