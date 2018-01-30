import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthworkerComponent } from './healthworker.component';

describe('HealthworkerComponent', () => {
  let component: HealthworkerComponent;
  let fixture: ComponentFixture<HealthworkerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HealthworkerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthworkerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
