package htlleonding.entities;

import jakarta.persistence.*;

@SuppressWarnings("unused")
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_generator")
    @SequenceGenerator(name = "answer_generator", sequenceName = "answer_seq")
    public int id;
    public String germanAnswer;
    public String englishAnswer;
    public boolean correct;

    public int getId() {
        return id;
    }

    public String getGermanAnswer() {
        return germanAnswer;
    }

    public String getEnglishAnswer() {
        return englishAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }
}
