package htlleonding.endpoints;

import htlleonding.entities.Answer;
import htlleonding.repository.AnswerRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/answers")
public class AnswerEndpoint {
    @Inject
    AnswerRepository answerRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Answer> getAllAnswers() {
        return answerRepository.getAllAnswers();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Answer getAnswerById(@PathParam("id") int id) {
        return answerRepository.getAnswerById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Answer addAnswer(Answer answer) {
        answerRepository.addAnswer(answer);
        return answer;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Answer updateAnswer(@PathParam("id") int id, Answer answer) {
        answerRepository.updateAnswer(id, answer);
        return answer;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteAnswer(@PathParam("id") int id) {
        Answer answer = answerRepository.getAnswerById(id);
        if (answer != null) {
            answerRepository.deleteAnswer(answer);
        }
    }
}
