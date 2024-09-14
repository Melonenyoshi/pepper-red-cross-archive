package htlleonding.repository;

import htlleonding.dao.ScreenSaverLinkDAO;
import htlleonding.entities.ScreenSaverLink;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ScreenSaverLinkRepository {
    @Inject
    ScreenSaverLinkDAO screenSaverLinkDAO;

    public List<ScreenSaverLink> getAll() {
        return screenSaverLinkDAO.getAll();
    }

    public ScreenSaverLink getById(int id) {
        return screenSaverLinkDAO.getScreenSaverLinkById(id);
    }

    public ScreenSaverLink addScreenSaverLink(ScreenSaverLink screenSaverLink) {
        return screenSaverLinkDAO.addScreenSaverLink(screenSaverLink);
    }

    public ScreenSaverLink updateScreenSaverLink(int id, ScreenSaverLink screenSaverLink) {
        return screenSaverLinkDAO.updateScreenSaverLink(id, screenSaverLink);
    }

    public void deleteScreenSaverLink(ScreenSaverLink screenSaverLink) {
        screenSaverLinkDAO.deleteScreenSaverLink(screenSaverLink);
    }
}
