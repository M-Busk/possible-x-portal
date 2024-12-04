import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {IRegistrationRequestEntryTO} from "../../../services/mgmt/api/backend";
import {StatusMessageComponent} from "../../common-views/status-message/status-message.component";
import {HttpErrorResponse} from "@angular/common/http";
import {ModalComponent} from "@coreui/angular";
import {RequestResponse} from "../registration-request/registration-request.component";
import {MatSort, MatSortable, Sort} from "@angular/material/sort";
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-registration-request-management',
  templateUrl: './registration-request-management.component.html',
  styleUrls: ['./registration-request-management.component.scss']
})
export class RegistrationRequestManagementComponent implements OnInit, AfterViewInit {

  @ViewChild("requestListStatusMessage") public requestListStatusMessage!: StatusMessageComponent;
  @ViewChild("operationStatusMessage") public operationStatusMessage!: StatusMessageComponent;
  @ViewChild("responseModal") responseModal: ModalComponent;
  @ViewChild(MatSort) sort: MatSort;
  registrationRequests = new MatTableDataSource<IRegistrationRequestEntryTO>();

  constructor(private apiService: ApiService) {
  }

  async getRegistrationRequestsWithSort() {
    this.registrationRequests.data = await this.apiService.getAllRegistrationRequests();
    this.sort.sort(({id: 'organizationName', start: 'asc'}) as MatSortable);
    this.registrationRequests.sort = this.sort;
  }

  async getRegistrationRequestsWithoutSort() {
    this.registrationRequests.data = await this.apiService.getAllRegistrationRequests();
    this.registrationRequests.sort = this.sort;
  }

  ngOnInit(): void {
    this.handleGetRegistrationRequests();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe((sortState: Sort) => {
      this.customSort(sortState);
    });
  }

  handleGetRegistrationRequests() {
    this.getRegistrationRequestsWithSort().catch((e: HttpErrorResponse) => {
      this.requestListStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.requestListStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }

  showResponse(response: RequestResponse){
    if(!response.isError) {
      this.operationStatusMessage.showSuccessMessage(response.message);
    } else {
      this.operationStatusMessage.showErrorMessage(response.message);
    }

    this.responseModal.visible = true;
    this.handleGetRegistrationRequests();
  }

  async customSort(sortState: Sort) {
    const data = this.registrationRequests.data.slice();
    if (!sortState.active || sortState.direction === '') {
      this.getRegistrationRequestsWithoutSort().catch((e: HttpErrorResponse) => {
        this.requestListStatusMessage.showErrorMessage(e.error.detail);
      }).catch(_ => {
        this.requestListStatusMessage.showErrorMessage("Unknown error occurred");
      });
      return;
    }

    this.registrationRequests.data = data.sort((a, b) => {
      const isAsc = sortState.direction === 'asc';
      switch (sortState.active) {
        case 'organizationName':
          return this.compare(a.name, b.name, isAsc);
        case 'status':
          return this.compare(a.status, b.status, isAsc);
        default:
          return 0;
      }
    });
  }

  compare(a: string | number, b: string | number, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }
}
