package htlleonding.endpoints;

import htlleonding.entities.ScreenSaverLink;
import htlleonding.repository.ScreenSaverLinkRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/screen-saver-links")
public class ScreenSaverLinkEndpoint {
    @Inject
    ScreenSaverLinkRepository screenSaverLinkRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ScreenSaverLink> getAllScreenSaverLinks() {
        return screenSaverLinkRepository.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ScreenSaverLink getScreenSaverLinkById(@PathParam("id") int id) {
        return screenSaverLinkRepository.getById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ScreenSaverLink addScreenSaverLink(ScreenSaverLink screenSaverLink) {
        return screenSaverLinkRepository.addScreenSaverLink(screenSaverLink);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ScreenSaverLink updateScreenSaverLink(@PathParam("id") int id,  ScreenSaverLink screenSaverLink) {
        return screenSaverLinkRepository.updateScreenSaverLink(id, screenSaverLink);
    }

    @DELETE
    @Path("/{id}")
    public void deleteScreenSaverLink(@PathParam("id") int id) {
        ScreenSaverLink screenSaverLink = screenSaverLinkRepository.getById(id);
        if (screenSaverLink != null) {
            screenSaverLinkRepository.deleteScreenSaverLink(screenSaverLink);
        }
    }
}
