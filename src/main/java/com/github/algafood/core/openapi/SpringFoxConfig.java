package com.github.algafood.core.openapi;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.algafood.api.dto.CidadeModel;
import com.github.algafood.api.dto.CozinhaModel;
import com.github.algafood.api.dto.EstadoModel;
import com.github.algafood.api.dto.FormaPagamentoModel;
import com.github.algafood.api.dto.GrupoModel;
import com.github.algafood.api.dto.PedidoResumoModel;
import com.github.algafood.api.dto.PermissaoModel;
import com.github.algafood.api.dto.ProdutoModel;
import com.github.algafood.api.dto.RestauranteBasicoModel;
import com.github.algafood.api.dto.UsuarioModel;
import com.github.algafood.api.exceptionhandler.Problem;
import com.github.algafood.api.openapi.model.CidadesModelOpenApi;
import com.github.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.github.algafood.api.openapi.model.EstadosModelOpenApi;
import com.github.algafood.api.openapi.model.FormasPagamentoModelOpenApi;
import com.github.algafood.api.openapi.model.GruposModelOpenApi;
import com.github.algafood.api.openapi.model.LinksModelOpenApi;
import com.github.algafood.api.openapi.model.PageableModelOpenApi;
import com.github.algafood.api.openapi.model.PedidosResumoModelOpenApi;
import com.github.algafood.api.openapi.model.PermissoesModelOpenApi;
import com.github.algafood.api.openapi.model.ProdutosModelOpenApi;
import com.github.algafood.api.openapi.model.RestaurantesBasicoModelOpenApi;
import com.github.algafood.api.openapi.model.UsuariosModelOpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
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
				.ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, 
						Resource.class, File.class, InputStream.class)
				.additionalModels(typeResolver.resolve(Problem.class))
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(
						AlternateTypeRules.newRule(
								typeResolver.resolve(CollectionModel.class, CidadeModel.class),
								CidadesModelOpenApi.class
						),
						AlternateTypeRules.newRule(
								typeResolver.resolve(CollectionModel.class, EstadoModel.class),
								EstadosModelOpenApi.class
						),
						AlternateTypeRules.newRule(
								typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
								FormasPagamentoModelOpenApi.class
						),
						AlternateTypeRules.newRule(
								typeResolver.resolve(CollectionModel.class, GrupoModel.class),
								GruposModelOpenApi.class
						),
						AlternateTypeRules.newRule(
								typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
								PermissoesModelOpenApi.class
						),
						AlternateTypeRules.newRule(
								typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
								ProdutosModelOpenApi.class
						),
						AlternateTypeRules.newRule(
								typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
								RestaurantesBasicoModelOpenApi.class
						),
						AlternateTypeRules.newRule(
								typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
								UsuariosModelOpenApi.class
						)
				)
				.alternateTypeRules(
						AlternateTypeRules.newRule(
								typeResolver.resolve(PagedModel.class, CozinhaModel.class),
								CozinhasModelOpenApi.class
						),
						AlternateTypeRules.newRule(
								typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
								PedidosResumoModelOpenApi.class
						)
				)
				.apiInfo(this.adicionarInformacoesDaApi())
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(List.of(authenticationScheme()))
				.securityContexts(List.of(securityContext()))
				.tags(
						new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos", "Gerencia os grupos"),
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os pedidos"),
						new Tag("Restaurantes", "Gerencia os restaurantes"),
						new Tag("Estados", "Gerencia os estados"),
						new Tag("Produtos", "Gerencia os produtos de restaurantes"),
						new Tag("Usuarios", "Gerencia os usuários"),
						new Tag("Permissões", "Gerencia as permissões")
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
	 * Adiciona suporte a OAuth2 na documentação
	 */
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(this.securityReference()).build();
	}

	private List<SecurityReference> securityReference() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return List.of(new SecurityReference("Authorization", authorizationScopes));
	}

	private HttpAuthenticationScheme authenticationScheme() {
		return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();
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
