package eu.possiblex.portal.application.entity.credentials.validation;

import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import io.netty.util.internal.StringUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtLeastOneRegistrationNumberNotEmptyValidator
    implements ConstraintValidator<AtLeastOneRegistrationNumberNotEmpty, GxLegalRegistrationNumberCredentialSubject> {

    @Override
    public boolean isValid(GxLegalRegistrationNumberCredentialSubject cs, ConstraintValidatorContext context) {

        // object must be non-null
        if (cs == null) {
            return false;
        }

        // at least one registration number field must be non-null and not empty
        return !StringUtil.isNullOrEmpty(cs.getEori()) || !StringUtil.isNullOrEmpty(cs.getVatID())
            || !StringUtil.isNullOrEmpty(cs.getLeiCode());
    }
}