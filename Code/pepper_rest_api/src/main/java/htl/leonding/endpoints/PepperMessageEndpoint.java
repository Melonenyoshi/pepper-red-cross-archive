package htl.leonding.endpoints;

import htl.leonding.entities.PepperMessage;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/pepperMessage")
public class PepperMessageEndpoint {
    @Inject
    PepperMessage pepperMessage;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage() {
        return pepperMessage.PepperMessage;
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public String setMessage(String message) {
        pepperMessage.PepperMessage = message;
        return "Message set: "+ pepperMessage.PepperMessage;
    }
}
