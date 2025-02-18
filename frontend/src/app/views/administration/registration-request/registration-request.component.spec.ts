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

import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrationRequestComponent} from './registration-request.component';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {AccordionModule} from "@coreui/angular";
import {provideAnimations} from "@angular/platform-browser/animations";
import {IRequestStatus} from "../../../services/mgmt/api/backend";

describe('RegistrationRequestComponent', () => {
  let component: RegistrationRequestComponent;
  let fixture: ComponentFixture<RegistrationRequestComponent>;
  let apiService: jasmine.SpyObj<ApiService>;

  const mockRequest = {
    legalRegistrationNumber: {eori: 'eori', vatID: '', leiCode: ''},
    legalAddress: {streetAddress: 'street', postalCode: '12345', locality: 'HH', countryCode: 'DE', countrySubdivisionCode: 'BY'},
    headquarterAddress: {streetAddress: 'street', postalCode: '12345', locality: 'HH', countryCode: 'DE', countrySubdivisionCode: 'BY'},
    name: 'participant name',
    description: 'description',
    status: IRequestStatus.NEW,
    omejdnConnectorCertificate: {client_id: 'client_id', client_name: 'client', keystore: 'key', password: 'pw', scope: 'scope'},
    vpLink: 'vpLink',
    emailAddress: 'email',
    didData: {did: 'did'}
  };

  beforeEach(() => {
    const apiServiceSpy = jasmine.createSpyObj('ApiService', ['deleteRegistrationRequest', 'acceptRegistrationRequest', 'rejectRegistrationRequest']);
    TestBed.configureTestingModule({
      declarations: [RegistrationRequestComponent],
      imports: [AccordionModule],
      providers: [
        {provide: ApiService, useValue: apiServiceSpy},
        provideAnimations()
      ]
    });
    fixture = TestBed.createComponent(RegistrationRequestComponent);
    component = fixture.componentInstance;
    apiService = TestBed.inject(ApiService) as jasmine.SpyObj<ApiService>;
    component.request = mockRequest;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call deleteRegistrationRequest on apiService when deleteRegistrationRequest is called', async () => {
    apiService.deleteRegistrationRequest.and.returnValue(Promise.resolve(undefined));
    const mockEvent = new Event('click');
    component.deleteRequest(mockEvent, {name: 'test'} as any);

    expect(apiService.deleteRegistrationRequest).toHaveBeenCalled();

  });

  it('should call acceptRegistrationRequest on apiService when acceptRegistrationRequest is called', async () => {
    apiService.acceptRegistrationRequest.and.returnValue(Promise.resolve(undefined));
    const mockEvent = new Event('click');
    component.acceptRequest(mockEvent, {name: 'test'} as any);

    expect(apiService.acceptRegistrationRequest).toHaveBeenCalled();

  });

  it('should call rejectRegistrationRequest on apiService when rejectRegistrationRequest is called', async () => {
    apiService.rejectRegistrationRequest.and.returnValue(Promise.resolve(undefined));
    const mockEvent = new Event('click');
    component.rejectRequest(mockEvent, {name: 'test'} as any);

    expect(apiService.rejectRegistrationRequest).toHaveBeenCalled();

  });
});
