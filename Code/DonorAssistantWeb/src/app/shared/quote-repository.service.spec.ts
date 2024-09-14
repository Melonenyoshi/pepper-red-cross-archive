import { TestBed } from '@angular/core/testing';

import { QuoteRepositoryService } from './quote-repository.service';

describe('QuoteRepositoryService', () => {
  let service: QuoteRepositoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QuoteRepositoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
