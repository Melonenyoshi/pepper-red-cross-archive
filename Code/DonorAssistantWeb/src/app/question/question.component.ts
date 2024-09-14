import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Question} from "../shared/question-model";
import {JwtServiceService} from "../shared/jwt.service.service";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent {
  @Input() tableId: number = 0;
  @Input() question: Question = new Question();
  @Input() editMode : boolean = true;
  @Input() doEdit: boolean = false;
  @Output() editDone = new EventEmitter<Question>();
  @Output() showAnswers = new EventEmitter<boolean>();
  @Input() showAnswersBoolean: boolean = false;
  @Output() deleteQuestion = new EventEmitter<number>();



  onEditDone(){
    this.doEdit=false;
    this.editDone.emit(this.question);
  }

  onShowAnswers()
  {
    this.showAnswersBoolean = !this.showAnswersBoolean;
    this.showAnswers.emit(this.showAnswersBoolean)
  }

  onDelete(){
    this.deleteQuestion.emit(this.question.id as number);
  }
}


