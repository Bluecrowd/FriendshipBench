import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BenchFormComponent } from './bench-form.component';

describe('BenchFormComponent', () => {
  let component: BenchFormComponent;
  let fixture: ComponentFixture<BenchFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BenchFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BenchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
