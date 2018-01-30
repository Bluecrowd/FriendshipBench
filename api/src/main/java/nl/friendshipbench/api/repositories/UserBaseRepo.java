package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * User base repo
 * @param <T>
 *
 * @author Nick Oosterhuis
 */
@NoRepositoryBean
public interface UserBaseRepo<T extends User> extends CrudRepository<T, Long> {
    T findByUsername(String username);
}
