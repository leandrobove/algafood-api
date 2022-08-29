package com.github.algafood.controller;

import static io.restassured.RestAssured.given;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.repository.CozinhaRepository;
import com.github.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "/application-test.properties")
public class CozinhaControllerTest {

	@Value("${local.server.port}")
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private static final int COZINHA_ID_INEXISTENTE = 100;

	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables(); // limpar db sempre antes de executar cada teste
		this.prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornar2Cozinhas_QuandoConsultarCozinhas() {

		given()
			.accept(ContentType.JSON)
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
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void deveRetornarStatus200ERespostaCorreta_QuantoConsultarCozinhaExistente() {

		given()
			.accept(ContentType.JSON)
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