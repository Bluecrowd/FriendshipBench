package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.models.Questionnaire;
import nl.friendshipbench.api.repositories.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jan-Bert on 23-1-2018.
 */
@RestController
public class QuestionnaireController
{
	@Autowired
	private QuestionnaireRepository questionnaireRepository;


	@CrossOrigin
	@GetMapping(value = "/questionnaires")
	public ResponseEntity<Iterable<Questionnaire>> getAllQuestionnaires() {
		return new ResponseEntity<>(questionnaireRepository.findAll(), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = "/questionnaires")
	public ResponseEntity<Questionnaire> createQuestionnaire(@RequestBody Questionnaire questionnaire) {
		questionnaireRepository.save(questionnaire);

		return new ResponseEntity<Questionnaire>(questionnaireRepository.findOne(questionnaire.id), HttpStatus.CREATED);
	}
}
