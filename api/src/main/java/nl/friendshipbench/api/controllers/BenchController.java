package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.Models.Bench;
import nl.friendshipbench.api.repositories.BenchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Jan-Bert on 22-1-2018.
 */
@RestController
public class BenchController
{
	@Autowired
	private BenchRepository benchRepository;

	@RequestMapping(value = "/benches")
	public Iterable<Bench> getAllBenches() {
		return benchRepository.findAll();
	}

	@RequestMapping(value = "/benches/{id}")
	public Bench getSingleBench(@PathVariable("id") long id) {
		return benchRepository.findOne(id);
	}
}
