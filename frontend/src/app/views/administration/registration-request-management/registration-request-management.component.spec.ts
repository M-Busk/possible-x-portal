import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationRequestManagementComponent } from './registration-request-management.component';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {IRegistrationRequestListTO} from "../../../services/mgmt/api/backend";

describe('RegistrationRequestManagementComponent', () => {
  let component: RegistrationRequestManagementComponent;
  let fixture: ComponentFixture<RegistrationRequestManagementComponent>;
  let apiService: jasmine.SpyObj<ApiService>;

  beforeEach(() => {
    const apiServiceSpy = jasmine.createSpyObj('ApiService', ['getAllRegistrationRequests']);
    TestBed.configureTestingModule({
      declarations: [RegistrationRequestManagementComponent],
      providers: [
        {provide: ApiService, useValue: apiServiceSpy}
      ]
    });
    fixture = TestBed.createComponent(RegistrationRequestManagementComponent);
    component = fixture.componentInstance;
    apiService = TestBed.inject(ApiService) as jasmine.SpyObj<ApiService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getAllRegistrationRequests on apiService when getAllRegistrationRequests is called', async () => {
    const emptyList: IRegistrationRequestListTO[] = [];
    apiService.getAllRegistrationRequests.and.returnValue(Promise.resolve(emptyList));

    component.getRegistrationRequests();

    expect(apiService.getAllRegistrationRequests).toHaveBeenCalled();

  });
});
