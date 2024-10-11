import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ParticipantWizardExtensionComponent} from './participant-wizard-extension.component';
import {WizardAppModule} from "../../sdwizard/wizardapp.module";
import {WizardExtensionModule} from "../wizard-extension.module";

describe('ParticipantWizardExtensionComponent', () => {
  let component: ParticipantWizardExtensionComponent;
  let fixture: ComponentFixture<ParticipantWizardExtensionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParticipantWizardExtensionComponent],
      imports: [
        WizardAppModule,
        WizardExtensionModule
      ]
    });
    fixture = TestBed.createComponent(ParticipantWizardExtensionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
