import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Quote} from "../shared/quote-model";

@Component({
  selector: 'app-quote',
  templateUrl: './quote.component.html',
  styleUrls: ['./quote.component.css']
})
export class QuoteComponent {
  @Input() quote: Quote = new Quote();
  @Input() tableId: number = 0;

  @Input() editMode : boolean = true;
  @Input() doEdit: boolean = false;
  @Output() editDone = new EventEmitter<Quote>();
  @Output() deleteQoute = new EventEmitter<number>();

  onEditDone(){
    this.doEdit=false;
    this.editDone.emit(this.quote);
  }

  onDelete()
  {
    this.deleteQoute.emit(this.quote.id as number);
  }
}
