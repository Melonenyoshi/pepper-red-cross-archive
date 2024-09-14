package htlleonding.repository;

import htlleonding.dao.QuoteDAO;
import htlleonding.entities.Quote;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class QuoteRepository {
    @Inject
    QuoteDAO quoteDAO;

    public List<Quote> getAllQuotes() {
        return quoteDAO.getAllQuotes();
    }

    public Quote getQuoteById(int id) {
        return quoteDAO.getQuoteById(id);
    }

    public Quote addQuote(Quote quote) {
        return quoteDAO.addQuote(quote);
    }

    public Quote updateQuote(int id, Quote quote) {
        return quoteDAO.updateQuote(id, quote);
    }

    public void deleteQuote(Quote quote) {
        quoteDAO.deleteQuote(quote);
    }

    public Quote getRandomQuote() {
        return quoteDAO.getRandomQuote();
    }
}
