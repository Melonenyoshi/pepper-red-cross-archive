package htl.leonding.endpoints;


import htl.leonding.entities.Quote;
import htl.leonding.tokengenerator.TokenManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/quotes")
public class QuoteEndpoint {
    @Inject
    JsonWebToken jwt;

    @RolesAllowed({"admin", "user"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllQuotes() {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(Quote.listAll()).build();
    }

    @RolesAllowed({"admin", "user"})
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuoteById(@PathParam("id") long id) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(Quote.findById(id)).build();
    }

    @RolesAllowed({"admin", "user"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addQuote(Quote quote) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Quote.persist(quote);
        return Response.ok(quote).build();
    }

    @RolesAllowed({"admin", "user"})
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateQuote(@PathParam("id") long id, Quote quote) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
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
        return Response.ok(quoteInDB).build();
    }

    @RolesAllowed({"admin", "user"})
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteQuote(@PathParam("id") long id) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Quote.deleteById(id);
        return Response.ok("Quote deleted").build();
    }

    @RolesAllowed({"admin", "user"})
    @POST
    @Path("/addList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addQuoteList(List<Quote> quotes) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        quotes.forEach(quote -> Quote.persist(quote));
        return Response.ok(quotes).build();
    }

    @RolesAllowed({"admin", "user"})
    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomQuote() {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(Quote.getRandomQuote()).build();
    }
}