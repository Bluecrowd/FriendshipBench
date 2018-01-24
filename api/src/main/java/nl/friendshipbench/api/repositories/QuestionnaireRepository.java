package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.Models.Questionnaire;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Jan-Bert on 23-1-2018.
 */
public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Long>
{
}
