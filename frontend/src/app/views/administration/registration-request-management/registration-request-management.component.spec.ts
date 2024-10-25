import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrationRequestManagementComponent} from './registration-request-management.component';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {AccordionModule} from '@coreui/angular';
import {CommonViewsModule} from '../../common-views/common-views.module';
import {IRegistrationRequestEntryTO} from "../../../services/mgmt/api/backend";

describe('RegistrationRequestManagementComponent', () => {
  let component: RegistrationRequestManagementComponent;
  let fixture: ComponentFixture<RegistrationRequestManagementComponent>;
  let apiService: jasmine.SpyObj<ApiService>;

  beforeEach(() => {
    const apiServiceSpy = jasmine.createSpyObj('ApiService', ['getAllRegistrationRequests', 'deleteRegistrationRequest', 'acceptRegistrationRequest', 'rejectRegistrationRequest']);
    TestBed.configureTestingModule({
      declarations: [RegistrationRequestManagementComponent],
      imports: [AccordionModule, CommonViewsModule],
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
    const emptyList: IRegistrationRequestEntryTO[] = [];
    apiService.getAllRegistrationRequests.and.returnValue(Promise.resolve(emptyList));

    component.getRegistrationRequests();

    expect(apiService.getAllRegistrationRequests).toHaveBeenCalled();

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
