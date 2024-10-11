import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DefaultLayoutComponent} from './containers';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'participant/registration',
    pathMatch: 'full'
  },
  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      {
        path: 'participant',
        loadChildren: () =>
          import('./views/participant/participant.module').then((m) => m.ParticipantModule)
      },
      {
        path: 'administration',
        loadChildren: () =>
          import('./views/administration/administration.module').then((m) => m.AdministrationModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
