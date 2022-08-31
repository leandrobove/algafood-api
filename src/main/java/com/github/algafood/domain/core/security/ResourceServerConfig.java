package com.github.algafood.domain.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig {
		
	@Bean
	public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			//.antMatchers("/oauth2/**").authenticated()
			.anyRequest().authenticated()
		.and()
			.cors()
		.and()
			.csrf().disable()
			//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		//.and()
			.oauth2ResourceServer()
				.jwt();
		
		http.formLogin(Customizer.withDefaults());
		
		return http.build();
	}

}
