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
package htl.leonding.tokengenerator;

import htl.leonding.entities.User;
import io.quarkus.logging.Log;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.Console;
import java.util.*;

public class TokenManager {
    /**
     * Generate JWT token
     */
    public static String generateToken(String username, String tokenId, Set<String> apiGroups) {
        return Jwt.issuer("https://www.htl-leonding.at")
                        .upn(username)
                        .groups(apiGroups)//new HashSet<>(Arrays.asList("User", "Admin"))
                        .claim("tokenId", tokenId)
                        .expiresAt(Long.MAX_VALUE)
                        .sign();
    }

    public static String generateUserTokenId() {
        return UUID.randomUUID().toString();
    }

    public static boolean validToken(JsonWebToken jwt) {
        User user = User.find("username", jwt.getName()).firstResult();
        System.out.println("Jwt: " + jwt.toString());
        if (user == null) {
            return false;
        } else if (user.tokenId.isEmpty()) {
            return false;
        }
        System.out.println("User: " + user.toString());
        String tokenId = jwt.getClaim("tokenId");
        return user.tokenId.equals(tokenId);
    }
}