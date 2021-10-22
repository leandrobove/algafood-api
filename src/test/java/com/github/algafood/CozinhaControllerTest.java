package com.github.algafood;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
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

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

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
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornar4Cozinhas_QuandoConsultarCozinhas() {

		given().accept(ContentType.JSON).when().get().then().body("", Matchers.hasSize(2)).body("nome",
				Matchers.hasItems("Tailandesa", "Indiana"));
	}

	@Test
	public void deveRetornarStatus201_QuantoCadastrarCozinha() {

		Cozinha cozinha = new Cozinha(null, "Marroquina", null);

		given().body(cozinha).contentType(ContentType.JSON).accept(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarUmaCozinhaSemNome() {
		Cozinha cozinha = new Cozinha(null, null, null);

		given().contentType(ContentType.JSON).accept(ContentType.JSON).body(cozinha).when().post().then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarUmaCozinhaComNomeVazio() {
		Cozinha cozinha = new Cozinha(null, "   ", null);

		given().contentType(ContentType.JSON).accept(ContentType.JSON).body(cozinha).when().post().then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha(null, "Tailandesa", null);
		Cozinha cozinha2 = new Cozinha(null, "Indiana", null);

		cozinhaRepository.save(cozinha1);
		cozinhaRepository.save(cozinha2);
	}
}