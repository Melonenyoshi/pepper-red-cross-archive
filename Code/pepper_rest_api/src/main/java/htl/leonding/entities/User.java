package htl.leonding.entities;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;

@Entity
@UserDefinition
@Table(name = "apiUsers")
public class User extends PanacheEntity {
    @Username
    public String username;
    @Password
    public String password;
    @Roles
    public List<String> mApiGroups;
    public String tokenId;
    public Date last_Logged_In;

    /**
     * Adds a new user to the database
     * @param username the username
     * @param password the unencrypted password (it will be encrypted with bcrypt)
     * @param apiGroups the comma-separated roles
     */
    public static void add(String username, String password, List<String> apiGroups) {
        User user = new User();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.mApiGroups = apiGroups;
        user.persist();
    }

    public static User makeUser(User user, String username, String password, List<String> apiGroups) {
        user.username = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.mApiGroups = apiGroups;
        return user;
    }
}
