import { Component } from '@angular/core';
import {User} from "../shared/user-model";
import {UserRepositoryService} from "../shared/user-repository.service";
import {JwtServiceService} from "../shared/jwt.service.service";

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent {
  editUser : boolean = false;
  newUser: User = new User();

  constructor(public userRepo : UserRepositoryService, public jwtService: JwtServiceService) {
  }

  loginandupdate() : boolean
  {
    return this.jwtService.isBooleanTrue()
  }

  onEditDoneUser(u: User)
  {
    this.userRepo.update(u);
  }

  onNewUser()
  {
    this.newUser = new User();
    this.editUser = true;
  }

  onNewUserCreated(u: User)
  {
    this.userRepo.add(u);
    this.editUser = false;
    this.userRepo.getAllUsers();
    window.location.reload();
  }

  onDeleteUser(id: number)
  {
    this.userRepo.delete(id);
    this.userRepo.getAllUsers();
    window.location.reload();
  }
}
