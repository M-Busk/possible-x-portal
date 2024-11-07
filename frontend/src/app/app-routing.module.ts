import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DefaultLayoutComponent} from './containers';
import {HomeComponent} from "./views/home/home.component";

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
        path: 'administration',
        loadChildren: () =>
          import('./views/administration/administration.module').then((m) => m.AdministrationModule)
      }
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
