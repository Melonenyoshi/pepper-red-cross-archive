package htlleonding.dao;

import htlleonding.entities.Quote;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class QuoteDAO {
    @Inject
    EntityManager em;

    public List<Quote> getAllQuotes() {
        return em.createQuery("SELECT q FROM Quote q", Quote.class).getResultList();
    }

    public Quote getQuoteById(int id) {
        return em.find(Quote.class, id);
    }

    @Transactional
    public Quote addQuote(Quote quote) {
        em.persist(quote);
        return quote;
    }

    @Transactional
    public Quote updateQuote(int id, Quote quote)
    {
        Quote quoteToUpdate = getQuoteById(id);
        quoteToUpdate.englishQuote = quote.englishQuote;
        quoteToUpdate.germanQuote = quote.germanQuote;

        return em.merge(quoteToUpdate);
    }

    @Transactional
    public void deleteQuote(Quote quote) {
        em.remove(em.contains(quote) ? quote : em.merge(quote));
    }

    public Quote getRandomQuote()
    {

        List<Quote> quotes = em.createQuery("SELECT q FROM Quote q", Quote.class).getResultList();
        Quote quote = null;
        for (Quote quoteLoop: quotes)
        {
            if(quoteLoop.lastDate == Date.valueOf(LocalDate.now()))
            {
                quote = quoteLoop;
            }
        }
        if(quote == null)
        {
           quote = getQuoteRandomRecursivePart(quotes);

        }
        return quote;

    }


    private Quote getQuoteRandomRecursivePart(List<Quote> quotes)
    {
        Random      random = new Random();
        Quote quote = quotes.get(random.nextInt(quotes.size()-1));
        if(quote.lastDate == Date.valueOf(LocalDate.now().minusDays(1)))
        {
            quote = getQuoteRandomRecursivePart(quotes);
        }
        else
        {
            quote.lastDate = Date.valueOf(LocalDate.now());
            updateQuote(quote.id, quote);
        }
        return quote;
    }
}
