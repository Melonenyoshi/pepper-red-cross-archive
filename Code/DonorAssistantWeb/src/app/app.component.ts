import {Component, HostListener} from '@angular/core';
import {JwtServiceService} from "./shared/jwt.service.service";
import {DonorAssistantApiService} from "./shared/donor-assistant-api.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Donor-Assistant Configuration Website';
  loggedIn: boolean = false;

  username: string = "";
  password: string = "";

  constructor(public jwtService: JwtServiceService, private apiClient : DonorAssistantApiService) {
    this.loggedIn = jwtService.isBooleanTrue();
  }

  tryLogin() {
    var success = false;
    if(this.apiClient.login(this.username, this.password) !== null)
    {
      success = true;
    }
    this.jwtService.setTokenValues(success, this.username);
    this.loggedIn = success;
  }

  logout(){
    this.loggedIn=false;
    localStorage.clear();
    sessionStorage.clear();
    this.apiClient.logout();
    window.location.reload();
  }
}
