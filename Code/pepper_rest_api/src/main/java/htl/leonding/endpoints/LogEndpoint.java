package htl.leonding.endpoints;

import htl.leonding.entities.Log;
import htl.leonding.tokengenerator.TokenManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/logs")
public class LogEndpoint {
    @Inject
    JsonWebToken jwt;

    @RolesAllowed({"admin", "logger", "user"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLogs() {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(Log.listAll()).build();
    }

    @RolesAllowed({"admin", "logger"})
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogById(@PathParam("id") long id) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(Log.findById(id)).build();
    }

    @RolesAllowed({"admin", "logger"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addLog(Log log) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Log.persist(log);
        return Response.ok(log).build();
    }

    @RolesAllowed({"admin", "logger"})
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateLog(@PathParam("id") long id, Log log) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Log updatedLog = Log.findById(id);
        if (updatedLog != null) {
            updatedLog.message = log.message;
            updatedLog.timestamp = log.timestamp;
            updatedLog.category = log.category;
            updatedLog.origin = log.origin;
            updatedLog.persist();
        }
        return Response.ok(updatedLog).build();
    }

    @RolesAllowed({"admin", "logger"})
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteLog(@PathParam("id") long id) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Log.deleteById(id);
        return Response.ok("Log deleted").build();
    }
}