package com.github.algafood.api.dto.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.github.algafood.core.validation.TaxaFrete;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInput {
	
	@ApiModelProperty(example = "Thai Gourmet", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "12.00", required = true)
	@NotNull
	@TaxaFrete
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdInput cozinha;

	@Valid
	@NotNull
	private EnderecoInput endereco;
}
