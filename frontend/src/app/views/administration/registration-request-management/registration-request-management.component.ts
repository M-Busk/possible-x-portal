import {Component, OnInit} from '@angular/core';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {IRegistrationRequestListTO} from "../../../services/mgmt/api/backend";

@Component({
  selector: 'app-registration-request-management',
  templateUrl: './registration-request-management.component.html',
  styleUrls: ['./registration-request-management.component.scss']
})
export class RegistrationRequestManagementComponent implements OnInit{
  registrationRequests: IRegistrationRequestListTO[] = [];

  constructor(private apiService: ApiService) {}

  async getRegistrationRequests() {
    this.registrationRequests = await this.apiService.getAllRegistrationRequests();
  }

  ngOnInit(): void {
    this.getRegistrationRequests();
  }
}
