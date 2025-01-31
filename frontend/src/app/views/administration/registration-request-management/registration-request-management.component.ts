import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {IRegistrationRequestEntryTO} from "../../../services/mgmt/api/backend";
import {StatusMessageComponent} from "../../common-views/status-message/status-message.component";
import {HttpErrorResponse} from "@angular/common/http";
import {ModalComponent} from "@coreui/angular";
import {RequestResponse} from "../registration-request/registration-request.component";
import {MatSort, Sort} from "@angular/material/sort";
import {MatTableDataSource} from '@angular/material/table';

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
  pageSize = 10;
  pageIndex = 0;
  totalNumberOfRegistrationRequests = 0;

  constructor(private readonly apiService: ApiService) {
  }

  async getRegistrationRequests(params: any) {
    const requestsResponseTO = await this.apiService.getRegistrationRequests(params);

    console.log(requestsResponseTO);

    this.registrationRequests.data = requestsResponseTO.content;
    this.totalNumberOfRegistrationRequests = requestsResponseTO.totalElements;
    this.registrationRequests.sort = this.sort;
  }

  ngOnInit(): void {
    // get registration requests with default sorting
    this.handleGetRegistrationRequests();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe((sortState: Sort) => {
      if (!sortState.active || sortState.direction === '') {
        // do nothing if sort is not active or direction is empty
        return;
      }
      // get registration requests with new sorting
      this.handleGetRegistrationRequests(sortState);
    });
  }

  handleGetRegistrationRequests(sortState: Sort = undefined) {
    // set params for pagination
    let params: any = {page: this.pageIndex, size: this.pageSize};

    // set sorting params if sortState is provided and active
    if (sortState?.active && sortState?.direction !== '') {
      params.sort = sortState.active + ',' + sortState.direction;
    }

    // get registration requests with params for pagination and sorting
    this.getRegistrationRequests(params)
      .catch((e: HttpErrorResponse) => {
        this.requestListStatusMessage.showErrorMessage(e.error.detail);
      }).catch(_ => {
      this.requestListStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }

  showResponse(response: RequestResponse) {
    if (!response.isError) {
      this.operationStatusMessage.showSuccessMessage(response.message);
    } else {
      this.operationStatusMessage.showErrorMessage(response.message);
    }

    this.responseModal.visible = true;
    this.handleGetRegistrationRequests(this.sort);
  }

  onPageChange(event: any): void {
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
    this.handleGetRegistrationRequests(this.sort);
  }
}
