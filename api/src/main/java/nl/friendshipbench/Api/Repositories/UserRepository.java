package nl.friendshipbench.Api.Repositories;

import nl.friendshipbench.Api.Models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * The userRepositoty
 *
 * @author Nick Oosterhuis
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
