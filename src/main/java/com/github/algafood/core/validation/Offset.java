package com.github.algafood.core.validation;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "[+-]([0-1][0-9]):([0-6][0-5])", message = "Padrão de offset inválido.")
public @interface Offset {

	String message() default "Offset inválido";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
