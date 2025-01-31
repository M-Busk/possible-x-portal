import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrationRequestManagementComponent} from './registration-request-management.component';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {AccordionModule, ModalModule} from '@coreui/angular';
import {CommonViewsModule} from '../../common-views/common-views.module';
import {IRegistrationRequestEntryTO} from "../../../services/mgmt/api/backend";
import {provideAnimations} from "@angular/platform-browser/animations";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";

describe('RegistrationRequestManagementComponent', () => {
  let component: RegistrationRequestManagementComponent;
  let fixture: ComponentFixture<RegistrationRequestManagementComponent>;
  let apiService: jasmine.SpyObj<ApiService>;

  beforeEach(() => {
    const apiServiceSpy = jasmine.createSpyObj('ApiService', ['getRegistrationRequests']);
    TestBed.configureTestingModule({
      declarations: [RegistrationRequestManagementComponent],
      imports: [AccordionModule, ModalModule, CommonViewsModule, MatSortModule, MatPaginatorModule],
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

  it('should call getRegistrationRequests on apiService when getRegistrationRequests is called', async () => {
    const emptyList: IRegistrationRequestEntryTO[] = [];
    apiService.getRegistrationRequests.and.returnValue(Promise.resolve({
      content: emptyList,
      totalElements: 0,
      numberOfElements: 0,
      pageable: null,
      first: null,
      last: null,
      size: 0,
      number: 0,
      sort: null,
      totalPages: 1,
      empty: true
    }));

    component.getRegistrationRequests(undefined);

    expect(apiService.getRegistrationRequests).toHaveBeenCalled();

  });
});
