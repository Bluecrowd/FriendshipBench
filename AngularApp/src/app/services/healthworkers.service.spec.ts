import { TestBed, inject } from '@angular/core/testing';

import { HealthworkersService } from './healthworkers.service';

describe('HealthworkersService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HealthworkersService]
    });
  });

  it('should be created', inject([HealthworkersService], (service: HealthworkersService) => {
    expect(service).toBeTruthy();
  }));
});
