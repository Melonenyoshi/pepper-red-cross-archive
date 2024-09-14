package htl.leonding.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Entity
public class Question extends PanacheEntity {

    public String germanQuestion;

    public String englishQuestion;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Answer> answers;

    public static List<Question> getRandomQuestion(int length) {
        List<Question> questions = Question.listAll();
        List<Question> randomQuestions = new LinkedList<Question>();
        if (length >= questions.size()) {
            randomQuestions = questions;
        } else {
            Random random = new Random();
            int i = 0;
            while (i < length) {
                Question q = questions.get(random.nextInt(questions.size()));
                if (!randomQuestions.contains(q)) {
                    int j = 0;
                    List<Answer> randomAnswers = new LinkedList<Answer>();
                    while (j < q.answers.size()) {
                        Answer a = q.answers.get(random.nextInt(q.answers.size()));
                        if (!randomAnswers.contains(a)) {
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


}
