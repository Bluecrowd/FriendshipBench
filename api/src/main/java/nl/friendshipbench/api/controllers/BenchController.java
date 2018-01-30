package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.models.Bench;
import nl.friendshipbench.api.repositories.BenchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Bench controller defines all possible HTTP methods for the appointments
 *
 * Created by Jan-Bert on 22-1-2018.
 */
@RestController
public class BenchController
{
	@Autowired
	private BenchRepository benchRepository;

	/**
	 * Method to get all the benches
	 *
	 * @method HTTP GET
	 * @endpoint /api/benches
	 * @return all benches
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@GetMapping(value = "/benches")
	public ResponseEntity<Iterable<Bench>> getAllBenches() {
		return new ResponseEntity<>(benchRepository.findAll(), HttpStatus.OK);
	}

	/**
	 * Method to get a specific bench by id
	 *
	 * @method HTTP GET
	 * @endpoint /api/benches/{id}
	 * @param id
	 * @return specific bench
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@GetMapping(value = "/benches/{id}")
	public ResponseEntity<Bench> getSingleBench(@PathVariable("id") long id) {

		Bench bench = benchRepository.findOne(id);

		if (bench != null)
		{
			return new ResponseEntity<>(bench, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	/**
	 * Method to create a bench
	 *
	 * @method HTTP POST
	 * @endpoint /api/benches
	 * @param bench
	 * @return response
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@PostMapping(value = "/benches")
	public ResponseEntity<Bench> createBench(@RequestBody Bench bench) {
		benchRepository.save(bench);

		return new ResponseEntity<>(benchRepository.findOne(bench.getId()), HttpStatus.CREATED);
	}

	/**
	 * Method to update the values of a specific bench
	 *
	 * @method HTTP PUT
	 * @endpoint api/benches/{id}
	 * @param id
	 * @param bench
	 * @return response
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@PutMapping(value = "/benches/{id}")
	public ResponseEntity<Bench> updateBench(@PathVariable("id") long id, @RequestBody Bench bench) {
		bench.setId(id);

		benchRepository.save(bench);

		return new ResponseEntity<>(benchRepository.findOne(id), HttpStatus.OK);
	}

	/**
	 * Method to delete a specific bench
	 *
	 * @method HTTP DELETE
	 * @endpoint /benches/{id}
	 * @param id
	 * @return response
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_ADMIN')")
	@DeleteMapping(value = "/benches/{id}")
	public ResponseEntity<Bench> deleteBench(@PathVariable("id") long id) {
		benchRepository.delete(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


}
