import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment";
import {ApiService} from "../../services/mgmt/api/api.service";

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  catalogUiUrl: string = '';
  protected readonly environment = environment;

  constructor(private readonly apiService: ApiService) {
  }

  ngOnInit() {
    this.apiService.getEnvironment().then(response => {
      this.catalogUiUrl = response.catalogUiUrl;
    }).catch(e => {
      console.log(e);
    });
  }

}
