import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantWizardExtensionComponent } from './participant-wizard-extension.component';

describe('ParticipantWizardExtensionComponent', () => {
  let component: ParticipantWizardExtensionComponent;
  let fixture: ComponentFixture<ParticipantWizardExtensionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParticipantWizardExtensionComponent]
    });
    fixture = TestBed.createComponent(ParticipantWizardExtensionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
