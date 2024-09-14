package htlleonding.entities;

import jakarta.persistence.*;
import java.util.List;

@SuppressWarnings("unused")
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name = "question_generator", sequenceName = "question_seq")
    public int id;
    public String germanQuestion;
    public String englishQuestion;
    public int correct;

    // removed the orphanRemoval = true, because it caused problems
    @OneToMany(cascade = CascadeType.ALL)
    public List<Answer> answers;

    public int getId() {
        return id;
    }

    public String getGermanQuestion() {
        return germanQuestion;
    }

    public String getEnglishQuestion() {
        return englishQuestion;
    }

    public int getCorrect() {
        return correct;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
