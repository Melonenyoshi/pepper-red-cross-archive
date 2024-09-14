import {Component, EventEmitter, Input, Output} from '@angular/core';
import {User} from "../shared/user-model";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {
  @Input() user: User = new User();
  @Input() tableId: number = 0;

  @Input() editMode : boolean = true;
  @Input() doEdit: boolean = false;
  @Output() editDone = new EventEmitter<User>();
  @Output() deleteUser = new EventEmitter<number>();

  onEditDone(){
    this.doEdit=false;
    this.editDone.emit(this.user);
  }

  onDelete()
  {
    this.deleteUser.emit(this.user.id as number);
  }
}
