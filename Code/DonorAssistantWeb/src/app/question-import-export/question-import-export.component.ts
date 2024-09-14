import { Component } from '@angular/core';
import {QuoteRepositoryService} from "../shared/quote-repository.service";
import {Quote} from "../shared/quote-model";
import {QuestionRepositoryService} from "../shared/question-repository.service";
import {Question} from "../shared/question-model";

@Component({
  selector: 'app-question-import-export',
  templateUrl: './question-import-export.component.html',
  styleUrls: ['./question-import-export.component.css']
})
export class QuestionImportExportComponent {

  textBoxValue: string = "";

  constructor(private queRepo: QuestionRepositoryService) {
  }

  import()
  {
    var questions: Question[] = JSON.parse(this.textBoxValue);
    this.queRepo.addAllQuestions(questions);
  }
  export()
  {
    var questions = this.queRepo.questions.map(q => q = this.queRepo.cleanUpQuestion(q));
    this.textBoxValue = JSON.stringify(questions);
  }
}
