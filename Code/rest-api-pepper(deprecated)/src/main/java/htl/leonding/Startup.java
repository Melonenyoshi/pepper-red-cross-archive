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
package htl.leonding;

import jakarta.inject.Singleton;
import htl.leonding.entities.User;
@Singleton
public class Startup {
    public void loadUsers() {
        if(User.find("username = \"admin\"").stream().findAny().isEmpty())
        {
            User.add("admin", "yuyhslEvcFXHB07xlUbB", "admin");
        }
        if(User.find("username = \"pepper\"").stream().findAny().isEmpty())
        {
            User.add("pepper", "UpdZQqRPYrQpOR5diFrj", "pepper");
        }
    }
}
