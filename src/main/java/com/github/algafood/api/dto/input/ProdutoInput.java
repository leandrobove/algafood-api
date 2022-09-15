package com.github.algafood.api.dto.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {

	@ApiModelProperty(example = "Hot roll de salmão com cream cheese e molho tarê", required = true)
	@NotBlank
	private String nome;

	@ApiModelProperty(example = "Acompanha hashi", required = true)
	@NotBlank
	private String descricao;

	@ApiModelProperty(example = "39.90", required = true)
	@NotNull
	@PositiveOrZero
	private BigDecimal preco;

	@ApiModelProperty(example = "true", required = true)
	@NotNull
	private Boolean ativo;

}
