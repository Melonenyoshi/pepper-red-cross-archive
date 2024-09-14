package htlleonding.dao;

import htlleonding.entities.Answer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AnswerDAO {
    @Inject
    EntityManager em;

    public List<Answer> getAllAnswers() {
        return em.createQuery("SELECT a FROM Answer a", Answer.class).getResultList();
    }

    public Answer getAnswerById(int id) {
        return em.find(Answer.class, id);
    }

    @Transactional
    public void addAnswer(Answer answer) {
        em.persist(answer);
    }

    @Transactional
    public void updateAnswer(int id, Answer answer) {
        Answer answerToUpdate = getAnswerById(id);
        answerToUpdate.germanAnswer = answer.getGermanAnswer();
        answerToUpdate.englishAnswer  = answer.getEnglishAnswer();
        answerToUpdate.correct = answer.isCorrect();
        em.persist(answerToUpdate);

    }
    @Transactional
    public void deleteAnswer(Answer answer) {
        em.remove(answer);
    }
}

