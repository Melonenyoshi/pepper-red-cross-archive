package htl.leonding.endpoints;

import htl.leonding.entities.Answer;
import htl.leonding.tokengenerator.TokenManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/answers")
public class AnswerEndpoint {
    @Inject
    JsonWebToken jwt;

    @RolesAllowed({"admin", "user"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAnswers() {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(Answer.listAll()).build();
    }

    @RolesAllowed({"admin", "user"})
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnswerById(@PathParam("id") long id) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(Answer.findById(id)).build();
    }

    @RolesAllowed({"admin", "user"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addAnswer(Answer answer) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Answer.persist(answer);
        return Response.ok(answer).build();
    }

    @RolesAllowed({"admin", "user"})
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateAnswer(@PathParam("id") int id, Answer answer) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Answer answerInDB = Answer.findById(id);
        if (answerInDB != null) {

            answerInDB.englishAnswer = answer.englishAnswer;
            answerInDB.germanAnswer = answer.germanAnswer;
            answerInDB.correct = answer.correct;
            answerInDB.persist();
        }
        return Response.ok(answerInDB).build();
    }

    @RolesAllowed({"admin", "user"})
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteAnswer(@PathParam("id") long id) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Answer.deleteById(id);
        return Response.ok("Answer deleted").build();
    }
}

