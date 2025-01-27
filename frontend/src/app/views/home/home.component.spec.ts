import {ComponentFixture, TestBed} from '@angular/core/testing';

import {HomeComponent} from './home.component';
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {ApiService} from "../../services/mgmt/api/api.service";
import {IEnvironmentTO} from "../../services/mgmt/api/backend";

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let mockEnvironment: IEnvironmentTO = {catalogUiUrl: "https://example.com/"};

  beforeEach(async () => {
    const apiServiceSpy = jasmine.createSpyObj('ApiService', ['getEnvironment']);
    apiServiceSpy.getEnvironment.and.returnValue(Promise.resolve(mockEnvironment));

    await TestBed.configureTestingModule({
      declarations: [HomeComponent],
      imports: [RouterTestingModule,
        HttpClientTestingModule],
      providers: [
        {provide: ApiService, useValue: apiServiceSpy}
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
