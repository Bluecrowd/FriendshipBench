package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * The userRepositoty
 *
 * @author Nick Oosterhuis
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
