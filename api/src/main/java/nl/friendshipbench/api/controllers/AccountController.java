package nl.friendshipbench.api.controllers;


import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.HealthWorker;
import nl.friendshipbench.api.models.Role;
import nl.friendshipbench.api.models.User;
import nl.friendshipbench.api.services.RegisterService;
import nl.friendshipbench.oauth2.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class AccountController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/admin/register", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> registerAdmin(@RequestBody User user) {
        user.setRoles(Arrays.asList(new Role("USER")));
        User newUser = registerService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/healthworker/register", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> registerHealthWorker(@RequestBody HealthWorker healthWorker) {
        healthWorker.setRoles(Arrays.asList(new Role("HEALTHWORKER")));
        HealthWorker newUser = registerService.addHeathWorker(healthWorker);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/client/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerClient(@RequestBody Client client) {
        client.setRoles(Arrays.asList(new Role("CLIENT")));
        Client newUser = registerService.addClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/account/", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') or hasAuthority('ROLE_HEALTHWORKER')")
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        return new ResponseEntity<Object>(principal, HttpStatus.OK);
    }

}
