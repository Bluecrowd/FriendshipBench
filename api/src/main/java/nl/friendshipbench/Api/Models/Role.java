package nl.friendshipbench.api.models;

import javax.persistence.*;

/**
 * The Role model
 *
 * @author Nick Oosterhuis
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private String roleName;

    //default constructor for hibernate
    public Role() {}

    public Role(String roleName) {
       this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
