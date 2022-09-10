package com.github.algafood.oauth2;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("webdriver.chrome.driver")
public class ChromeWebDriverProperties {

	@NotNull
	private Resource webDriverLocation;
}
