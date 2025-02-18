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
import {MatPaginatorModule} from "@angular/material/paginator";

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
    MatSortModule,
    MatPaginatorModule
  ]
})
export class AdministrationModule { }
