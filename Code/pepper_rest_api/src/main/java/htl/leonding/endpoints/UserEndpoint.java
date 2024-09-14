package htl.leonding.endpoints;

import htl.leonding.entities.User;
import htl.leonding.tokengenerator.TokenManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndpoint{
    @Inject
    JsonWebToken jwt;

    @RolesAllowed({"admin"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(User.listAll()).build();
    }

    @RolesAllowed("admin")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(User.findById(id)).build();
    }


    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response login(User user) {
        User userDB = User.find("username", user.username).firstResult();
        if (userDB == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (!userDB.password.equals(user.password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        userDB.last_Logged_In = new Date();
        String tokenId = TokenManager.generateUserTokenId();

        Set<String> roleNames = new HashSet<>();
        for (String apiGroup : userDB.mApiGroups) {
            roleNames.add(apiGroup);
        }

        String token = TokenManager.generateToken(user.username,tokenId, roleNames);
        userDB.tokenId = tokenId;
        userDB.persist();
        return Response.ok(token).build();
    }

    @RolesAllowed({"admin", "user", "logger"})
    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response logout() {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String tokenId = jwt.getClaim("tokenId");
        User userDB = User.find("tokenId", tokenId).firstResult();
        if (userDB == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        userDB.tokenId = null;
        userDB.persist();
        return Response.ok("User logged out").build();
    }


    @RolesAllowed("admin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response createUser(User user) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        User.add(user.username, user.password, user.mApiGroups);
        User userDB = User.find("username", user.username).firstResult();
        return Response.ok(userDB).build();
    }

    @RolesAllowed("admin")
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateUser(@PathParam("id") Long id, User user) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        User userInDB = User.findById(id);
        if (userInDB != null) {
            userInDB = User.makeUser(userInDB, user.username, user.password, user.mApiGroups);
            userInDB.persist();
        }
        return Response.ok(userInDB).build();
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
        if(!TokenManager.validToken(jwt)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        User user = User.findById(id);
        User.deleteById(id);
        return Response.ok(user).build();
    }
}