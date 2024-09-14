package htlleonding.dao;

import htlleonding.entities.ScreenSaverLink;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ScreenSaverLinkDAO {
    @Inject
    EntityManager em;

    public List<ScreenSaverLink> getAll() {
        return em.createQuery("SELECT s FROM ScreenSaverLink s", ScreenSaverLink.class).getResultList();
    }

    public ScreenSaverLink getScreenSaverLinkById(int id) {
        return em.find(ScreenSaverLink.class, id);
    }

    @Transactional
    public ScreenSaverLink addScreenSaverLink(ScreenSaverLink screenSaverLink) {
        em.persist(screenSaverLink);
        return screenSaverLink;
    }

    @Transactional
    public ScreenSaverLink updateScreenSaverLink(int id, ScreenSaverLink screenSaverLink) {
        ScreenSaverLink screenSaverLinkToUpdate = getScreenSaverLinkById(id);
        screenSaverLinkToUpdate.url = screenSaverLink.getUrl();

        return em.merge(screenSaverLinkToUpdate);
    }

    @Transactional
    public void deleteScreenSaverLink(ScreenSaverLink screenSaverLink) {
        em.remove(em.contains(screenSaverLink) ? screenSaverLink : em.merge(screenSaverLink));
    }
}
