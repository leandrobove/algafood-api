package com.github.algafood.oauth2;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.core.security.authorizationserver.AlgafoodSecurityProperties;

@Component
public class AuthorizationCodePkceFlow {

	@Autowired
	private AlgafoodSecurityProperties algafoodSecurityProperties;
	
	//private ChromeWebDriver chromeWebDriver;
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	private String codeVerifier;
	
	public AuthorizationCodePkceFlow(ChromeWebDriver chromeWebDriver) {
		driver = chromeWebDriver.getChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		this.setup();
	}
	
	private void setup() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		//driver.manage().window().maximize();
	}

	private String gerarCodeVerifier() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] codeVerifier = new byte[32];
		secureRandom.nextBytes(codeVerifier);

		return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
	}

	private String gerarCodeChallenge(String codeVerifier)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] bytes = codeVerifier.getBytes("US-ASCII");
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(bytes, 0, bytes.length);
		byte[] digest = messageDigest.digest();

		return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
	}
	
	private String obterCodeDaURI(String codeUri) {
		if(!codeUri.contains("=") || !codeUri.contains("&")) {
			throw new IllegalArgumentException("Uri não está correta.");
		}
		return codeUri.split("=")[1].split("&")[0];
	}

	public String obterAuthorizationCode(String email, String senha, String clientId, String redirectUri, String scopes, String state) {
		try {
			// gerar code verifier
			codeVerifier = this.gerarCodeVerifier();

			// gerar code challenge
			String codeChallenge = this.gerarCodeChallenge(getCodeVerifier());
			
			// solicitar autorização no authorization server
			String authorizationCodeUri = String.format(algafoodSecurityProperties.getProviderUrl() 
					+ "/oauth2/authorize"
					+ "?response_type=code" 
					+ "&client_id=%s" 
					+ "&state=%s" 
					+ "&redirect_uri=%s" 
					+ "&scope=%s"
					+ "&code_challenge=%s" 
					+ "&code_challenge_method=S256",
					clientId, state, redirectUri, scopes, codeChallenge);

			//obter o authorization code
			driver.navigate().to(authorizationCodeUri);
			wait.until(ExpectedConditions.visibilityOfElementLocated(OauthLoginConstants.BTN_LOGIN));
			driver.findElement(OauthLoginConstants.CAMPO_EMAIL).sendKeys(email);
			driver.findElement(OauthLoginConstants.CAMPO_SENHA).sendKeys(senha);
			driver.findElement(OauthLoginConstants.BTN_LOGIN).click();
			
			//marcar escopos
			if(!driver.findElements(OauthLoginConstants.BTN_ENVIAR_ESCOPOS).isEmpty()) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(OauthLoginConstants.BTN_ENVIAR_ESCOPOS));
				for(String scope : scopes.split(" ")) {
					driver.findElement(OauthLoginConstants.getCampoScopo(scope)).click();
			    }
				driver.findElement(OauthLoginConstants.BTN_ENVIAR_ESCOPOS).click();
			}
			
			//obter ?code= do uri
			wait.until(ExpectedConditions.urlContains("?code"));
			String codeUri = driver.getCurrentUrl();
			
			return this.obterCodeDaURI(codeUri);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(driver != null) {
				driver.quit();
			}
		}
		
		return null;
	}

	public String getCodeVerifier() {
		return codeVerifier;
	}
}
