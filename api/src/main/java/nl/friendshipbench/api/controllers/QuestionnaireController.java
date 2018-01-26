package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.HealthWorker;
import nl.friendshipbench.api.models.Questionnaire;
import nl.friendshipbench.api.repositories.ClientRepository;
import nl.friendshipbench.api.repositories.HealthworkerRepository;
import nl.friendshipbench.api.repositories.QuestionnaireRepository;
import nl.friendshipbench.oauth2.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jan-Bert on 23-1-2018.
 */
@RestController
public class QuestionnaireController
{

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private HealthworkerRepository healthworkerRepository;


	@Autowired
	private QuestionnaireRepository questionnaireRepository;

	@CrossOrigin
	@GetMapping(value = "/questionnaires")
	public ResponseEntity<Iterable<Questionnaire>> getAllQuestionnaires() {
		return new ResponseEntity<>(questionnaireRepository.findAll(), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(value = "/questionnaires/me")
	public ResponseEntity<Iterable<Questionnaire>> getAllQuestionnairesITook() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

		Client client = clientRepository.findByUsername(principal.getUsername());

		return new ResponseEntity<>(questionnaireRepository.findByClient(client), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(value = "/questionnaires/client/{id}")
	public ResponseEntity<Iterable<Questionnaire>> getAllQuestionnairesMyClientMade(@PathVariable("id") long clientId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

		HealthWorker healthworker = healthworkerRepository.findByUsername(principal.getUsername());

		Client client = clientRepository.findOne(clientId);



		if (client.getHealthWorker().getId().equals(healthworker.getId()))
		{
			return new ResponseEntity<>(questionnaireRepository.findByClient(client), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}


	}

	@CrossOrigin
	@PutMapping(value = "/questionnaires/{id}")
	public ResponseEntity<Questionnaire> updateQuestionnaire(@PathVariable("id") long id, @RequestBody Questionnaire questionnaire) {
		questionnaire.id = id;

		questionnaireRepository.save(questionnaire);

		return new ResponseEntity<Questionnaire>(questionnaireRepository.findOne(id), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = "/questionnaires")
	public ResponseEntity<Questionnaire> createQuestionnaire(@RequestBody Questionnaire questionnaire) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

		Client client = clientRepository.findByUsername(principal.getUsername());
		questionnaire.client = client;

		if(client != null)
		{
			questionnaireRepository.save(questionnaire);
			return new ResponseEntity<Questionnaire>(questionnaireRepository.findOne(questionnaire.id), HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<Questionnaire>(HttpStatus.BAD_REQUEST);
		}

	}
}
