/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {IRegistrationRequestEntryTO, IRequestStatus} from "../../../services/mgmt/api/backend";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiService} from "../../../services/mgmt/api/api.service";
import {commonMessages} from "../../../../environments/common-messages";

export interface RequestResponse {
  isError: boolean;
  message: string;
}

@Component({
  selector: 'app-registration-request',
  templateUrl: './registration-request.component.html',
  styleUrls: ['./registration-request.component.scss']
})
export class RegistrationRequestComponent implements OnInit, OnChanges {
  @Input() request: IRegistrationRequestEntryTO;
  @Output() response: EventEmitter<RequestResponse> = new EventEmitter();

  isClickableAccept: boolean = true;
  isClickableReject: boolean = true;
  isClickableDelete: boolean = true;

  accepting: boolean = false;
  rejecting: boolean = false;
  deleting: boolean = false;

  constructor(private readonly apiService: ApiService) {
  }

  ngOnInit() {
    this.computeButtonStates();
  }

  ngOnChanges(changes: SimpleChanges) {
    this.computeButtonStates();
  }

  computeButtonStates() {
    switch (this.request.status) {
      case IRequestStatus.NEW:
      case IRequestStatus.ACCEPTED:
        this.isClickableAccept = true;
        this.isClickableReject = true;
        this.isClickableDelete = true;
        break;
      case IRequestStatus.REJECTED:
        this.isClickableAccept = false;
        this.isClickableReject = false;
        this.isClickableDelete = true;
        break;
      default:
        this.isClickableAccept = false;
        this.isClickableReject = false;
        this.isClickableDelete = false;
    }
  }

  disableAllButtons() {
    this.isClickableAccept = false;
    this.isClickableReject = false;
    this.isClickableDelete = false;
  }

  async acceptRequest(event: Event, request: IRegistrationRequestEntryTO): Promise<void> {
    event.stopPropagation();
    this.disableAllButtons();
    this.accepting = true;
    this.apiService.acceptRegistrationRequest(request.name).then(() => {
      console.log("Accept request for: " + request.name);
      this.response.emit({
        isError: false,
        message: "Request accepted successfully. Participant was checked for compliance and stored in the catalog."
      });
      this.accepting = false;
    }).catch((e: HttpErrorResponse) => {
      if (e.status === 500) {
        this.response.emit({isError: true, message: commonMessages.general_error});
      } else {
        this.response.emit({isError: true, message: e.error.details});
      }
      this.accepting = false;
    }).catch(_ => {
      this.response.emit({isError: true, message: commonMessages.general_error});
      this.accepting = false;
    });
  }

  async deleteRequest(event: Event, request: IRegistrationRequestEntryTO): Promise<void> {
    event.stopPropagation();
    this.disableAllButtons();
    this.deleting = true;
    this.apiService.deleteRegistrationRequest(request.name).then(() => {
      console.log("Delete request for: " + request.name);
      this.response.emit({isError: false, message: "Request deleted successfully"});
      this.deleting = false;
    }).catch((e: HttpErrorResponse) => {
      if (e.status === 500) {
        this.response.emit({isError: true, message: commonMessages.general_error});
      } else {
        this.response.emit({isError: true, message: e.error.details});
      }
      this.deleting = false;
    }).catch(_ => {
      this.response.emit({isError: true, message: commonMessages.general_error});
      this.deleting = false;
    });
  }

  async rejectRequest(event: Event, request: IRegistrationRequestEntryTO): Promise<void> {
    event.stopPropagation();
    this.disableAllButtons();
    this.rejecting = true;
    this.apiService.rejectRegistrationRequest(request.name).then(() => {
      console.log("Reject request for: " + request.name);
      this.response.emit({isError: false, message: "Request rejected successfully"});
      this.rejecting = false;
    }).catch((e: HttpErrorResponse) => {
      if (e.status === 500) {
        this.response.emit({isError: true, message: commonMessages.general_error});
      } else {
        this.response.emit({isError: true, message: e.error.details});
      }
      this.rejecting = false;
    }).catch(_ => {
      this.response.emit({isError: true, message: commonMessages.general_error});
      this.rejecting = false;
    });
  }

  protected isRegistrationRequestNew(request: IRegistrationRequestEntryTO): boolean {
    return request.status === IRequestStatus.NEW;
  }

  protected isRegistrationRequestAccepted(request: IRegistrationRequestEntryTO): boolean {
    return request.status === IRequestStatus.ACCEPTED;
  }

  protected isRegistrationRequestRejected(request: IRegistrationRequestEntryTO): boolean {
    return request.status === IRequestStatus.REJECTED;
  }

  protected isRegistrationRequestCompleted(request: IRegistrationRequestEntryTO): boolean {
    return request.status === IRequestStatus.COMPLETED;
  }

}
