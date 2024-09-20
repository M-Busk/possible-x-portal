import {Component, ViewChild} from '@angular/core';
import {BaseWizardExtensionComponent} from "../base-wizard-extension/base-wizard-extension.component";
import {StatusMessageComponent} from "../../views/common-views/status-message/status-message.component";
import {ApiService} from "../../services/mgmt/api/api.service";
import {BehaviorSubject, takeWhile} from "rxjs";
import {isGxLegalParticipantCs, isGxLegalRegistrationNumberCs} from "../../utils/credential-utils";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-participant-wizard-extension',
  templateUrl: './participant-wizard-extension.component.html',
  styleUrls: ['./participant-wizard-extension.component.scss']
})
export class ParticipantWizardExtensionComponent {
  @ViewChild("gxParticipantWizard") private gxParticipantWizard: BaseWizardExtensionComponent;
  @ViewChild("gxRegistrationNumberWizard") private gxRegistrationNumberWizard: BaseWizardExtensionComponent;
  @ViewChild("participantRegistrationStatusMessage") public participantRegistrationStatusMessage!: StatusMessageComponent;

  public prefillDone: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(
    private apiService: ApiService
  ) {}

  public async loadShape(id: string, registrationNumberId: string): Promise<void> {
    this.prefillDone.next(false);
    console.log("Loading participant shape");
    await this.gxParticipantWizard.loadShape(this.apiService.getGxLegalParticipantShape(), id);
    await this.gxRegistrationNumberWizard.loadShape(this.apiService.getGxLegalRegistrationNumberShape(), registrationNumberId);

  }

  public isShapeLoaded(): boolean {
    return this.gxParticipantWizard?.isShapeLoaded() && this.gxRegistrationNumberWizard?.isShapeLoaded();
  }

  private prefillHandleCs(cs: any) { // TODO add java classes
    if (isGxLegalParticipantCs(cs)) {
      this.gxParticipantWizard.prefillFields(cs, ["gx:legalRegistrationNumber", "gx:subOrganization", "gx:parentOrganization"]);
    }
    if (isGxLegalRegistrationNumberCs(cs)) {
      this.gxRegistrationNumberWizard.prefillFields(cs, []);
    }
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
    console.log("Create offer.");
    this.participantRegistrationStatusMessage.hideAllMessages();

    let gxParticipantJson: any = this.gxParticipantWizard.generateJsonCs();// TODO add java classes
    let gxRegistrationNumberJson: any = this.gxRegistrationNumberWizard.generateJsonCs();// TODO add java classes

    let createOfferTo: any = {
    }

    console.log(createOfferTo);

    this.apiService.registerParticipant().then(response => {
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

}
