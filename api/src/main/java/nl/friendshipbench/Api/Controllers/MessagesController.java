package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.models.Messages;
import nl.friendshipbench.api.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

/**
 * Created by Jan-Bert on 29-1-2018.
 */
@RestController
public class MessagesController
{

	@Autowired
	private MessagesRepository messagesRepository;

	/**
	 * Method to get all message
	 *
	 * @method HTTP GET
	 * @endpoint /api/messages
	 * @param room
	 * @param timestamp
	 * @return all message
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@GetMapping(value = "/messages/{room}")
	public ResponseEntity<Iterable<Messages>> getMessages(@PathVariable("room") String room, @RequestParam(value="timestamp", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime timestamp)
	{
		if(timestamp != null)
		{
			Iterable<Messages> messages = messagesRepository.findByRoomAndTimeAfter(room, timestamp);
			return new ResponseEntity<>(messages, HttpStatus.OK);
		}
		else
		{
			Iterable<Messages> messages = messagesRepository.findByRoom(room);
			return new ResponseEntity<>(messages, HttpStatus.OK);
		}
	}

}
