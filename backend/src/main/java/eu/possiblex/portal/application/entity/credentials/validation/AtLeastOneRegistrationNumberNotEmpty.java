package eu.possiblex.portal.application.entity.credentials.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AtLeastOneRegistrationNumberNotEmptyValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneRegistrationNumberNotEmpty {
    String message() default "At least one field must be not empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
