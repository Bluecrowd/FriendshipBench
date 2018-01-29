package nl.friendshipbench.api.repositories;

import com.sun.scenario.effect.Offset;
import nl.friendshipbench.api.models.Messages;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;

/**
 * Created by Jan-Bert on 29-1-2018.
 */
public interface MessagesRepository extends CrudRepository<Messages, Long>
{
	public Iterable<Messages> findByRoomAndTimeAfter(String room, OffsetDateTime offsetDateTime);
	public Iterable<Messages> findByRoom(String room);
}
