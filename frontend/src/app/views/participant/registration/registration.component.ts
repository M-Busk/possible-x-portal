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

import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {
  ParticipantWizardExtensionComponent
} from "../../../wizard-extension/participant-wizard-extension/participant-wizard-extension.component";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements AfterViewInit {

  @ViewChild("wizardExtension") wizardExtension: ParticipantWizardExtensionComponent;

  ngAfterViewInit(): void {
    this.prefillWizardNewParticipant();
  }


  async prefillWizardNewParticipant() {
    this.wizardExtension.loadShape("did:web:WILL_BE_GENERATED", "urn:uuid:WILL_BE_GENERATED");

    this.wizardExtension.prefillFields([]);
  }
}
