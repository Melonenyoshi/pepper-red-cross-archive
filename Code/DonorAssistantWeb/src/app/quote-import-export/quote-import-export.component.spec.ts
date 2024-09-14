import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteImportExportComponent } from './quote-import-export.component';

describe('QuoteImportExportComponent', () => {
  let component: QuoteImportExportComponent;
  let fixture: ComponentFixture<QuoteImportExportComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuoteImportExportComponent]
    });
    fixture = TestBed.createComponent(QuoteImportExportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
