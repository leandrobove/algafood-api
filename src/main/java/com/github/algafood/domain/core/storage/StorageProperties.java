package com.github.algafood.domain.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "algafood.storage")

@Getter
@Setter
public class StorageProperties {

	private Local local = new Local();

	@Getter
	@Setter
	public class Local {

		private Path diretorioFotos;

	}

}
