package htl.leonding.endpoints;


import htl.leonding.entities.Quote;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/quotes")
public class QuoteEndpoint {

    @RolesAllowed({"admin", "user", "pepper"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Quote> getAllQuotes() {
        return Quote.listAll();
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @GET
    
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Quote getQuoteById(@PathParam("id") long id) {
        return Quote.findById(id);
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Quote addQuote(Quote quote) {
        Quote.persist(quote);
        return quote;
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Quote updateQuote(@PathParam("id") long id, Quote quote) {
        Quote quoteInDB = Quote.findById(id);
        if (quoteInDB != null) {
            quoteInDB.germanQuote = quote.germanQuote;
            quoteInDB.englishQuote = quote.englishQuote;
            quoteInDB.persist();
        }
        else {
            System.out.println("Quote not found");
            return null;
        }
        return quoteInDB;
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteQuote(@PathParam("id") long id) {
        Quote.deleteById(id);
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @POST
    @Path("/addList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Quote> addQuoteList(List<Quote> quotes) {
        quotes.forEach(quote -> Quote.persist(quote));
        return quotes;
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Quote getRandomQuote() {
        return Quote.getRandomQuote();
    }
}