import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionImportExportComponent } from './question-import-export.component';

describe('QuestionImportExportComponent', () => {
  let component: QuestionImportExportComponent;
  let fixture: ComponentFixture<QuestionImportExportComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuestionImportExportComponent]
    });
    fixture = TestBed.createComponent(QuestionImportExportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
