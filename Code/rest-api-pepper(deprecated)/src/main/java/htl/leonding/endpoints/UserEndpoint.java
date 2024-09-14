package htl.leonding.endpoints;

import htl.leonding.entities.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Date;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndpoint{

    @RolesAllowed({"admin","pepper"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return User.listAll();
    }

    @RolesAllowed({"admin","pepper"})
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("id") Long id) {
        return User.findById(id);
    }

    @RolesAllowed({"admin", "user", "logger", "pepper"})
    @GET
    @Path("/login/{user}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Transactional
    public String login(@PathParam("user") String user) {
        User userDB = User.find("username = ?1", user).firstResult();
        userDB.last_Logged_In = new Date();
        userDB.persist();
        return "Login successful";
    }


    @RolesAllowed({"admin","pepper"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public void createUser(User user) {
        User.add(user.username, user.password, user.role);
    }

    @RolesAllowed({"admin","pepper"})
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public User updateUser(@PathParam("id") Long id, User user) {
        User userInDB = User.findById(id);
        if (userInDB != null) {
            userInDB = User.makeUser(userInDB, user.username, user.password, user.role);
            userInDB.persist();
        }
        return user;
    }

    @RolesAllowed({"admin","pepper"})
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public User deleteUser(@PathParam("id") Long id) {
        User user = User.findById(id);
        User.deleteById(id);
        return user;
    }
}