import { Injectable } from '@angular/core';
import {Question} from "./question-model";
import {DonorAssistantApiService} from "./donor-assistant-api.service";
import {Answer} from "./answer-model";

@Injectable({
  providedIn: 'root'
})
export class QuestionRepositoryService {

  public questions: Question[] = []
  public showAnswers: { [key: number]: boolean} = [];
  constructor(private apiClient : DonorAssistantApiService) {
    this.getAllQuestions();
  }
  getAllQuestions()
  {
    this.apiClient.getAllQuestions().subscribe({
      next: value => {this.questions = value;
        for(let q of this.questions)
        {
          this.showAnswers[q.id as number] = false;
        }
      },
      error: (err) =>{console.error(err)}
    })
  }
  addNewDictionaryEntry(id: number)
  {
    this.showAnswers[id]=true;
  }
  add(question: Question)
  {
    this.apiClient.addQuestion(this.cleanUpQuestion(question)).subscribe();
  }
  update(question: Question)
  {
    this.apiClient.updateQuestion(this.cleanUpQuestion(question), question.id as number).subscribe();
  }

  delete(id: number)
  {
    this.apiClient.deleteQuestion(id).subscribe();
  }

  updateDictionary(bool: boolean, id: number)
  {
    this.showAnswers[id] = bool;
  }

  getDictionaryValue(id: number | null)
  {
    return this.showAnswers[(id as number)];
  }

  getQuestionById(id: number | null)
  {
    for(let q of this.questions)
    {
      if(q.id === id as number)
      {
        return q;
      }
    }
    return null;
  }

  addAllQuestions(questions: Question[])
  {
    return this.apiClient.addAllQuestions(questions).subscribe();
  }

  cleanUpQuestion(question: Question)
  {
    let q = new Question();
    q.id = null;
    q.germanQuestion = question.germanQuestion;
    q.englishQuestion = question.englishQuestion
    q.answers = [new Answer(), new Answer(), new Answer(), new Answer()]
    for(let i = 0; i < 4; i++)
    {
      q.answers[i] = {id: null, germanAnswer: question.answers[i].germanAnswer, englishAnswer: question.answers[i].englishAnswer, correct:question.answers[i].correct }
    }
    return q;
  }
}
