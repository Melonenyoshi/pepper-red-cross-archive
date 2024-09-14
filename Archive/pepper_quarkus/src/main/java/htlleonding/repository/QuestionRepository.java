package htlleonding.repository;

import htlleonding.dao.QuestionDAO;
import htlleonding.entities.Question;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class QuestionRepository {
    @Inject
    QuestionDAO questionDAO;

    @Inject
    AnswerRepository answerRepository;

    public List<Question> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

    public Question getQuestionById(int id) {
        return questionDAO.getQuestionById(id);
    }

    public Question addQuestion(Question question) {

        return questionDAO.addQuestion(question);
    }

    public void updateQuestion(int id, Question question) {
        questionDAO.updateQuestion(id, question);
    }

    public void deleteQuestion(int id) {
        questionDAO.deleteQuestion(id);
    }

    public List<Question> getRandomQuestions(int amount) {
        return questionDAO.getRandomQuestion(amount);
    }
    public List<Question> removeAllQuestions(){
       return questionDAO.removeAllQuestions();
    }

    public List<Question> addAllQuestions(List<Question> questions)
    {
        return questionDAO.addAllQuestions(questions);
    }
}
