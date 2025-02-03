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
        path: 'attribution',
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
