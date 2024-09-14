import { TestBed } from '@angular/core/testing';

import { DonorAssistantApiService } from './donor-assistant-api.service';

describe('DonorAssistantApiService', () => {
  let service: DonorAssistantApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DonorAssistantApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
