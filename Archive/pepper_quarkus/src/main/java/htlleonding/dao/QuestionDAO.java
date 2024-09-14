package htlleonding.dao;

import htlleonding.entities.Answer;
import htlleonding.entities.Question;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class QuestionDAO {
    @Inject
    EntityManager em;

    @Inject
    AnswerDAO answerDAO;

    public List<Question> getAllQuestions() {
        return em.createQuery("SELECT q FROM Question q", Question.class).getResultList();
    }

    public Question getQuestionById(int id) {
        return em.find(Question.class, id);
    }

    @Transactional
    public Question addQuestion(Question question) {
        em.persist(question);
        return question;
    }


    @Transactional
    public void updateQuestion(int id, Question question) {
        Question questionToUpdate = getQuestionById(id);
        questionToUpdate.germanQuestion = question.getGermanQuestion();
        questionToUpdate.englishQuestion = question.getEnglishQuestion();
        questionToUpdate.correct = question.getCorrect();
        questionToUpdate.answers = question.getAnswers();
        em.persist(questionToUpdate);
    }

    @Transactional
    public void deleteQuestion(int id) {
        Question question = em.find(Question.class, id);
        if (question != null) {
            em.remove(question);
        } else {
            // Handle the case when the question does not exist
            // or has already been deleted
        }
    }

    /// gets random questions from database and shuffles the answers so they are not always in the same order
    public List<Question> getRandomQuestion(int length)
    {
        List<Question> questions = em.createQuery("SELECT q FROM Question q", Question.class).getResultList();
        List<Question> randomQuestions = new LinkedList<Question>();
        if(length >= questions.size())
        {
            randomQuestions = questions;
        }
        else
        {
            Random random = new Random();
            int i = 0;
            while (i <= length)
            {
                Question q = questions.get(random.nextInt(questions.size()));
                if(!randomQuestions.contains(q))
                {
                    int j = 0;
                    List<Answer> randomAnswers = new LinkedList<Answer>();
                    while (j < q.answers.size())
                    {
                        Answer a = q.answers.get(random.nextInt(q.answers.size()));
                        if(!randomAnswers.contains(a))
                        {
                            randomAnswers.add(a);
                            j++;
                        }
                    }
                    q.answers = randomAnswers;
                    randomQuestions.add(q);
                    i++;
                }
            }
        }
        return randomQuestions;
    }
    @Transactional
    public List<Question> removeAllQuestions()
    {
        List<Question> list =  em.createQuery("SELECT q FROM Question q", Question.class).getResultList();
        for(Question q : list)
        {
            deleteQuestion(q.id);
        }
        return list;
    }

    @Transactional
    public List<Question> addAllQuestions(List<Question> questions)
    {
        for(Question q : questions)
        {
            addQuestion(q);
        }
        return questions;
    }
}
