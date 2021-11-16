package com.github.algafood.domain.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

/*
 * Classe de implementação da Annotation @Multiplo que valida se um número é multiplo de outro fixado no parametro @Multiplo(numero=5), por exemplo.
 */
public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

	private DataSize maxSize;

	@Override
	public void initialize(FileSize fileSize) {
		this.maxSize = DataSize.parse(fileSize.max());
	}

	@Override
	public boolean isValid(MultipartFile valueRecebidoDoUsuario, ConstraintValidatorContext context) {

		boolean isValid = false;

		if (valueRecebidoDoUsuario != null) {
			if (valueRecebidoDoUsuario.getSize() <= maxSize.toBytes()) {
				isValid = true;
			}
		}

		return isValid;
	}

}
