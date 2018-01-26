package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.Answer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Jan-Bert on 23-1-2018.
 */
public interface AnswerRepository extends CrudRepository<Answer, Long>
{
}
