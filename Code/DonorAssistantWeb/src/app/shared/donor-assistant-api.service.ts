import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Question} from "./question-model";
import {Quote} from "./quote-model";
import {User} from "./user-model";

@Injectable({
  providedIn: 'root'
})
export class DonorAssistantApiService {
  headers = new HttpHeaders();
  questionsAll = '/l.federsel/questions/'; // can also be used to get, put, post(?) a question
  quotesAll = '/l.federsel/quotes/';
  userLogin = '/l.federsel/users/login/'
  usersAll = '/l.federsel/users/'

  username = "admin"
  password = "yuyhslEvcFXHB07xlUbB"

  constructor(private http: HttpClient) { }

  public getAllQuestions()
  {
    return this.http.get<Question[]>(this.questionsAll);
  }

  public getAllQuotes()
  {
    return this.http.get<Quote[]>(this.quotesAll);
  }

  public getAllUsers()
  {
    return this.http.get<User[]>(this.usersAll);
  }

  public updateQuestion(q: Question, id: number)
  {
    return this.http.put<number>(this.questionsAll + id, q);
  }

  public updateQuote(q: Quote, id: number)
  {
    return this.http.put<number>(this.quotesAll + id, q);
  }

  public updateUser(u: User, id: number)
  {
    return this.http.put<number>(this.usersAll + id, u);
  }

  public addQuestion(q: Question)
  {
    return this.http.post<void>(this.questionsAll, q);
  }

  public addQuote(q: Quote)
  {
    return this.http.post<void>(this.quotesAll, q);
  }

  public addUser(u: User)
  {
    return this.http.post<void>(this.usersAll, u);
  }

  public addAllQuestions(qs: Question[])
  {
    return this.http.post<void>(this.questionsAll + "addList", qs);
  }

  public addAllQuotes(qs: Quote[])
  {
    return this.http.post<void>(this.quotesAll + "addList", qs);
  }

  public deleteQuestion(id: number)
  {
    return this.http.delete<void>(this.questionsAll + id);
  }

  public deleteQuote(id: number)
  {
    return this.http.delete<void>(this.quotesAll + id);
  }

  public deleteUser(id: number)
  {
    return this.http.delete<void>(this.usersAll + id);
  }

  public login(username: string, password: string)
  {
    try {
      this.username = username;
      this.password = password;
      return this.http.get<string>(this.userLogin + username);
    }
    catch (e) {
      return null
    }
  }

  public logout()
  {
    this.headers = new HttpHeaders();
  }

  public getCredentials()
  {
    return btoa(`${this.username}:${this.password}`)
  }
}
