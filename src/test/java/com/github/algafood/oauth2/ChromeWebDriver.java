package com.github.algafood.oauth2;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ChromeWebDriver {
	
	@Autowired
	private ChromeWebDriverProperties chromeWebDriverProperties;
	
	private ChromeOptions getChromeOptions() {
		ChromeOptions chromeOptions = new ChromeOptions();
		
		//add configurações
		chromeOptions.addArguments("headless");
		
		return chromeOptions;
	}
	
	public WebDriver getChromeDriver() {
		Resource webDriverLocation = chromeWebDriverProperties.getWebDriverLocation();
		
		try {
			System.setProperty("webdriver.chrome.driver", webDriverLocation.getFile().getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		WebDriver webDriver = new ChromeDriver(getChromeOptions());
		
		return webDriver;
	}

}
