import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegistrationRequestManagementComponent } from './registration-request-management/registration-request-management.component';
import {AdministrationRoutingModule} from "./administration-routing.module";
import {
  AccordionButtonDirective,
  AccordionComponent,
  AccordionItemComponent,
  TemplateIdDirective
} from "@coreui/angular";


@NgModule({
  declarations: [
    RegistrationRequestManagementComponent
  ],
  imports: [
    CommonModule,
    AdministrationRoutingModule,
    AccordionComponent,
    AccordionItemComponent,
    AccordionButtonDirective,
    TemplateIdDirective
  ]
})
export class AdministrationModule { }
