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

    this.wizardExtension.prefillFields([
      {
        "@type": "gx:LegalParticipant",
        "gx:legalRegistrationNumber": "urn:uuid:WILL_BE_GENERATED"
      } as any]);
  }
}
