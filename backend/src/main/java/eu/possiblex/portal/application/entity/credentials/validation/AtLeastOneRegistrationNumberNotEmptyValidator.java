/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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