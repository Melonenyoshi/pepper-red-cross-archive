import { Injectable } from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class JwtServiceService {
  constructor(private jwtHelper: JwtHelperService) {}

  setTokenValues(loggedIn: boolean, nametoshow: string) {
    const token = { isBooleanTrue: loggedIn , nametoshow: nametoshow};
    localStorage.setItem('jwtToken', JSON.stringify(token));
  }

  isBooleanTrue(): boolean {
    const token = localStorage.getItem('jwtToken');
    return token ? JSON.parse(token).isBooleanTrue : false;
  }

  getUsername(): string{
    const token = localStorage.getItem('jwtToken');
    return token ? JSON.parse(token).nametoshow : "";
  }
}
