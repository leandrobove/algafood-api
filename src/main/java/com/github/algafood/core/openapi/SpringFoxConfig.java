package com.github.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.algafood.api.exceptionhandler.Problem;
import com.github.algafood.api.openapi.model.LinksModelOpenApi;
import com.github.algafood.api.openapi.model.PageableModelOpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

	@Bean
	public Docket configureApiDocket() {
		var typeResolver = new TypeResolver();
		
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
				.ignoredParameterTypes(ServletWebRequest.class)
				.additionalModels(typeResolver.resolve(Problem.class))
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.apiInfo(this.adicionarInformacoesDaApi())
				.tags(
						new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos", "Gerencia os grupos"),
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os pedidos"),
						new Tag("Restaurantes", "Gerencia os restaurantes"),
						new Tag("Estados", "Gerencia os estados"),
						new Tag("Produtos", "Gerencia os produtos de restaurantes")
				);
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
		          .representation(MediaType.APPLICATION_JSON)
		          .apply(this.getProblemaModelReference())
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
					.representation(MediaType.APPLICATION_JSON)
			        .apply(this.getProblemaModelReference())
			        .build(),
				new ResponseBuilder()
	                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	                .description("Erro interno no servidor")
	                .representation(MediaType.APPLICATION_JSON)
			         .apply(this.getProblemaModelReference())
	                .build(),
	            new ResponseBuilder()
	                .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
	                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
	                .build(),
	            new ResponseBuilder()
	                .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
	                .description("Requisição recusada porque o corpo está em um formato não suportado")
	                .representation(MediaType.APPLICATION_JSON)
			        .apply(this.getProblemaModelReference())
	                .build()
				);
	}
	
	private List<Response> globalDeleteResponseMessages() {
		return Arrays.asList(
	            new ResponseBuilder()
	                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
	                .description("Requisição inválida (erro do cliente)")
	                .representation(MediaType.APPLICATION_JSON)
			        .apply(this.getProblemaModelReference())
	                .build(),
	            new ResponseBuilder()
	                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	                .description("Erro interno no servidor")
	                .representation(MediaType.APPLICATION_JSON)
			        .apply(this.getProblemaModelReference())
	                .build()
	    );
	}
	
	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
	
	private Consumer<RepresentationBuilder> getProblemaModelReference() {
		return r -> r.model(m -> m.name("Problema")
				.referenceModel(ref -> ref.key(k -> k.qualifiedModelName(q -> q.name("Problema")
						.namespace("com.github.algafood.api.exceptionhandler")))));
	}
}
