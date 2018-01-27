package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.Helpers.UserHelper;
import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.HealthWorker;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

/**
 * RestController for all account features
 *
 * @author Nick Oosterhuis
 */
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserHelper userHelper = new UserHelper();

    /**
     * Method to show the details of the logged in user
     *
     * @method GET
     * @endpoint api/account/me
     * @return ResponseEntity
     */
    @CrossOrigin
    @RequestMapping(value = "/account/me", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<?> getUserInfo() {
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


    /**
     * Method to update the user values of each type op user which is currently logged in
     *
     * @method PUT
     * @param mapper
     * @return ResponseEntity
     * @throws Exception
     * @endpoint /api/account/me
     */
    @CrossOrigin
    @RequestMapping(value = "/account/me", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<?> editUserInfo(@RequestBody HashMap<String, Object> mapper) throws Exception {
        CustomUserDetails principal = userHelper.principalHelper();
        String currentUsername = principal.getUsername();

        String oldPassword = (String) mapper.get("oldPassword");
        String newPassword = (String) mapper.get("newPassword");
        String firstName = (String) mapper.get("firstName");
        String lastName = (String) mapper.get("lastName");
        String gender = (String) mapper.get("gender");
        int age = (int) mapper.get("age");
        String streetName = (String) mapper.get("streetName");
        String houseNumber = (String) mapper.get("houseNumber");
        String province = (String) mapper.get("province");
        String district = (String) mapper.get("district");
        String email = (String) mapper.get("email");
        String phoneNumber = (String) mapper.get("phoneNumber");

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        for (GrantedAuthority authority : authorities) {

            //Users with role CLIENT
            if(authority.getAuthority().equals("ROLE_CLIENT")) {
                Client currentUser = clientRepository.findByUsername(currentUsername);
                String dbPassword = currentUser.getPassword();

                if(passwordEncoder.matches(dbPassword, oldPassword))
                    if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals(""))
                        currentUser.setPassword(passwordEncoder.encode(newPassword));

                currentUser.setFirstName(firstName);
                currentUser.setLastName(lastName);
                currentUser.setGender(gender);
                currentUser.setAge(age);
                currentUser.setStreetName(streetName);
                currentUser.setHousenumber(houseNumber);
                currentUser.setProvince(province);
                currentUser.setDistrict(district);
                currentUser.setEmail(email);
                currentUser.setPhonenumber(phoneNumber);

                clientRepository.save(currentUser);
                return ResponseEntity.ok("User updated successfully");
            }
            //users with role HEALTHWORKER
            else if(authority.getAuthority().equals("ROLE_HEALTHWORKER") || authority.getAuthority().equals("ROLE_PENDING")) {
                HealthWorker currentUser = healthworkerRepository.findByUsername(currentUsername);
                String dbPassword = currentUser.getPassword();

                if (passwordEncoder.matches(dbPassword, oldPassword))
                    if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals(""))
                        currentUser.setPassword(passwordEncoder.encode(newPassword));

                currentUser.setFirstName(firstName);
                currentUser.setLastName(lastName);
                currentUser.setGender(gender);
                currentUser.setAge(age);
                currentUser.setEmail(email);
                currentUser.setPhonenumber(phoneNumber);

                healthworkerRepository.save(currentUser);
                return ResponseEntity.ok("User updated successfully");
            }
            //users with role ADMIN
            else if(authority.getAuthority().equals("ROLE_ADMIN")) {
                User currentUser = userRepository.findByUsername(currentUsername);
                String dbPassword = currentUser.getPassword();

                if(passwordEncoder.matches(dbPassword, oldPassword))
                    if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals(""))
                        currentUser.setPassword(passwordEncoder.encode(newPassword));

                userRepository.save(currentUser);
                return ResponseEntity.ok("User updated successfully");
            }
        }
        return new ResponseEntity<>("Something went wrong ", HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @RequestMapping(value = "/account/getmyclients", method = RequestMethod.GET)
    public ResponseEntity<?> getMyClients() {
        CustomUserDetails principal = userHelper.principalHelper();
        String username = principal.getUsername();

        HealthWorker currentUser = healthworkerRepository.findByUsername(username);
        Iterable<Client> myClients = clientRepository.findByHealthWorker(currentUser);
        int size;

        if(myClients instanceof Collection<?>) {
            size = ((Collection<?>) myClients).size();
            if (size == 0) {
                return new ResponseEntity<Object>("You don't have any Clients bound to you", HttpStatus.NO_CONTENT);
            } else
                return new ResponseEntity<Object>(myClients, HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Something went wrong", HttpStatus.I_AM_A_TEAPOT);
    }

    @CrossOrigin
    @RequestMapping(value = "/account/setmyhealthworker/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> setMyHealthWorker(@PathVariable("id") long id) {
        CustomUserDetails principal = userHelper.principalHelper();
        String username = principal.getUsername();
        Client currentUser = clientRepository.findByUsername(username);

        HealthWorker choosenHealthWorker = healthworkerRepository.findOne(id);

        if(choosenHealthWorker != null) {
            currentUser.setHealthWorker(choosenHealthWorker);
            clientRepository.save(currentUser);
            return new ResponseEntity<Object>("Your selected health worker: " + choosenHealthWorker.getFirstName() + " " +choosenHealthWorker.getLastName(), HttpStatus.OK);
        }

        return new ResponseEntity<Object>("Something went wrong", HttpStatus.I_AM_A_TEAPOT);
    }
}
