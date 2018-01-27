package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.Role;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role getByRoleName(String roleName);
}
