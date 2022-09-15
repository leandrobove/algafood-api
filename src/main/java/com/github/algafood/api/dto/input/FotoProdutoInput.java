package com.github.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.github.algafood.core.validation.FileContentType;
import com.github.algafood.core.validation.FileSize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoInput {

	@ApiModelProperty(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", required = true)
	@NotNull
	@FileSize(max = "500KB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	private MultipartFile arquivo;

	@ApiModelProperty(value = "Descrição da foto do produto", required = true)
	@NotBlank
	private String descricao;

}
