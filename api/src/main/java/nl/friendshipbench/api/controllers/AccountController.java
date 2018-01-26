package nl.friendshipbench.api.controllers;


import nl.friendshipbench.api.Helpers.UserHelper;
import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.HealthWorker;
import nl.friendshipbench.api.models.Role;
import nl.friendshipbench.api.models.User;
import nl.friendshipbench.api.repositories.ClientRepository;
import nl.friendshipbench.api.repositories.HealthworkerRepository;
import nl.friendshipbench.api.repositories.UserRepository;
import nl.friendshipbench.api.services.RegisterService;
import nl.friendshipbench.oauth2.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@RestController
public class AccountController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private HealthworkerRepository healthworkerRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @RequestMapping(value = "/admin/register", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> registerAdmin(@RequestBody User user) {
        user.setRoles(Arrays.asList(new Role("USER")));
        User newUser = registerService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(value = "/healthworker/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerHealthWorker(@RequestBody HealthWorker healthWorker) {
        healthWorker.setRoles(Arrays.asList(new Role("HEALTHWORKER")));
        HealthWorker newUser = registerService.addHeathWorker(healthWorker);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(value = "/client/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerClient(@RequestBody Client client) {
        client.setRoles(Arrays.asList(new Role("CLIENT")));
        Client newUser = registerService.addClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(value = "/account/me", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<?> getUserInfo() {
        
        UserHelper userHelper = new UserHelper();
        CustomUserDetails principal = userHelper.principalHelper();
        String username = principal.getUsername();

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().equals("ROLE_CLIENT")) {
                Client request = clientRepository.findByUsername(username);
                return new ResponseEntity<Object>(request, HttpStatus.OK);
            }
            else if(authority.getAuthority().equals("ROLE_HEALTHWORKER")) {
                HealthWorker request = healthworkerRepository.findByUsername(username);
                return new ResponseEntity<Object>(request, HttpStatus.OK);
            }
            else if(authority.getAuthority().equals("ROLE_ADMIN")) {
                User request = userRepository.findByUsername(username);
                return new ResponseEntity<Object>(request, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
