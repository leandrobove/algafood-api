package com.github.algafood.core.web;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	/*@Override
	public void addCorsMappings(CorsRegistry registry) {
		//Habilita o CORS globalmente para todos os controllers
		registry.addMapping("/**").allowedMethods("*");
	}*/
	
	@Bean
	public Filter shallowEtagHeaderFilter() {
		//Habilita o filter para adicionar E-Tag nos requests
		return new ShallowEtagHeaderFilter();
	}
}
