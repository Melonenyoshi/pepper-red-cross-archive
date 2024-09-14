package htl.leonding.endpoints;

import htl.leonding.entities.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/logs")
public class LogEndpoint {
    @RolesAllowed({"admin", "logger", "user","pepper"})

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Log> getAllLogs() {
        return Log.listAll();
    }

    @RolesAllowed({"admin", "logger","pepper"})
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Log getLogById(@PathParam("id") long id) {
        return Log.findById(id);
    }

    @RolesAllowed({"admin", "logger","pepper"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Log addLog(Log log) {
        Log.persist(log);
        return log;
    }

    @RolesAllowed({"admin", "logger","pepper"})
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Log updateLog(@PathParam("id") long id, Log log) {
        Log updatedLog = Log.findById(id);
        if (updatedLog != null) {
            updatedLog.message = log.message;
            updatedLog.timestamp = log.timestamp;
            updatedLog.category = log.category;
            updatedLog.origin = log.origin;
            updatedLog.persist();
        }
        return updatedLog;
    }

    @RolesAllowed({"admin", "logger","pepper"})
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public void deleteLog(@PathParam("id") long id) {
        Log.deleteById(id);
    }
}