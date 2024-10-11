import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrationComponent} from './registration.component';
import {GridModule} from '@coreui/angular';
import {CommonViewsModule} from '../../common-views/common-views.module';
import {WizardAppModule} from "../../../sdwizard/wizardapp.module";
import {WizardExtensionModule} from "../../../wizard-extension/wizard-extension.module";
import {ApiService} from '../../../services/mgmt/api/api.service';

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;
  let apiService: jasmine.SpyObj<ApiService>;

  beforeEach(() => {
    const apiServiceSpy = jasmine.createSpyObj('ApiService', ['registerParticipant']);

    TestBed.configureTestingModule({
      declarations: [RegistrationComponent],
      providers: [
        {provide: ApiService, useValue: apiServiceSpy}
      ],
      imports: [GridModule, CommonViewsModule, WizardAppModule, WizardExtensionModule],
    });
    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    apiService = TestBed.inject(ApiService) as jasmine.SpyObj<ApiService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call registerParticipant on apiService when registerParticipant is called', async () => {
    apiService.registerParticipant.and.returnValue(undefined);

    component.wizardExtension.prefillDone.subscribe((value) => {
      if (value) {
        component.wizardExtension.registerParticipant().then(() => {
          expect(apiService.registerParticipant).toHaveBeenCalled();
        });
      }
    });

  });
});
