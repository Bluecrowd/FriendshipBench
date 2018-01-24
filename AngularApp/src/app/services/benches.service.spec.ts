import { TestBed, inject } from '@angular/core/testing';

import { BenchesService } from './benches.service';

describe('BenchesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BenchesService]
    });
  });

  it('should be created', inject([BenchesService], (service: BenchesService) => {
    expect(service).toBeTruthy();
  }));
});
