import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BenchesComponent } from './benches.component';

describe('BenchesComponent', () => {
  let component: BenchesComponent;
  let fixture: ComponentFixture<BenchesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BenchesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BenchesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
