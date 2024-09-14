package htlleonding.endpoints;

import htlleonding.entities.Quote;
import htlleonding.repository.QuoteRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/quotes")
public class QuoteEndpoint {
    @Inject
    QuoteRepository quoteRepository;

    /// works
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Quote> getAllQuotes() {
        return quoteRepository.getAllQuotes();
    }

    /// works
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Quote getQuoteById(@PathParam("id") int id) {
        return quoteRepository.getQuoteById(id);
    }

    /// works
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Quote addQuote(Quote quote) {
        return quoteRepository.addQuote(quote);
    }

    /// works
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Quote updateQuote(@PathParam("id") int id, Quote quote) {
        return quoteRepository.updateQuote(id, quote);
    }

    /// works
    @DELETE
    @Path("/{id}")
    public void deleteQuote(@PathParam("id") int id) {
        Quote quote = quoteRepository.getQuoteById(id);
        if (quote != null) {
            quoteRepository.deleteQuote(quote);
        }
    }

    /// works
    @POST
    @Path("/addList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Quote> addQuoteList(List<Quote> quotes) {
        quotes.forEach(quote -> quoteRepository.addQuote(quote));
        return quotes;
    }

    /// works, maybe try to save the last quote you got and then get a random quote that is not the last one???
    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Quote getRandomQuote() {
        return quoteRepository.getRandomQuote();
    }
}
