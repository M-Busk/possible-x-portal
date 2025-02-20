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

import {Component, ViewChild} from '@angular/core';
import {BaseWizardExtensionComponent} from "../base-wizard-extension/base-wizard-extension.component";
import {StatusMessageComponent} from "../../views/common-views/status-message/status-message.component";
import {ApiService} from "../../services/mgmt/api/api.service";
import {BehaviorSubject, takeWhile} from "rxjs";
import {isGxLegalParticipantCs, isGxLegalRegistrationNumberCs} from "../../utils/credential-utils";
import {HttpErrorResponse} from "@angular/common/http";
import {
  ICreateRegistrationRequestTO,
  IGxLegalParticipantCredentialSubject,
  IGxLegalRegistrationNumberCredentialSubject,
  IPojoCredentialSubject,
  IPxParticipantExtensionCredentialSubject
} from "../../services/mgmt/api/backend";
import {commonMessages} from "../../../environments/common-messages";

@Component({
  selector: 'app-participant-wizard-extension',
  templateUrl: './participant-wizard-extension.component.html',
  styleUrls: ['./participant-wizard-extension.component.scss']
})
export class ParticipantWizardExtensionComponent {
  @ViewChild("participantRegistrationStatusMessage") public participantRegistrationStatusMessage!: StatusMessageComponent;
  public prefillDone: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  @ViewChild("pxParticipantExtensionWizard") private readonly pxParticipantExtensionWizard: BaseWizardExtensionComponent;
  @ViewChild("gxParticipantWizard") private readonly gxParticipantWizard: BaseWizardExtensionComponent;
  @ViewChild("gxRegistrationNumberWizard") private readonly gxRegistrationNumberWizard: BaseWizardExtensionComponent;

  constructor(
    private readonly apiService: ApiService
  ) {
  }

  public async loadShape(id: string, registrationNumberId: string): Promise<void> {
    this.prefillDone.next(false);
    console.log("Loading participant shape");

    let participantShapeSource = await this.apiService.getGxLegalParticipantShape();
    participantShapeSource = this.adaptGxShape(participantShapeSource, "LegalParticipant", ["legalRegistrationNumber"]);
    let participantExtensionShape = await this.apiService.getPxParticipantExtension();

    await this.pxParticipantExtensionWizard.loadShape(Promise.resolve(participantExtensionShape), "will:be:replaced");
    await this.gxParticipantWizard.loadShape(Promise.resolve(participantShapeSource), id);
    await this.gxRegistrationNumberWizard.loadShape(this.apiService.getGxLegalRegistrationNumberShape(), registrationNumberId);

  }

  public prefillFields(csList: IPojoCredentialSubject[]) {
    for (let cs of csList) {
      this.prefillHandleCs(cs)
    }

    this.gxParticipantWizard.prefillDone
      .pipe(
        takeWhile(done => !done, true)
      )
      .subscribe(done => {
        if (done) {
          this.gxRegistrationNumberWizard.prefillDone.pipe(
            takeWhile(done => !done, true)
          )
            .subscribe(done => {
              if (done) {
                this.prefillDone.next(true);
              }
            });
        }
      });
  }

