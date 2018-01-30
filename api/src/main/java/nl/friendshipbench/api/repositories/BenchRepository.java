package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.Bench;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Jan-Bert on 22-1-2018.
 */
public interface BenchRepository extends CrudRepository<Bench, Long>
{

}
