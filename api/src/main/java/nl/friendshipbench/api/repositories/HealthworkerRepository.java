package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.HealthWorker;

import javax.transaction.Transactional;

/**
 * The HealthworkerRepositoty
 *
 * @author Nick Oosterhuis
 */

@Transactional
public interface HealthWorkerRepository extends UserBaseRepo<HealthWorker> {
}
