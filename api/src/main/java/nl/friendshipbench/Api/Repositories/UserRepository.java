package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * The userRepositoty
 *
 * @author Nick Oosterhuis
 */

@Transactional
public interface UserRepository extends UserBaseRepo<User> {
}
