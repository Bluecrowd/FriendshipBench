package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Jan-Bert on 22-1-2018.
 */
public interface QuestionRepository extends CrudRepository<Question, Long>
{
	public List<Question> findByActiveTrue();
}
