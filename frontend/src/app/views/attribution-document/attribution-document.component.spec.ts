import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttributionDocumentComponent } from './attribution-document.component';
import {RouterTestingModule} from "@angular/router/testing";

describe('AttributionDocumentComponent', () => {
  let component: AttributionDocumentComponent;
  let fixture: ComponentFixture<AttributionDocumentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AttributionDocumentComponent],
      imports: [RouterTestingModule]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AttributionDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
