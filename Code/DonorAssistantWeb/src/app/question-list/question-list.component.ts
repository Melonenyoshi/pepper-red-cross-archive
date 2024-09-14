import {Component} from '@angular/core';
import {QuestionRepositoryService} from "../shared/question-repository.service";
import {Question} from "../shared/question-model";
import {Answer} from "../shared/answer-model";
import {JwtServiceService} from "../shared/jwt.service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent {
  editQuestion: boolean = false;
  newQuestion: Question = new Question();

  constructor(public questionRepo : QuestionRepositoryService, public jwtService: JwtServiceService, private router: Router) {
  }

  loginandupdate(): boolean
  {
    return this.jwtService.isBooleanTrue();
  }
  onNewQuestion()
  {
    this.newQuestion = new Question();
    this.newQuestion.answers = [new Answer(),new Answer(),new Answer(),new Answer()]
    this.questionRepo.addNewDictionaryEntry(this.newQuestion.id as number)
    this.editQuestion=true;
  }
  onNewQuestionCreated(q: Question)
  {
    this.questionRepo.add(q);
    this.editQuestion=false;
    this.questionRepo.getAllQuestions();
    window.location.reload();
  }
  onEditDoneQuestion(q: Question)
  {
    this.questionRepo.update(q);
  }

  onEditDoneAnswer(a: Answer, id: number | null)
  {
    if(id !== 0)
    {
      let question = this.questionRepo.getQuestionById(id);
      if(question)
      {
        let answerIndex = question.answers.findIndex(answer => answer.id === a.id)

        if(answerIndex !== -1)
        {
          question.answers[answerIndex] = a;
          this.onEditDoneQuestion(question);
        }
      }
    }
  }

  onShowAnswers(bool: boolean, id: number | null)
  {
    this.questionRepo.updateDictionary(bool, id as number);
  }

  onDeleteQuestion(id: number)
  {
    this.questionRepo.delete(id);
    this.questionRepo.getAllQuestions();
    window.location.reload();
  }

  navigateToImportExport()
  {
    this.router.navigate(['/questions', 'import-export'])
  }
}
