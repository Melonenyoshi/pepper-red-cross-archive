/*----------------------------------------------------------
 *                HTBLA-Leonding / Class: < 3CHIF >
 * ---------------------------------------------------------
 * Exercise Number: 0
 * Title:
 * Author: Amgad Hammash
 * ----------------------------------------------------------
 * Description:
 * ----------------------------------------------------------
 */
package htl.leonding.endpoints;

import htl.leonding.entities.Answer;
import htl.leonding.entities.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Date;

@Path("/login")
public class LoginEndpoint {
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
}
