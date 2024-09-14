import { Component } from '@angular/core';
import {QuoteRepositoryService} from "../shared/quote-repository.service";
import {Quote} from "../shared/quote-model";
import {JwtServiceService} from "../shared/jwt.service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-quote-list',
  templateUrl: './quote-list.component.html',
  styleUrls: ['./quote-list.component.css']
})
export class QuoteListComponent {
  editQuote: boolean =false;
  newQuote: Quote = new Quote();
  constructor(public quoteRepo : QuoteRepositoryService, public jwtService: JwtServiceService, private router: Router) {
  }

  loginandupdate(): boolean
  {
    return this.jwtService.isBooleanTrue();
  }
  onEditDoneQuote(q: Quote)
  {
    this.quoteRepo.update(q);
  }

  onNewQuote()
  {
    this.newQuote = new Quote();
    this.editQuote = true;
  }

  onNewQuoteCreated(q: Quote)
  {
    this.quoteRepo.add(q);
    this.editQuote=false;
    this.quoteRepo.getAllQuotes();
    window.location.reload();
  }

  onDeleteQuote(id: number)
  {
    this.quoteRepo.delete(id);
    this.quoteRepo.getAllQuotes();
    window.location.reload();
  }

  navigateToImportExport()
  {
    this.router.navigate(['/quotes', 'import-export'])
  }
}
