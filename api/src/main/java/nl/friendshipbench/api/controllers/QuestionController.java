package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.models.Question;
import nl.friendshipbench.api.repositories.QuestionRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by Jan-Bert on 22-1-2018.
 */
@RestController
public class QuestionController
{

	@Autowired
	private QuestionRepository questionRepository;

	@CrossOrigin
	@GetMapping(value = "/questions")
	public ResponseEntity<Iterable<Question>> getQuestions(@RequestParam(value="only-active", defaultValue = "false") String onlyActive)
	{
		boolean showOnlyActive = Boolean.parseBoolean(onlyActive);

		Iterable<Question> results = null;
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
			return new ResponseEntity<Iterable<Question>>(results, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Iterable<Question>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin
	@GetMapping(value = "/questions/{id}")
	public ResponseEntity<Question> getSingleQuestion(@PathVariable("id") long id)
	{
		Question question = questionRepository.findOne(id);

		if (question != null)
		{
			return new ResponseEntity<Question>(question, HttpStatus.OK);
		}

		return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
	}

	@CrossOrigin
	@PostMapping(value = "/questions")
	public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
		questionRepository.save(question);

		return new ResponseEntity<Question>(questionRepository.findOne(question.id), HttpStatus.CREATED);
	}

	@CrossOrigin
	@PutMapping(value = "/questions/{id}")
	public ResponseEntity<Question> updateQuestion(@PathVariable("id") long id, @RequestBody Question question) {
		question.id = id;

		questionRepository.save(question);

		return new ResponseEntity<Question>(questionRepository.findOne(id), HttpStatus.OK);
	}

}
