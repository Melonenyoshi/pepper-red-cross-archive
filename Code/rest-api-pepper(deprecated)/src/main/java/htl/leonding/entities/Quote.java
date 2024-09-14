package htl.leonding.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.util.List;
import java.util.Random;


@Entity
public class Quote extends PanacheEntity {

    public String englishQuote;
    public String germanQuote;

    public static Quote getRandomQuote() {
        // Find all quotes
        List<Quote> allQuotes = listAll();

        if (!allQuotes.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(allQuotes.size());

            return allQuotes.get(randomIndex);
        } else {
            return null;
        }
    }
}
