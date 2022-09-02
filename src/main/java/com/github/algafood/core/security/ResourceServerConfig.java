package com.github.algafood.core.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig {
		
	@Bean
	public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			//.antMatchers("/oauth2/**").authenticated()
			.antMatchers(HttpMethod.POST, "/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
			.antMatchers(HttpMethod.PUT, "/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
			.antMatchers(HttpMethod.DELETE, "/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
			.antMatchers(HttpMethod.GET, "/cozinhas/**").authenticated()
			.anyRequest().denyAll()
		.and()
			.cors()
		.and()
			.csrf().disable()
			//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		//.and()
			.oauth2ResourceServer()
				.jwt()
				.jwtAuthenticationConverter(this.jwtAuthenticationConverter());
		
		http.formLogin(Customizer.withDefaults());
		
		return http.build();
	}
	
	/*
	 * Pega os authorities dentro do token JWT e converte em uma Collection de GrantedAuthority
	 */
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		
		converter.setJwtGrantedAuthoritiesConverter((jwt) -> {
			List<String> authorities = jwt.getClaimAsStringList("authorities");
			
			if(authorities == null) {
				return Collections.emptyList();
			}
			
			JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
			Collection<GrantedAuthority> grantedAuthorities = authoritiesConverter.convert(jwt);
			
			grantedAuthorities.addAll(authorities.stream().map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList()));
			
			return grantedAuthorities;
		});
		
		return converter;
	}

}
