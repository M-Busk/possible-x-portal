import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { RegistrationRequestManagementComponent } from './registration-request-management/registration-request-management.component';
import {AdministrationRoutingModule} from "./administration-routing.module";
import {
  AccordionButtonDirective,
  AccordionComponent,
  AccordionItemComponent,
  ButtonDirective,
  ModalBodyComponent,
  ModalComponent,
  ModalFooterComponent,
  ModalHeaderComponent, ModalToggleDirective,
  TemplateIdDirective
} from "@coreui/angular";
import {CommonViewsModule} from "../common-views/common-views.module";
import { RegistrationRequestComponent } from './registration-request/registration-request.component';
import {MatSortModule} from "@angular/material/sort";


@NgModule({
  declarations: [
    RegistrationRequestManagementComponent,
    RegistrationRequestComponent
  ],
  exports: [
    RegistrationRequestComponent
  ],
  imports: [
    CommonModule,
    AdministrationRoutingModule,
    AccordionComponent,
    AccordionItemComponent,
    AccordionButtonDirective,
    TemplateIdDirective,
    NgOptimizedImage,
    ButtonDirective,
    CommonViewsModule,
    ModalComponent,
    ModalBodyComponent,
    ModalHeaderComponent,
    ModalFooterComponent,
    ModalToggleDirective,
    MatSortModule
  ]
})
export class AdministrationModule { }
