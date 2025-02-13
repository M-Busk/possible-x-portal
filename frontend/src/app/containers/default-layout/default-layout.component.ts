import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {ApiService} from "../../services/mgmt/api/api.service";
import {AuthService} from "../../services/mgmt/auth/auth.service";

@Component({
  selector: 'app-default-layout',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.scss']
})
export class DefaultLayoutComponent implements OnInit {
  isAdminPage = false;
  environment = environment;
  isMainPage = false;
  versionNumber: string = '';
  versionDate: string = '';

  constructor(private readonly router: Router, private readonly apiService: ApiService, protected auth: AuthService) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isAdminPage = event.urlAfterRedirects.includes('administration/management');
        this.isMainPage = event.urlAfterRedirects === '' || event.urlAfterRedirects === '/';
      }
    });
  }

  ngOnInit() {
    this.apiService.getVersion().then(response => {
      this.versionNumber = response.version;
      this.versionDate = response.date;
    }).catch(e => {
      console.log(e);
    });
  }
}
