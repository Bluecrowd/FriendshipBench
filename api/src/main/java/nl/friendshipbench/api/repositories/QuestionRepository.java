package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.Models.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Jan-Bert on 22-1-2018.
 */
public interface QuestionRepository extends CrudRepository<Question, Long>
{
	public List<Question> findByActiveTrue();
}
