package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.HealthWorker;

import javax.transaction.Transactional;

/**
 * The ClientRepositoty
 *
 * @author Nick Oosterhuis
 */

@Transactional
public interface ClientRepository extends UserBaseRepo<Client> {
	public Iterable<Client> findByHealthWorker(HealthWorker healthWorker);
}
