package com.github.algafood.api.exceptionhandler;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problema implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private LocalDateTime dataHora;
	private String mensagem;

}
