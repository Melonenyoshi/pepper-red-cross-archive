import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { AppComponent } from './app.component';
import { QuoteComponent } from './quote/quote.component';
import { MainComponent } from './main/main.component';
import {RouterModule, Routes, RouterOutlet} from "@angular/router";
import { QuestionComponent } from './question/question.component';
import { QuoteListComponent } from './quote-list/quote-list.component';
import { QuestionListComponent } from './question-list/question-list.component';
import { AnswerComponent } from './answer/answer.component';
import {FormsModule} from "@angular/forms";
import {JwtModule} from "@auth0/angular-jwt";
import { UsersComponent } from './users/users.component';
import {HttpInterceptorService} from "./shared/http-interceptor.service";
import { UsersListComponent } from './users-list/users-list.component';
import { QuoteImportExportComponent } from './quote-import-export/quote-import-export.component';
import { QuestionImportExportComponent } from './question-import-export/question-import-export.component';

const appRoutes:Routes = [
  {path: '', component: MainComponent},
  {path: 'main', component: MainComponent},
  {path: 'quotes', children: [
      { path: '', component: QuoteListComponent},
      { path: 'import-export', component: QuoteImportExportComponent}
    ] },
  {path: 'questions', children: [
      { path: '', component: QuestionListComponent},
      { path: 'import-export', component: QuestionImportExportComponent}
    ] },
  {path: 'users', component: UsersListComponent}

]

@NgModule({
  declarations: [
    AppComponent,
    QuoteComponent,
    MainComponent,
    QuestionComponent,
    QuoteListComponent,
    QuestionListComponent,
    AnswerComponent,
    UsersComponent,
    UsersListComponent,
    QuoteImportExportComponent,
    QuestionImportExportComponent,
  ],
    imports: [
      JwtModule.forRoot({
        config:{
          tokenGetter: () => localStorage.getItem('jwtToken'),
          allowedDomains: ['localhost:4200'],
        }
      }),
        HttpClientModule,
        BrowserModule,
        RouterModule.forRoot(appRoutes),
        FormsModule
    ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
