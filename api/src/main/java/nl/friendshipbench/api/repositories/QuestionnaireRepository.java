package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.Questionnaire;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Jan-Bert on 23-1-2018.
 */
public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Long>
{
	public List<Questionnaire> findByClient(Client client);
}
