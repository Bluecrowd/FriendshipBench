package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.models.HealthWorker;
import nl.friendshipbench.api.models.Role;
import nl.friendshipbench.api.models.User;
import nl.friendshipbench.api.repositories.HealthworkerRepository;
import nl.friendshipbench.api.repositories.RoleRepository;
import nl.friendshipbench.api.repositories.UserRepository;
import nl.friendshipbench.api.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Admin controller for all possible http operations regarding getting all admins, get one by id and creating one
 *
 * @author Nick Oosterhuis
 */
@RestController
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HealthworkerRepository healthworkerRepository;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * GET Method to gather all admins
     *
     * @method HTTP GET
     * @endpoint api/admins
     * @return all admins
     */
    @CrossOrigin
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/admins")
    public ResponseEntity<Iterable<User>> getAllAdmins() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET Method to gather one admin by id
     *
     * @method HTTP GET
     * @endpoint /api/admins/{id}
     * @param id
     * @return specific admin
     */
    @CrossOrigin
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/admins/{id}")
    public ResponseEntity<User> getAdminById(@PathVariable("id") long id) {
        User user = userRepository.findOne(id);

        if (user != null)
        {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method makes it possible to register an admin
     * Expects an username, password and role
     *
     * @method HTTP POST
     * @endpoint api/admins/register
     * @param user
     * @return ResponseEntity
     */
    @CrossOrigin
    @RequestMapping(value = "/admins/register", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> registerAdmin(@RequestBody User user) {
        Role role = roleRepository.getByRoleName("ADMIN");
        user.setRoles(Arrays.asList(role));
        User newUser = registerService.addUser(user);

        return new ResponseEntity<>("successfully created admin", HttpStatus.CREATED);
    }

    /**
     * Method to approve a healthworker by injecting custom JSON
     * it expects "approve": true or false in boolean format
     *
     * @method HTTP PUT
     * @endpoint /admins/approvehealthworker/{id}
     * @param id
     * @param mapper
     * @return ResponseEntity
     */
    @CrossOrigin
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/admins/approvehealthworker/{id}")
    public  ResponseEntity<?> approveHealthWorkerRole(@PathVariable("id") long id, @RequestBody HashMap<String, Object> mapper) {
        HealthWorker healthWorker = healthworkerRepository.findOne(id);
        Role role = roleRepository.getByRoleName("HEALTHWORKER");
        boolean approve = (boolean) mapper.get("approve");

        if(approve) {
            healthWorker.setRoles(Arrays.asList(role));
            return ResponseEntity.ok("Health worker role is approved");
        }
        else
            return ResponseEntity.ok("Health worker is not approved");
    }


}