  async registerParticipant() {
    console.log("Register participant.");
    this.participantRegistrationStatusMessage.hideAllMessages();
    this.participantRegistrationStatusMessage.showInfoMessage();

    let gxParticipantJson: IGxLegalParticipantCredentialSubject = this.gxParticipantWizard.generateJsonCs();
    let gxRegistrationNumberJson: IGxLegalRegistrationNumberCredentialSubject = this.gxRegistrationNumberWizard.generateJsonCs();
    let pxParticipantExtensionJson: IPxParticipantExtensionCredentialSubject = this.pxParticipantExtensionWizard.generateJsonCs();

    gxParticipantJson["gx:legalRegistrationNumber"] = {"@id": gxRegistrationNumberJson.id} as any;

    let registerParticipantTo: ICreateRegistrationRequestTO = {
      participantCs: gxParticipantJson,
      registrationNumberCs: gxRegistrationNumberJson,
      participantExtensionCs: pxParticipantExtensionJson
    }

    const trimmedRegisterParticipantTo = this.trimStringsInDataStructure(registerParticipantTo);

    console.log(trimmedRegisterParticipantTo);

    this.apiService.registerParticipant(trimmedRegisterParticipantTo).then(response => {
      console.log(response);
      this.participantRegistrationStatusMessage.showSuccessMessage();
    }).catch((e: HttpErrorResponse) => {
      if (e.status === 500) {
        this.participantRegistrationStatusMessage.showErrorMessage(commonMessages.general_error);
      } else {
        this.participantRegistrationStatusMessage.showErrorMessage(e.error.details);
      }
    }).catch(_ => {
      this.participantRegistrationStatusMessage.showErrorMessage(commonMessages.general_error);
    });

  }

  public ngOnDestroy() {
    this.gxParticipantWizard.ngOnDestroy();
    this.gxRegistrationNumberWizard.ngOnDestroy();
    this.participantRegistrationStatusMessage.hideAllMessages();
  }

  trimStringsInDataStructure(obj: any): any {
    if (typeof obj === 'string') {
      return obj.trim();
    } else if (Array.isArray(obj)) {
      return obj.map(this.trimStringsInDataStructure);
    } else if (typeof obj === 'object' && obj !== null) {
      return Object.keys(obj).reduce((acc, key) => {
        acc[key] = this.trimStringsInDataStructure(obj[key]);
        return acc;
      }, {} as any);
    }
    return obj;
  }

  protected isWizardFormInvalid(): boolean {
    let participantWizardInvalid = this.gxParticipantWizard?.isWizardFormInvalid();
    let registrationNumberWizardInvalid = this.gxRegistrationNumberWizard?.isWizardFormInvalid();
    let participantExtensionWizardInvalid = this.pxParticipantExtensionWizard?.isWizardFormInvalid();

    return participantWizardInvalid || registrationNumberWizardInvalid || participantExtensionWizardInvalid || this.isRegistrationNumberInvalid();
  }

  protected isRegistrationNumberInvalid(): boolean {
    if (this.gxRegistrationNumberWizard?.isWizardShapePresent()) {
      let gxRegistrationNumberJson: IGxLegalRegistrationNumberCredentialSubject = this.gxRegistrationNumberWizard.generateJsonCs();

      let leiCode = gxRegistrationNumberJson["gx:leiCode"]?.["@value"];
      let vatID = gxRegistrationNumberJson["gx:vatID"]?.["@value"];
      let eori = gxRegistrationNumberJson["gx:EORI"]?.["@value"];

      return !this.isFieldFilled(leiCode) && !this.isFieldFilled(vatID) && !this.isFieldFilled(eori);
    } else {
      return false;
    }
  }

  protected adaptGxShape(shapeSource: any, shapeName: string, excludedFields: string[]) {
    if (typeof shapeSource !== 'object' || shapeSource === null) {
      console.error("Invalid input: shape is not of expected type.");
      return null;
    }

    shapeSource.shapes.forEach((shape: any) => {
      if (shape.targetClassName === shapeName) {
        shape.constraints = shape.constraints.filter((constraint: any) => {
          return !(constraint.path.prefix === "gx" && excludedFields.includes(constraint.path.value));
        });
      }
    });

    console.log(shapeSource);
    return shapeSource;
  }

  private prefillHandleCs(cs: IPojoCredentialSubject) {
    if (isGxLegalParticipantCs(cs)) {
      this.gxParticipantWizard.prefillFields(cs, []);
    }
    if (isGxLegalRegistrationNumberCs(cs)) {
      this.gxRegistrationNumberWizard.prefillFields(cs, []);
    }
  }

  private isFieldFilled(str: string) {
    if (!str || str.trim().length === 0) {
      return false;
    }
    return true;
  }

}
