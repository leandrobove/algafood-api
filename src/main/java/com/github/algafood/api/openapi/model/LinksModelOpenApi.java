package com.github.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Links")

@Setter
@Getter
public class LinksModelOpenApi {

	private LinkModel rel;

	@ApiModel(value = "Link")
	@Setter
	@Getter
	private class LinkModel {

		private String href;
		private boolean templated;
	}
}
