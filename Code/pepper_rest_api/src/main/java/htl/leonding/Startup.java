package htl.leonding;

import htl.leonding.entities.User;
import htl.leonding.entities.ApiGroups;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.util.List;


@Singleton
public class Startup {
    @Transactional
    void onStart(@Observes StartupEvent ev) {
        // Create roles if they don't exist
        ApiGroups adminRole = ApiGroups.find("groupName", "admin").firstResult();
        if (adminRole == null) {
            adminRole = new ApiGroups();
            adminRole.setGroupName("admin");
            adminRole.persist();
        }

        ApiGroups userApiGroups = ApiGroups.find("groupName", "user").firstResult();
        if (userApiGroups == null) {
            userApiGroups = new ApiGroups();
            userApiGroups.setGroupName("user");
            userApiGroups.persist();
        }

        ApiGroups loggerRole = ApiGroups.find("groupName", "logger").firstResult();
        if (loggerRole == null) {
            loggerRole = new ApiGroups();
            loggerRole.setGroupName("logger");
            loggerRole.persist();
        }

        // Create users with appropriate roles if they don't exist
        createUserIfNotExists("admin", "yuyhslEvcFXHB07xlUbB", List.of(adminRole.getGroupName(), userApiGroups.getGroupName(), loggerRole.getGroupName()));
        createUserIfNotExists("logger", "UpdZQqRPYrQpOR5diFrj", List.of(loggerRole.getGroupName()));
        createUserIfNotExists("pepper", "jkasfi8219FNI21wesfg", List.of(userApiGroups.getGroupName()));
    }

    @Transactional
    void createUserIfNotExists(String username, String password, List<String> apiGroups) {
        User existingUser = User.find("username", username).firstResult();
        if (existingUser == null) {
            User newUser = new User();
            newUser.username = username;
            newUser.password = password;
            newUser.mApiGroups = apiGroups;
            newUser.persist();
        }
    }
}
