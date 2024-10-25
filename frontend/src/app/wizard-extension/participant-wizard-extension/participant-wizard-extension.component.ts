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

@Component({
  selector: 'app-participant-wizard-extension',
  templateUrl: './participant-wizard-extension.component.html',
  styleUrls: ['./participant-wizard-extension.component.scss']
})
export class ParticipantWizardExtensionComponent {
  @ViewChild("participantRegistrationStatusMessage") public participantRegistrationStatusMessage!: StatusMessageComponent;
  public prefillDone: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  @ViewChild("pxParticipantExtensionWizard") private pxParticipantExtensionWizard: BaseWizardExtensionComponent;
  @ViewChild("gxParticipantWizard") private gxParticipantWizard: BaseWizardExtensionComponent;
  @ViewChild("gxRegistrationNumberWizard") private gxRegistrationNumberWizard: BaseWizardExtensionComponent;

  constructor(
    private apiService: ApiService
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

  public isShapeLoaded(): boolean {
    return this.gxParticipantWizard?.isShapeLoaded() && this.gxRegistrationNumberWizard?.isShapeLoaded()
      && this.pxParticipantExtensionWizard?.isShapeLoaded();
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

    let gxParticipantJson: IGxLegalParticipantCredentialSubject = this.gxParticipantWizard.generateJsonCs();
    let gxRegistrationNumberJson: IGxLegalRegistrationNumberCredentialSubject = this.gxRegistrationNumberWizard.generateJsonCs();
    let pxParticipantExtensionJson: IPxParticipantExtensionCredentialSubject = this.pxParticipantExtensionWizard.generateJsonCs();

    gxParticipantJson["gx:legalRegistrationNumber"] = {"@id": gxRegistrationNumberJson.id} as any;

    let registerParticipantTo: ICreateRegistrationRequestTO = {
      participantCs: gxParticipantJson,
      registrationNumberCs: gxRegistrationNumberJson,
      participantExtensionCs: pxParticipantExtensionJson
    }

    console.log(registerParticipantTo);

    this.apiService.registerParticipant(registerParticipantTo).then(response => {
      console.log(response);
      this.participantRegistrationStatusMessage.showSuccessMessage();
    }).catch((e: HttpErrorResponse) => {
      this.participantRegistrationStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.participantRegistrationStatusMessage.showErrorMessage("Unbekannter Fehler");
    });

  }

  public ngOnDestroy() {
    this.gxParticipantWizard.ngOnDestroy();
    this.gxRegistrationNumberWizard.ngOnDestroy();
    this.participantRegistrationStatusMessage.hideAllMessages();
  }

  protected isWizardFormInvalid(): boolean {
    let participantWizardInvalid = this.gxParticipantWizard?.isWizardFormInvalid();
    let registrationNumberWizardInvalid = this.gxRegistrationNumberWizard?.isWizardFormInvalid();
    let participantExtensionWizardInvalid = this.pxParticipantExtensionWizard?.isWizardFormInvalid();

    return participantWizardInvalid || registrationNumberWizardInvalid || participantExtensionWizardInvalid;
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

}
