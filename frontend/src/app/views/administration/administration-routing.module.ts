import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {RegistrationRequestManagementComponent} from './registration-request-management/registration-request-management.component';

const routes: Routes = [
  {
    path: 'management',
    component: RegistrationRequestManagementComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministrationRoutingModule {
}
