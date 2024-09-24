import {Component, ViewChild} from '@angular/core';
import {BaseWizardExtensionComponent} from "../base-wizard-extension/base-wizard-extension.component";
import {StatusMessageComponent} from "../../views/common-views/status-message/status-message.component";
import {ApiService} from "../../services/mgmt/api/api.service";
import {BehaviorSubject, takeWhile} from "rxjs";
import {isGxLegalParticipantCs, isGxLegalRegistrationNumberCs} from "../../utils/credential-utils";
import {HttpErrorResponse} from "@angular/common/http";
import {IRegistrationRequestTO} from "../../services/mgmt/api/backend";

@Component({
  selector: 'app-participant-wizard-extension',
  templateUrl: './participant-wizard-extension.component.html',
  styleUrls: ['./participant-wizard-extension.component.scss']
})
export class ParticipantWizardExtensionComponent {
  @ViewChild("participantRegistrationStatusMessage") public participantRegistrationStatusMessage!: StatusMessageComponent;
  public prefillDone: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  @ViewChild("gxParticipantWizard") private gxParticipantWizard: BaseWizardExtensionComponent;
  @ViewChild("gxRegistrationNumberWizard") private gxRegistrationNumberWizard: BaseWizardExtensionComponent;

  constructor(
    private apiService: ApiService
  ) {
  }

  public async loadShape(id: string, registrationNumberId: string): Promise<void> {
    this.prefillDone.next(false);
    console.log("Loading participant shape");
    await this.gxParticipantWizard.loadShape(this.apiService.getGxLegalParticipantShape(), id);
    await this.gxRegistrationNumberWizard.loadShape(this.apiService.getGxLegalRegistrationNumberShape(), registrationNumberId);

  }

  public isShapeLoaded(): boolean {
    return this.gxParticipantWizard?.isShapeLoaded() && this.gxRegistrationNumberWizard?.isShapeLoaded();
  }

  public prefillFields(csList: any[]) { // TODO add java classes
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

    let gxParticipantJson: any = this.gxParticipantWizard.generateJsonCs();// TODO add java classes
    let gxRegistrationNumberJson: any = this.gxRegistrationNumberWizard.generateJsonCs();// TODO add java classes

    let registerParticipantTo: IRegistrationRequestTO = {
      participantCs: gxParticipantJson,
      registrationNumberCs: gxRegistrationNumberJson
    }

    console.log(registerParticipantTo);

    this.apiService.registerParticipant(registerParticipantTo).then(response => {
      console.log(response);
      this.participantRegistrationStatusMessage.showSuccessMessage("", 20000);
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

    return participantWizardInvalid || registrationNumberWizardInvalid;
  }

  private prefillHandleCs(cs: any) { // TODO add java classes
    if (isGxLegalParticipantCs(cs)) {
      this.gxParticipantWizard.prefillFields(cs, ["gx:legalRegistrationNumber"]);
    }
    if (isGxLegalRegistrationNumberCs(cs)) {
      this.gxRegistrationNumberWizard.prefillFields(cs, []);
    }
  }

}
