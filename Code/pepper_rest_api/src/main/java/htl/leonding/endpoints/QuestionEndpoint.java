package htl.leonding.endpoints;


import htl.leonding.entities.Question;
import htl.leonding.tokengenerator.TokenManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/questions")
public class QuestionEndpoint {
    @Inject
    JsonWebToken jwt;

    @RolesAllowed({"admin", "user"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllQuestions() {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(Question.listAll()).build();
    }

    @RolesAllowed({"admin", "user"})
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionById(@PathParam("id") long id) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(Question.findById(id)).build();
    }

    @RolesAllowed({"admin", "user"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addQuestion(Question question) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Question.persist(question);
        return Response.ok(question).build();
    }

    @RolesAllowed({"admin", "user"})
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateQuestion(@PathParam("id") long id, Question question) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Question questioninDB = Question.findById(id);
        if(questioninDB != null)
        {
            questioninDB.germanQuestion = question.germanQuestion;
            questioninDB.englishQuestion = question.englishQuestion;
            questioninDB.answers = question.answers;
            questioninDB.persist();
        }
        return Response.ok(question).build();
    }

    @RolesAllowed({"admin", "user"})
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteQuestion(@PathParam("id") long id) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Question.deleteById(id);
        return Response.ok("Question deleted").build();
    }

    @RolesAllowed({"admin", "user"})
    @GET
    @Path("/random/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomQuestions(@PathParam("amount") int amount) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(Question.getRandomQuestion(amount)).build();
    }

    @RolesAllowed({"admin", "user"})
    @DELETE
    @Path("/removeAll")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response deleteAllQuestions() {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            Question.deleteAll();
            return Response.ok("success").build();
        } catch (Exception e) {
            return Response.notModified(e.getMessage()).build();
        }
    }

    @RolesAllowed({"admin", "user"})
    @POST
    @Path("/addList")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addAllQuestions(List<Question> questions) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            questions.forEach(question -> Question.persist(question));
            return Response.ok("success").build();
        } catch (Exception e) {
            return Response.notModified(e.getMessage()).build();
        }

    }
}

