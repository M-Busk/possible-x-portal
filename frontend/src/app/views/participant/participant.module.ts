import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RegistrationComponent} from './registration/registration.component';
import {ParticipantRoutingModule} from "./participant-routing.module";
import {WizardExtensionModule} from "../../wizard-extension/wizard-extension.module";
import {ContainerComponent} from "@coreui/angular";


@NgModule({
  declarations: [
    RegistrationComponent
  ],
  imports: [
    CommonModule,
    ParticipantRoutingModule,
    WizardExtensionModule,
    ContainerComponent
  ]
})
export class ParticipantModule {
}
