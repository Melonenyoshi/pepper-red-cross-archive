package htl.leonding.endpoints;

import htl.leonding.entities.Answer;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/answers")
public class AnswerEndpoint {

    @RolesAllowed({"admin", "user", "pepper"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Answer> getAllAnswers() {
        return Answer.listAll();
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Answer getAnswerById(@PathParam("id") long id) {
        return Answer.findById(id);
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Answer addAnswer(Answer answer) {
        Answer.persist(answer);
        return answer;
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Answer updateAnswer(@PathParam("id") int id, Answer answer) {
        Answer answerInDB = Answer.findById(id);
        if (answerInDB != null) {

            answerInDB.englishAnswer = answer.englishAnswer;
            answerInDB.germanAnswer = answer.germanAnswer;
            answerInDB.correct = answer.correct;
            answerInDB.persist();
        }
        return answer;
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public void deleteAnswer(@PathParam("id") long id) {
        Answer.deleteById(id);
    }
}

