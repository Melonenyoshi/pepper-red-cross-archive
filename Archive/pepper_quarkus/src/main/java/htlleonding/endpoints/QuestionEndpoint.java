package htlleonding.endpoints;

import htlleonding.entities.Question;
import htlleonding.repository.QuestionRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/questions")
public class QuestionEndpoint {
    @Inject
    QuestionRepository questionRepository;

    /// works
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getAllQuestions() {
        return questionRepository.getAllQuestions();
    }

    /// works
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Question getQuestionById(@PathParam("id") int id) {
        return questionRepository.getQuestionById(id);
    }

    /// works
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Question addQuestion(Question question) {

        return questionRepository.addQuestion(question);
    }

    /// works
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Question updateQuestion(@PathParam("id") int id, Question question) {
        questionRepository.updateQuestion(id, question);
        return question;
    }

    /// works
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteQuestion(@PathParam("id") int id) {
        questionRepository.deleteQuestion(id);
    }

    /// works
    @GET
    @Path("/random/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getRandomQuestions(@PathParam("amount") int amount) {
        return questionRepository.getRandomQuestions(amount);
    }

    /// works
    @DELETE
    @Path("/removeall")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> deleteAllQuestions() {
        return questionRepository.removeAllQuestions();
    }

    /// works
    @POST
    @Path("/addall")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Question> addAllQuestions(List<Question> questions) {
        return questionRepository.addAllQuestions(questions);
    }
}
