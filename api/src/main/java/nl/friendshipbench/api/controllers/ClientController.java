package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.Role;
import nl.friendshipbench.api.repositories.ClientRepository;
import nl.friendshipbench.api.repositories.RoleRepository;
import nl.friendshipbench.api.services.RegisterService;
import nl.friendshipbench.oauth2.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * The Client controller with all possible http methods
 *
 * @author Nick Oosterhuis
 */
@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * GET Method to gather all clients
     *
     * @endpoint api/clients
     * @return all Clients
     */
    @CrossOrigin
    @GetMapping(value = "/clients")
    public ResponseEntity<Iterable<Client>> getAllClients() {
        return new ResponseEntity<>(clientRepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET Method to gather one client by id
     *
     * @param id
     * @return specific client
     */
    @CrossOrigin
    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Client> getClientbyId(@PathVariable("id") long id) {
        Client client = clientRepository.findOne(id);

        if (client != null)
        {
            return new ResponseEntity<>(client, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Method makes it possible to register an client
     *
     * @endpoint api/clients/register
     * @param client
     * @return ResponseEntity
     */
    @CrossOrigin
    @RequestMapping(value = "/clients/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerClient(@RequestBody Client client) {

        Role role = roleRepository.getByRoleName("CLIENT");
        client.setRoles(Arrays.asList(role));
        Client newUser = registerService.addClient(client);

        return new ResponseEntity<>("Successfully created client", HttpStatus.CREATED);
    }

}
