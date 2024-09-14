import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Answer} from "../shared/answer-model";

@Component({
  selector: 'app-answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.css']
})
export class AnswerComponent {
  @Input() answer : Answer = new Answer();
  @Input() tableId: number = 0;

  @Input() editMode : boolean = true;
  @Input() doEdit: boolean = false;
  @Output() editDone = new EventEmitter<Answer>();

  onEditDone(){
    this.doEdit=false;
    this.editDone.emit(this.answer);
  }
}
