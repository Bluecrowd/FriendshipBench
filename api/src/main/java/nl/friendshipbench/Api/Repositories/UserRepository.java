package nl.friendshipbench.Api.Repositories;

import nl.friendshipbench.Api.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The userRepositoty
 *
 * @author Nick Oosterhuis
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
