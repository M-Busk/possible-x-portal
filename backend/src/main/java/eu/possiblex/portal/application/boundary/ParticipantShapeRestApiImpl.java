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

package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.business.control.SdCreationWizardApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipantShapeRestApiImpl implements ParticipantShapeRestApi {

    private static final String ECOSYSTEM_GAIAX = "gx";

    private static final String ECOSYSTEM_POSSIBLE = "px";

    private final SdCreationWizardApiService sdCreationWizardApiService;

    public ParticipantShapeRestApiImpl(@Autowired SdCreationWizardApiService sdCreationWizardApiService) {

        this.sdCreationWizardApiService = sdCreationWizardApiService;
    }

    /**
     * Retrieve the Gaia-X legal participant shape from the SD Creation Wizard API and return it.
     *
     * @return catalog shape
     */
    @Override
    public String getGxLegalParticipantShape() {

        return sdCreationWizardApiService.getShapeByName(ECOSYSTEM_GAIAX, "Legalparticipant.json");
    }

    /**
     * Retrieve the Gaia-X legal registration number shape from the SD Creation Wizard API and return it.
     *
     * @return catalog shape
     */
    @Override
    public String getGxLegalRegistrationNumberShape() {

        return sdCreationWizardApiService.getShapeByName(ECOSYSTEM_GAIAX, "Legalregistrationnumber.json");
    }

    /**
     * Retrieve the Possible-X participant extension shape from the SD Creation Wizard API and return it.
     *
     * @return catalog shape
     */
    @Override
    public String getPxParticipantExtension() {

        return sdCreationWizardApiService.getShapeByName(ECOSYSTEM_POSSIBLE, "Possiblexlegalparticipantextension.json");
    }
}
