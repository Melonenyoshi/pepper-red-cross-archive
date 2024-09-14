package htl.leonding.endpoints;


import htl.leonding.entities.Question;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/questions")
public class QuestionEndpoint {

    @RolesAllowed({"admin", "user", "pepper"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getAllQuestions() {
        return Question.listAll();
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Question getQuestionById(@PathParam("id") long id) {
        return Question.findById(id);
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Question addQuestion(Question question) {

        Question.persist(question);
        return question;
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Question updateQuestion(@PathParam("id") long id, Question question) {
        Question questioninDB = Question.findById(id);
        if(questioninDB != null)
        {
            questioninDB.germanQuestion = question.germanQuestion;
            questioninDB.englishQuestion = question.englishQuestion;
            questioninDB.answers = question.answers;
            questioninDB.persist();
        }
        return question;
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public void deleteQuestion(@PathParam("id") long id) {
        Question.deleteById(id);
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @GET
    @Path("/random/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getRandomQuestions(@PathParam("amount") int amount) {
        return Question.getRandomQuestion(amount);
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @DELETE
    @Path("/removeAll")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String deleteAllQuestions() {
        try {
            Question.deleteAll();
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RolesAllowed({"admin", "user", "pepper"})
    @POST
    @Path("/addList")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String addAllQuestions(List<Question> questions) {
        try {
            questions.forEach(question -> Question.persist(question));
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }

    }
}

