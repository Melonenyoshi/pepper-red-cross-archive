package htlleonding.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_generator")
    @SequenceGenerator(name = "quote_generator", sequenceName = "quote_seq")
    public int id;
    public String englishQuote;
    public String germanQuote;

    public Date lastDate;

    public int getId() {
        return id;
    }

    public String getEnglishQuote() {
        return englishQuote;
    }

    public String getGermanQuote() {
        return germanQuote;
    }
}
