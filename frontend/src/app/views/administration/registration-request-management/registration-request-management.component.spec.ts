import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrationRequestManagementComponent} from './registration-request-management.component';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {AccordionModule, ModalModule} from '@coreui/angular';
import {CommonViewsModule} from '../../common-views/common-views.module';
import {IRegistrationRequestEntryTO} from "../../../services/mgmt/api/backend";
import {provideAnimations} from "@angular/platform-browser/animations";

describe('RegistrationRequestManagementComponent', () => {
  let component: RegistrationRequestManagementComponent;
  let fixture: ComponentFixture<RegistrationRequestManagementComponent>;
  let apiService: jasmine.SpyObj<ApiService>;

  beforeEach(() => {
    const apiServiceSpy = jasmine.createSpyObj('ApiService', ['getAllRegistrationRequests']);
    TestBed.configureTestingModule({
      declarations: [RegistrationRequestManagementComponent],
      imports: [AccordionModule, ModalModule, CommonViewsModule],
      providers: [
        {provide: ApiService, useValue: apiServiceSpy},
        provideAnimations()
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
});
