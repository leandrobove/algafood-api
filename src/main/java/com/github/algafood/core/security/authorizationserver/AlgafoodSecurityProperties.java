package com.github.algafood.core.security.authorizationserver;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("algafood.auth")
public class AlgafoodSecurityProperties {
	
	@NotBlank
	private String providerUrl;

}
