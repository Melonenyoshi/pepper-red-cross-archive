package htlleonding.repository;

import htlleonding.dao.AnswerDAO;
import htlleonding.entities.Answer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AnswerRepository {
    @Inject
    AnswerDAO answerDAO;

    public List<Answer> getAllAnswers() {
        return answerDAO.getAllAnswers();
    }

    public Answer getAnswerById(int id) {
        return answerDAO.getAnswerById(id);
    }

    public void addAnswer(Answer answer) {
        answerDAO.addAnswer(answer);
    }

    public void updateAnswer(int id, Answer answer) {
        answerDAO.updateAnswer(id, answer);
    }

    public void deleteAnswer(Answer answer) {
        answerDAO.deleteAnswer(answer);
    }
}
