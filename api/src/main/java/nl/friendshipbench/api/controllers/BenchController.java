package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.Models.Bench;
import nl.friendshipbench.api.repositories.BenchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Jan-Bert on 22-1-2018.
 */
@RestController
public class BenchController
{
	@Autowired
	private BenchRepository benchRepository;

	@GetMapping(value = "/benches")
	public ResponseEntity<Iterable<Bench>> getAllBenches() {
		return new ResponseEntity<Iterable<Bench>>(benchRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/benches/{id}")
	public ResponseEntity<Bench> getSingleBench(@PathVariable("id") long id) {
		return new ResponseEntity<Bench>(benchRepository.findOne(id), HttpStatus.OK);
	}

	@PostMapping(value = "/benches")
	public ResponseEntity<Bench> createBench(@RequestBody Bench bench) {
		benchRepository.save(bench);

		return new ResponseEntity<Bench>(benchRepository.findOne(bench.id), HttpStatus.OK);
	}

	@PutMapping(value = "/benches/{id}")
	public ResponseEntity<Bench> updateBench(@PathVariable("id") long id, @RequestBody Bench bench) {
		//TODO: Fix dingen met id
		benchRepository.save(bench);

		return new ResponseEntity<Bench>(benchRepository.findOne(id), HttpStatus.OK);
	}

	@DeleteMapping(value = "/benches/{id}")
	public ResponseEntity<Bench> updateBench(@PathVariable("id") long id) {
		benchRepository.delete(id);

		return new ResponseEntity<Bench>(HttpStatus.NO_CONTENT);
	}


}
