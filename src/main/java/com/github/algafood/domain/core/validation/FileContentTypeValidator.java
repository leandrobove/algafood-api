package com.github.algafood.domain.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private List<String> allowedContentTypes;

	@Override
	public void initialize(FileContentType constraint) {
		this.allowedContentTypes = Arrays.asList(constraint.allowed());
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

		boolean isValid = false;

		if (multipartFile != null) {
			if (allowedContentTypes.contains(multipartFile.getContentType())) {
				isValid = true;
			}
		}

		return isValid;
	}

}
