import { Injectable } from '@angular/core';
import {User} from "./user-model";
import {DonorAssistantApiService} from "./donor-assistant-api.service";

@Injectable({
  providedIn: 'root'
})
export class UserRepositoryService {

  public users: User[] = [];

  constructor(private apiClient : DonorAssistantApiService) {
    this.getAllUsers()
  }

  getAllUsers()
  {
    this.apiClient.getAllUsers().subscribe({
      next: value => {this.users = value;},
      error: (err) => {console.error(err)}
    })
  }

  add(user: User)
  {
    this.apiClient.addUser(this.cleanUpUser(user)).subscribe();
  }

  update(user: User)
  {
    this.apiClient.updateUser(this.cleanUpUser(user), user.id as number).subscribe();
  }

  delete(id: number)
  {
    this.apiClient.deleteUser(id).subscribe();
  }

  cleanUpUser(user: User)
  {
    let u = new User()
    u.id=null
    u.username = user.username
    u.password = user.password
    u.role = user.role
    return u;
  }
}
