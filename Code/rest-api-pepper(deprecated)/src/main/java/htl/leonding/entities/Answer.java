package htl.leonding.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Answer extends PanacheEntity {
    public String germanAnswer;
    public String englishAnswer;
    public boolean correct;

}
