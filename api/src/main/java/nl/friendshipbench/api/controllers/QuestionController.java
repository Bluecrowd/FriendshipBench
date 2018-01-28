package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.models.Question;
import nl.friendshipbench.api.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Question controller defines all possible HTTP methods for the questions
 *
 * Created by Jan-Bert on 22-1-2018.
 */
@RestController
public class QuestionController
{

	@Autowired
	private QuestionRepository questionRepository;

	/**
	 * Method to get all active questions
	 *
	 * @method HTTP GET
	 * @endpoint /api/questions
	 * @param onlyActive
	 * @return all questions
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@GetMapping(value = "/questions")
	public ResponseEntity<Iterable<Question>> getQuestions(@RequestParam(value="only-active", defaultValue = "false") String onlyActive)
	{
		boolean showOnlyActive = Boolean.parseBoolean(onlyActive);

		Iterable<Question> results;
		if(showOnlyActive)
		{
			results = questionRepository.findByActiveTrue();
		}
		else
		{
			results = questionRepository.findAll();
		}

		if (results != null)
		{
			return new ResponseEntity<>(results, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * method to get a specific question by id
	 *
	 * @method HTTP GET
	 * @endpoint /api/questions/{id}
	 * @param id
	 * @return specific question
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@GetMapping(value = "/questions/{id}")
	public ResponseEntity<Question> getSingleQuestion(@PathVariable("id") long id)
	{
		Question question = questionRepository.findOne(id);

		if (question != null)
		{
			return new ResponseEntity<>(question, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Method to create a question
	 *
	 * @method HTTP POST
	 * @endpoint /api/questions
	 * @param question
	 * @return response
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@PostMapping(value = "/questions")
	public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
		questionRepository.save(question);

		return new ResponseEntity<>(questionRepository.findOne(question.getId()), HttpStatus.CREATED);
	}

	/**
	 * Method to update a specific question
	 *
	 * @method HTTP PUT
	 * @endpoint /api/questions/{id}
	 * @param id
	 * @param question
	 * @return response
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@PutMapping(value = "/questions/{id}")
	public ResponseEntity<Question> updateQuestion(@PathVariable("id") long id, @RequestBody Question question) {
		question.setId(id);

		questionRepository.save(question);

		return new ResponseEntity<>(questionRepository.findOne(id), HttpStatus.OK);
	}
}
