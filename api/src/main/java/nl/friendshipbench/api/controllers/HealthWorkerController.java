package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.models.HealthWorker;
import nl.friendshipbench.api.models.Role;
import nl.friendshipbench.api.repositories.HealthworkerRepository;
import nl.friendshipbench.api.repositories.RoleRepository;
import nl.friendshipbench.api.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * The health worker controller with all the possible HTTP methods
 *
 * @author Nick Oosterhuis
 */
@RestController
public class HealthWorkerController {

    @Autowired
    private HealthworkerRepository healthworkerRepository;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * GET Method to gather all health workers
     *
     * @method HTTP GET
     * @endpoint api/healthworkers
     * @return all health workers
     */
    @CrossOrigin
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_CLIENT')")
    @GetMapping(value = "/healthworkers")
    public ResponseEntity<Iterable<HealthWorker>> getAllHealthWorkers() {
        return new ResponseEntity<>(healthworkerRepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET Method to gather one health worker by id
     *
     * @method HTTP GET
     * @endpoint /api/healthworkers/{id}
     * @param id
     * @return specific health worker
     */
    @CrossOrigin
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_CLIENT')")
    @GetMapping(value = "/healthworkers/{id}")
    public ResponseEntity<HealthWorker> getHealthWorkerById(@PathVariable("id") long id) {
        HealthWorker healthWorker = healthworkerRepository.findOne(id);

        if (healthWorker != null)
        {
            return new ResponseEntity<>(healthWorker, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method makes it possible to register an health worker
     *
     * @method HTTP POST
     * @endpoint api/healthworkers/register
     * @param healthWorker
     * @return ResponseEntity
     */
    @CrossOrigin
    @RequestMapping(value = "/healthworkers/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerHealthWorker(@RequestBody HealthWorker healthWorker) {
        Role role = roleRepository.getByRoleName("PENDING");
        healthWorker.setRoles(Arrays.asList(role));
        HealthWorker newUser = registerService.addHeathWorker(healthWorker);

        return new ResponseEntity<>("Successfully created health worker", HttpStatus.CREATED);
    }
}
