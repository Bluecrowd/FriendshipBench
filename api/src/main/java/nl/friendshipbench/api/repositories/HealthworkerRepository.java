package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.HealthWorker;
import nl.friendshipbench.api.models.Role;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The HealthworkerRepositoty
 *
 * @author Nick Oosterhuis
 */

@Transactional
public interface HealthworkerRepository extends UserBaseRepo<HealthWorker> {
    Iterable<HealthWorker> findAllByRoles(List<Role> roles);
}
