package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.models.Bench;
import nl.friendshipbench.api.repositories.BenchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jan-Bert on 22-1-2018.
 */

@RestController
public class BenchController
{
	@Autowired
	private BenchRepository benchRepository;

	@CrossOrigin
	@GetMapping(value = "/benches")
	public ResponseEntity<Iterable<Bench>> getAllBenches() {
		return new ResponseEntity<Iterable<Bench>>(benchRepository.findAll(), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(value = "/benches/{id}")
	public ResponseEntity<Bench> getSingleBench(@PathVariable("id") long id) {

		Bench bench = benchRepository.findOne(id);

		if (bench != null)
		{
			return new ResponseEntity<Bench>(bench, HttpStatus.OK);
		}

		return new ResponseEntity<Bench>(HttpStatus.NOT_FOUND);

	}

	@CrossOrigin
	@PostMapping(value = "/benches")
	public ResponseEntity<Bench> createBench(@RequestBody Bench bench) {
		benchRepository.save(bench);

		return new ResponseEntity<Bench>(benchRepository.findOne(bench.id), HttpStatus.CREATED);
	}

	@CrossOrigin
	@PutMapping(value = "/benches/{id}")
	public ResponseEntity<Bench> updateBench(@PathVariable("id") long id, @RequestBody Bench bench) {
		//TODO: Fix dingen met id
		benchRepository.save(bench);

		return new ResponseEntity<Bench>(benchRepository.findOne(id), HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping(value = "/benches/{id}")
	public ResponseEntity<Bench> updateBench(@PathVariable("id") long id) {
		benchRepository.delete(id);

		return new ResponseEntity<Bench>(HttpStatus.NO_CONTENT);
	}


}
