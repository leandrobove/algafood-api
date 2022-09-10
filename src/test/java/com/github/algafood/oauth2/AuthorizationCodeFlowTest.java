package com.github.algafood.oauth2;

import static io.restassured.RestAssured.given;

import java.util.Base64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.github.algafood.core.security.authorizationserver.AlgafoodSecurityProperties;

import io.restassured.RestAssured;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
public class AuthorizationCodeFlowTest {

	@Autowired
	private AuthorizationCodePkceFlow authorizationCodePkceFlow;
	
	@Autowired
	private AlgafoodSecurityProperties algafoodSecurityProperties;

	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.baseURI = algafoodSecurityProperties.getProviderUrl();
	}
	
	@Test
	public void devePegarOauthTokenRefresh() {
		//dados do resource owner e client
		String email = "joao.ger@yopmail.com";
		String senha = "123";
		String clientId = "algafood-web";
		String cliendPassword = "web123";
		String scopes = "READ WRITE";
		String redirectUri = "http://127.0.0.1:8080/authorized";
		String state = "abc";
		
		String authorizationCode = authorizationCodePkceFlow.obterAuthorizationCode(email, senha, clientId, redirectUri, scopes, state);
		
		//gera access_token and refresh_token
		String accessToken = 
		given()
			.header("Authorization", "Basic " + 
					Base64.getEncoder().encodeToString((clientId + ":" + cliendPassword).getBytes()))
			.formParam("grant_type", "authorization_code")
			.formParam("code_verifier", authorizationCodePkceFlow.getCodeVerifier())
			.formParam("redirect_uri", redirectUri)
			.formParam("code", authorizationCode)
			.log().all()
		.when()
			.post("/oauth2/token")
		.then()
			.log().all()
			.statusCode(200)
			.extract().path("access_token")
		;
		
		Assertions.assertTrue(accessToken != null);
	}

}
