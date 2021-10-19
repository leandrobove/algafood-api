package com.github.algafood.domain.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * Classe de implementação da Annotation @Multiplo que valida se um número é multiplo de outro fixado no parametro @Multiplo(numero=5), por exemplo.
 */
public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

	private int numeroMultiplo;

	@Override
	public void initialize(Multiplo multiplo) {
		this.numeroMultiplo = multiplo.numero();
	}

	@Override
	public boolean isValid(Number valueRecebidoDoUsuario, ConstraintValidatorContext context) {

		boolean isValid = false;

		if (valueRecebidoDoUsuario != null) {
			//transforma ambos os valores para BigDecimal
			var valorDecimal = BigDecimal.valueOf(valueRecebidoDoUsuario.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);

			//captura o resto da divisão
			var resto = valorDecimal.remainder(multiploDecimal);

			//verifica se o resto da divisão é igual a zero
			if (BigDecimal.ZERO.compareTo(resto) == 0) {
				isValid = true;
			}
		}

		return isValid;
	}

}
