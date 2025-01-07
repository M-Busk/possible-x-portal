import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';

import {
  AvatarModule,
  ButtonGroupModule,
  ButtonModule,
  CardModule,
  FormModule,
  GridModule,
  NavModule,
  ProgressModule,
  TableModule,
  TabsModule,
} from '@coreui/angular';
import {IconModule} from '@coreui/icons-angular';

import {ImpressumRoutingModule} from './impressum-routing.module';
import {ImpressumComponent} from './impressum.component';
import {RouterModule} from "@angular/router";

@NgModule({
  imports: [
    ImpressumRoutingModule,
    CardModule,
    NavModule,
    IconModule,
    TabsModule,
    CommonModule,
    GridModule,
    ProgressModule,
    ReactiveFormsModule,
    ButtonModule,
    FormModule,
    ButtonModule,
    ButtonGroupModule,
    AvatarModule,
    TableModule,
    RouterModule
  ],
  declarations: [ImpressumComponent],
})
export class ImpressumModule {
}
