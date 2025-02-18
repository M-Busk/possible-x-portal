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

import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DefaultLayoutComponent} from './containers';
import {HomeComponent} from "./views/home/home.component";
import {authGuard} from "./services/mgmt/auth/auth.guard";
import {isAuthenticated} from "./services/mgmt/auth/auth-resolver.service";

const routes: Routes = [
  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      {
        path: '',
        component: HomeComponent
      },
      {
        path: 'participant',
        loadChildren: () =>
          import('./views/participant/participant.module').then((m) => m.ParticipantModule)
      },
      {
        canActivate: [authGuard],
        resolve: { isAuthenticated: isAuthenticated},
        path: 'administration',
        loadChildren: () =>
          import('./views/administration/administration.module').then((m) => m.AdministrationModule)
      },
      {
        path: 'impressum',
        loadChildren: () =>
          import('./views/impressum/impressum.module').then((m) => m.ImpressumModule)
      },
      {
        path: 'login',
        loadChildren: () =>
          import('./views/login/login.module').then((m) => m.LoginModule)
      },
      {
        path: 'licenses',
        loadChildren: () =>
          import('./views/attribution-document/attribution-document.module').then((m) => m.AttributionDocumentModule)
      }
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes), RouterModule.forRoot(routes, { anchorScrolling: 'enabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
