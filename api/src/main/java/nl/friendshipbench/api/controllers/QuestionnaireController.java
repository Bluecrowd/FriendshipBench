package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.Helpers.UserHelper;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Questionnaire controller defines all possible HTTP methods for the questionnaires
 *
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

	private UserHelper userHelper = new UserHelper();

	/**
	 * Method to get all questionnaires bound to a health worker or client
	 *
	 * @method HTTP GET
	 * @endpoint /api/questionnaires
	 * @return all questionnaires
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER')")
	@GetMapping(value = "/questionnaires")
	public ResponseEntity<Iterable<Questionnaire>> getAllQuestionnaires() {
		CustomUserDetails principal = userHelper.principalHelper();

		List<Questionnaire> allQuestionnaires = new ArrayList<>();

		for( GrantedAuthority authority : principal.getAuthorities())
		{
			if (authority.toString().equals("ROLE_HEALTHWORKER"))
			{
				HealthWorker healthworker = healthworkerRepository.findByUsername(principal.getUsername());

				Iterable<Client> clients = clientRepository.findByHealthWorker(healthworker);



				for(Client client : clients)
				{
					List<Questionnaire> questionnaires = questionnaireRepository.findByClient(client);
					allQuestionnaires = Stream.concat(allQuestionnaires.stream(), questionnaires.stream())
						.collect(Collectors.toList());
				}
				break;
			}
			if (authority.toString().equals("ROLE_CLIENT"))
			{
				Client client = clientRepository.findByUsername(principal.getUsername());

				allQuestionnaires = questionnaireRepository.findByClient(client);
				break;
			}
		}

		return new ResponseEntity<>(allQuestionnaires, HttpStatus.OK);
	}

	/**
	 * Method to get a questionnaire bound to a health worker or client by id
	 *
	 * @method HTTP GET
	 * @endpoint /api/questionnaires/{id}
	 * @return specific questionnaire
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER')")
	@GetMapping(value = "/questionnaires/{id}")
	public ResponseEntity<Questionnaire> getSpecificQuestionnaire(@PathVariable("id") long id) {
		CustomUserDetails principal = userHelper.principalHelper();

		Questionnaire questionnaire = questionnaireRepository.findOne(id);

		for( GrantedAuthority authority : principal.getAuthorities())
		{
			if (authority.toString().equals("ROLE_HEALTHWORKER"))
			{
				HealthWorker healthworker = healthworkerRepository.findByUsername(principal.getUsername());

				if (questionnaire.getClient().getHealthWorker().equals(healthworker))
				{
					break;
				}
				else
				{
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}

			}
			if (authority.toString().equals("ROLE_CLIENT"))
			{
				Client client = clientRepository.findByUsername(principal.getUsername());

				if (questionnaire.getClient().equals(client))
				{
					break;
				}
				else
				{
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}
			}
		}

		return new ResponseEntity<>(questionnaire, HttpStatus.OK);
	}

	/**
	 * Method to get a questionnaire from a bound client
	 *
	 * @method HTTP GET
	 * @endpoint /api/questionnaires/client/{id}
	 * @param clientId
	 * @return
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_HEALTHWORKER')")
	@GetMapping(value = "/questionnaires/client/{id}")
	public ResponseEntity<Iterable<Questionnaire>> getAllQuestionnairesMyClientMade(@PathVariable("id") long clientId) {
		CustomUserDetails principal = userHelper.principalHelper();

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

	/**
	 * Method to update a specific questionnaire by id
	 *
	 * @method HTTP PUT
	 * @endpoint /api/
	 * @param id
	 * @param questionnaire
	 * @return
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@PutMapping(value = "/questionnaires/{id}")
	public ResponseEntity<Questionnaire> updateQuestionnaire(@PathVariable("id") long id, @RequestBody Questionnaire questionnaire) {
		questionnaire.setId(id);

		questionnaireRepository.save(questionnaire);

		return new ResponseEntity<>(questionnaireRepository.findOne(id), HttpStatus.OK);
	}

	/**
	 * Method to create a questionnaire
	 *
	 * @method HTTP POST
	 * @endpoint /api/questionnaires
	 * @param questionnaire
	 * @return
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@PostMapping(value = "/questionnaires")
	public ResponseEntity<Questionnaire> createQuestionnaire(@RequestBody Questionnaire questionnaire) {
		CustomUserDetails principal = userHelper.principalHelper();

		Client client = clientRepository.findByUsername(principal.getUsername());
		questionnaire.setClient(client);

		if(client != null)
		{
			questionnaireRepository.save(questionnaire);
			return new ResponseEntity<>(questionnaireRepository.findOne(questionnaire.getId()), HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
}
