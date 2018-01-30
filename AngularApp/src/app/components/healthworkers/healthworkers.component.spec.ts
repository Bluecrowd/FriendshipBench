import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthworkersComponent } from './healthworkers.component';

describe('HealthworkersComponent', () => {
  let component: HealthworkersComponent;
  let fixture: ComponentFixture<HealthworkersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HealthworkersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthworkersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
