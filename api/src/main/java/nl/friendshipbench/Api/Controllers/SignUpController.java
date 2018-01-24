package nl.friendshipbench.Api.Controllers;

import nl.friendshipbench.Api.Models.Role;
import nl.friendshipbench.Api.Models.User;
import nl.friendshipbench.Api.Services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * The Sign up controller to register a user with role user
 *
 * @author Nick Oosterhuis
 */
@RestController
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody User user) {
        user.setRoles(Arrays.asList(new Role("USER")));
        User newUser = signUpService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
