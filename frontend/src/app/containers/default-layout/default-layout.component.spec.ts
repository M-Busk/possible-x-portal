import {ComponentFixture, TestBed} from '@angular/core/testing';
import {CollapseModule, FooterModule, GridModule, NavbarModule, NavModule} from '@coreui/angular';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {DefaultLayoutComponent} from './default-layout.component';
import {of} from 'rxjs';

describe('DefaultLayoutComponent', () => {
  let component: DefaultLayoutComponent;
  let fixture: ComponentFixture<DefaultLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DefaultLayoutComponent],
      imports: [NavbarModule, GridModule, CollapseModule, NoopAnimationsModule, NavModule, RouterModule, FooterModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({}), // Mock route parameters
            snapshot: {paramMap: {get: () => null}} // Mock snapshot
          }
        }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DefaultLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
