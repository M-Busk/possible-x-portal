import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {ApiService} from "../../services/mgmt/api/api.service";

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
  authToken: string | null = null;

  constructor(private router: Router, private apiService: ApiService) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isAdminPage = event.urlAfterRedirects.includes('administration/management');
        this.isMainPage = event.urlAfterRedirects === '' || event.urlAfterRedirects === '/';
      }
    });
  }

  ngOnInit() {
    this.authToken = sessionStorage.getItem('authToken');
    this.apiService.getVersion().then(response => {
      this.versionNumber = response.version;
      this.versionDate = response.date;
    }).catch(e => {
      console.log(e);
    });
  }
}
