package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.Client;

import javax.transaction.Transactional;

/**
 * The ClientRepositoty
 *
 * @author Nick Oosterhuis
 */

@Transactional
public interface ClientRepository extends UserBaseRepo<Client> {
}
