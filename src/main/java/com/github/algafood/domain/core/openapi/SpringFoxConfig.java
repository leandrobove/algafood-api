package com.github.algafood.domain.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
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
					.useDefaultResponseMessages(false)
					.globalResponses(HttpMethod.GET, this.globalGetResponseMessages())
					.globalResponses(HttpMethod.POST, this.globalPostPutResponseMessages())
					.globalResponses(HttpMethod.PUT, this.globalPostPutResponseMessages())
					.globalResponses(HttpMethod.DELETE, this.globalDeleteResponseMessages())
				.apiInfo(this.adicionarInformacoesDaApi())
				.tags(new Tag("Cidades", "Gerencia as cidades"));
	}

	public ApiInfo adicionarInformacoesDaApi() {
		return new ApiInfoBuilder()
				.title("Algafood RestAPI")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Algaworks", "https://algaworks.com", "contact@algaworks.com"))
				.build();
	}
	
	/*
	 * Código de erro globais
	 */
	private List<Response> globalGetResponseMessages() {
		  return Arrays.asList(
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          .description("Erro interno do Servidor")
		      .build(),
		  new ResponseBuilder()
		      .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		      .description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          .build()
		  );
	}
	
	private List<Response> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
					.description("Requisição inválida (erro do cliente)")
				.build(),
				new ResponseBuilder()
	                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	                .description("Erro interno no servidor")
	                .build(),
	            new ResponseBuilder()
	                .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
	                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
	                .build(),
	            new ResponseBuilder()
	                .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
	                .description("Requisição recusada porque o corpo está em um formato não suportado")
	                .build()
				);
	}
	
	private List<Response> globalDeleteResponseMessages() {
		return Arrays.asList(
	            new ResponseBuilder()
	                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
	                .description("Requisição inválida (erro do cliente)")
	                .build(),
	            new ResponseBuilder()
	                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	                .description("Erro interno no servidor")
	                .build()
	    );
	}
	
}
