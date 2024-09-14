import { Injectable } from '@angular/core';
import {Quote} from "./quote-model";
import {DonorAssistantApiService} from "./donor-assistant-api.service";

@Injectable({
  providedIn: 'root'
})
export class QuoteRepositoryService {
  public quotes: Quote[] = []
  constructor(private apiClient : DonorAssistantApiService) {
    this.getAllQuotes()
  }

  getAllQuotes()
  {
    this.apiClient.getAllQuotes().subscribe({
      next: value => {this.quotes = value;},
      error: (err) =>{console.error(err)}
    })
  }

  add(quote: Quote)
  {
    this.apiClient.addQuote(this.cleanUpQuote(quote)).subscribe();
  }
  update(quote: Quote)
  {
    this.apiClient.updateQuote(this.cleanUpQuote(quote), quote.id as number).subscribe();
  }

  delete(id: number)
  {
    this.apiClient.deleteQuote(id).subscribe();
  }

  addAllQuotes(quotes: Quote[])
  {
    this.apiClient.addAllQuotes(quotes).subscribe();
  }

  cleanUpQuote(quote: Quote)
  {
    let q = new Quote();
    q.id=null;
    q.germanQuote = quote.germanQuote;
    q.englishQuote = quote.englishQuote
    return q;
  }
}
