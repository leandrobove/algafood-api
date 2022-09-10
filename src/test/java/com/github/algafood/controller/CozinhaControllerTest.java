package com.github.algafood.controller;

import static io.restassured.RestAssured.given;

import java.util.Base64;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.repository.CozinhaRepository;
import com.github.algafood.oauth2.AuthorizationCodePkceFlow;
import com.github.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CozinhaControllerTest {

	@Value("${local.server.port}")
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private AuthorizationCodePkceFlow authorizationCodePkceFlow;
	
	private static final int COZINHA_ID_INEXISTENTE = 100;
	
	private String accessToken;
	
	@BeforeAll
	public void beforeAll() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		//autenticação
		String email = "joao.ger@yopmail.com";
		String senha = "123";
		String clientId = "algafood-web";
		String cliendPassword = "web123";
		String scopes = "READ WRITE";
		String redirectUri = "http://127.0.0.1:8080/authorized";
		String state = "abc";
		
		String authorizationCode = authorizationCodePkceFlow.obterAuthorizationCode(email, senha, clientId, redirectUri, scopes, state);
		
		//gera access_token and refresh_token
		Response response = given()
			.header("Authorization", "Basic " + 
					Base64.getEncoder().encodeToString((clientId + ":" + cliendPassword).getBytes()))
			.contentType(ContentType.URLENC)
			.formParam("grant_type", "authorization_code")
			.formParam("code_verifier", authorizationCodePkceFlow.getCodeVerifier())
			.formParam("redirect_uri", redirectUri)
			.formParam("code", authorizationCode)
		.when()
			.post("/oauth2/token")
		.then()
			.statusCode(200)
			.extract().response()
		;
		
		accessToken = response.jsonPath().get("access_token");
	}

	@BeforeEach
	public void setup() {
		RestAssured.basePath = "/cozinhas";
		RestAssured.port = port;
		
		databaseCleaner.clearTables(); // limpar db sempre antes de executar cada teste
		this.prepararDados();
	}
	
	@Test
	public void deveRetornarStatus403_QuandoConsultarCozinhasSemAutenticacao() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.FORBIDDEN.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
			.auth().oauth2(accessToken)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornar2Cozinhas_QuandoConsultarCozinhas() {

		given()
			.accept(ContentType.JSON)
			.auth().oauth2(accessToken)
		.when()
			.get()
		.then()
			.body("_embedded.cozinhas", Matchers.hasSize(2));
	}

	@Test
	public void deveRetornarStatus201_QuantoCadastrarCozinha() {

		String cozinhaJson = "{\r\n"
				+ "    \"nome\":\"Japonesa\"\r\n"
				+ "}";

		given()
			.body(cozinhaJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.auth().oauth2(accessToken)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarUmaCozinhaSemNome() {
		String cozinhaJson = "{}";

		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(cozinhaJson)
			.auth().oauth2(accessToken)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarUmaCozinhaComNomeVazio() {

		String cozinhaJson = "{\r\n"
				+ "    \"nome\":\"  \"\r\n"
				+ "}";

		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(cozinhaJson)
			.auth().oauth2(accessToken)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void deveRetornarStatus200ERespostaCorreta_QuantoConsultarCozinhaExistente() {

		given()
			.accept(ContentType.JSON)
			.auth().oauth2(accessToken)
			.pathParam("cozinhaId", 2)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", CoreMatchers.equalTo("Indiana"));
	}

	@Test
	public void deveRetornarStatus404_QuantoConsultarCozinhaInexistente() {

		given()
			.auth().oauth2(accessToken)
			.accept(ContentType.JSON)
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
		.when()
			.get("/{cozinhaId}")
		.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus200ERespostaCorreta_QuandoAtualizarCozinha() {
		
		String cozinhaJson = "{\r\n"
				+ "    \"nome\":\"Nova Cozinha\"\r\n"
				+ "}";
		
		given()
			.auth().oauth2(accessToken)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
//			.pathParam("cozinhaId", 1)
			.body(cozinhaJson)
		.when()
			.put("/{cozinhaId}", 1)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", CoreMatchers.equalTo("Nova Cozinha"))
			.body("id", CoreMatchers.equalTo(1));
		
	}
	
	@Test
	public void deveRetornarStatus400_QuandoAtualizarCozinhaInexistente() {
		
		String cozinhaJson = "{\r\n"
				+ "    \"nome\":\"Nova Cozinha\"\r\n"
				+ "}";
		
		given()
			.auth().oauth2(accessToken)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.body(cozinhaJson)
		.when()
			.put("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
		
	}
	
	@Test
	public void deveRetornarStatus400_QuandoAtualizarCozinhaSemNome() {
		
		String cozinhaJson = "{\r\n"
				+ "    \"nome\":\"   \"\r\n"
				+ "}";
		
		given()
			.auth().oauth2(accessToken)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.pathParam("cozinhaId", 1)
			.body(cozinhaJson)
		.when()
			.put("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
		
	}
	
	@Test
	public void deveRetornarStatus200_QuandoDeletarCozinha() {
		
		given()
			.auth().oauth2(accessToken)
			.accept(ContentType.JSON)
			.pathParam("cozinhaId", 1)
		.when()
			.delete("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoDeletarCozinhaInexistente() {
		
		given()
			.auth().oauth2(accessToken)
			.accept(ContentType.JSON)
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
		.when()
			.delete("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha(null, "Tailandesa", null);
		Cozinha cozinha2 = new Cozinha(null, "Indiana", null);

		cozinhaRepository.save(cozinha1);
		cozinhaRepository.save(cozinha2);
	}
}