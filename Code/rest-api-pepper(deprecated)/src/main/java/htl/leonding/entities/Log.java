package htl.leonding.entities;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.sql.Timestamp;

@Entity
public class Log extends PanacheEntity {
    public String message;
    public Timestamp timestamp;
    public String category;
    public String origin;
}