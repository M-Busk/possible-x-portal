import {Component} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-default-layout',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.scss']
})
export class DefaultLayoutComponent {
  isAdminPage = false;
  environment = environment;
  isMainPage = false;

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isAdminPage = event.urlAfterRedirects.includes('administration/management');
        this.isMainPage = event.urlAfterRedirects === '' || event.urlAfterRedirects === '/';
      }
    });
  }

}
