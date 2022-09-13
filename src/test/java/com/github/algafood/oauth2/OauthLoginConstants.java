package com.github.algafood.oauth2;

import org.openqa.selenium.By;

public class OauthLoginConstants {
	
	public static final By CAMPO_EMAIL = By.xpath("//input[@name=\"username\"]");
	public static final By CAMPO_SENHA = By.xpath("//input[@name=\"password\"]");
	public static final By BTN_LOGIN = By.xpath("/html/body/form/button");
	public static By getCampoScopo(String scope) {
		return By.xpath(String.format("//*[@id=\"%s\"]", scope));
	}
	public static final By BTN_ENVIAR_ESCOPOS = By.xpath("//*[@id=\"submit-consent\"]");
}