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
package htl.leonding.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jdk.jfr.Name;

@Entity
public class ApiGroups extends PanacheEntity {
    public String groupName;
    public static ApiGroups add(String role) {
        ApiGroups apiGroups1 = new ApiGroups();
        apiGroups1.groupName = role;
        apiGroups1.persist();
        return apiGroups1;
    }

    // Constructors, getters, and setters
    public ApiGroups() {
    }

    public ApiGroups(String roleName) {
        this.groupName = roleName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String roleName) {
        this.groupName = roleName;
    }
}
