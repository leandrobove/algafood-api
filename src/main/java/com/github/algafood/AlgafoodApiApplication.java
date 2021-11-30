package com.github.algafood;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.algafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		// define UTC como horário padrão
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(ZoneOffset.UTC.getId())));

		SpringApplication.run(AlgafoodApiApplication.class, args);
	}

}
