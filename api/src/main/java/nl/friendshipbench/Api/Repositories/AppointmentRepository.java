package nl.friendshipbench.api.repositories;

import nl.friendshipbench.api.models.Appointment;
import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.HealthWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jan-Bert on 27-1-2018.
 */
@RestController
public interface AppointmentRepository extends CrudRepository<Appointment, Long>
{
	Iterable<Appointment> findByHealthWorker(HealthWorker healthWorker);
	Iterable<Appointment> findByClient(Client client);
}
