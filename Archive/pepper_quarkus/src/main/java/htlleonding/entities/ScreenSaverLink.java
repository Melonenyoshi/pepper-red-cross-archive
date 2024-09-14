package htlleonding.entities;

import jakarta.persistence.*;

@Entity(name = "ScreenSaverLinkTable")
public class ScreenSaverLink {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screensaver-link_generator")
    @SequenceGenerator(name = "screensaver-link_generator", sequenceName = "screensaver-link_seq")
    public int id;

    public String url;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}

