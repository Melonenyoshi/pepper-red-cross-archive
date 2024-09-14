import { Component } from '@angular/core';
import {QuoteRepositoryService} from "../shared/quote-repository.service";
import {Quote} from "../shared/quote-model";

@Component({
  selector: 'app-quote-import-export',
  templateUrl: './quote-import-export.component.html',
  styleUrls: ['./quote-import-export.component.css']
})
export class QuoteImportExportComponent {

  textBoxValue: string = "";
  constructor(private quoRepo: QuoteRepositoryService) {
  }

  import()
  {
    var quotes: Quote[] = JSON.parse(this.textBoxValue);
    this.quoRepo.addAllQuotes(quotes);
  }
  export()
  {
    var quotes = this.quoRepo.quotes.map(q => q = this.quoRepo.cleanUpQuote(q));
    this.textBoxValue = JSON.stringify(quotes);
  }
}
