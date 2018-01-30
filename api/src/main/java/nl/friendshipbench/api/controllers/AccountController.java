package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.Helpers.UserHelper;
import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.HealthWorker;
import nl.friendshipbench.api.models.Role;
import nl.friendshipbench.api.models.User;
import nl.friendshipbench.api.repositories.ClientRepository;
import nl.friendshipbench.api.repositories.HealthworkerRepository;
import nl.friendshipbench.api.repositories.RoleRepository;
import nl.friendshipbench.api.repositories.UserRepository;
import nl.friendshipbench.oauth2.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * RestController for all account features
 *
 * @author Nick Oosterhuis
 */
@RestController
public class AccountController {

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private HealthworkerRepository healthworkerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserHelper userHelper = new UserHelper();

    /**
     * Method to show the details of the logged in user
     *
     * @method HTTP GET
     * @endpoint api/account/me
     * @return ResponseEntity
     */
    @CrossOrigin
    @RequestMapping(value = "/account/me", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PENDING') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<?> getUserInfo() {
        CustomUserDetails principal = userHelper.principalHelper();
        String username = principal.getUsername();

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().equals("ROLE_CLIENT")) {
                Client request = clientRepository.findByUsername(username);
                return new ResponseEntity<Object>(request, HttpStatus.OK);
            }
            else if(authority.getAuthority().equals("ROLE_HEALTHWORKER") || authority.getAuthority().equals("ROLE_PENDING")) {
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
     * @method HTTP PUT
     * @endpoint /api/account/me
     * @param mapper
     * @return ResponseEntity
     * @throws Exception
     */
    @CrossOrigin
    @RequestMapping(value = "/account/me", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<?> editUserInfo(@RequestBody HashMap<String, Object> mapper) throws Exception {
        CustomUserDetails principal = userHelper.principalHelper();
        String currentUsername = principal.getUsername();

        String firstName = (String) mapper.get("firstName");
        String lastName = (String) mapper.get("lastName");
        String gender = (String) mapper.get("gender");
        String streetName = (String) mapper.get("streetName");
        String houseNumber = (String) mapper.get("houseNumber");
        String province = (String) mapper.get("province");
        String district = (String) mapper.get("district");
        //OffsetDateTime birthday = (OffsetDateTime) mapper.get("birthDay");

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        for (GrantedAuthority authority : authorities) {

            //Users with role CLIENT
            if(authority.getAuthority().equals("ROLE_CLIENT")) {
                Client currentUser = clientRepository.findByUsername(currentUsername);

                currentUser.setFirstName(firstName);
                currentUser.setLastName(lastName);
                currentUser.setGender(gender);
                currentUser.setStreetName(streetName);
                currentUser.setHousenumber(houseNumber);
                currentUser.setProvince(province);
                currentUser.setDistrict(district);
                //currentUser.setBirthDay(birthday);


                clientRepository.save(currentUser);
                return ResponseEntity.ok("User updated successfully");
            }
            //users with role HEALTHWORKER
            else if(authority.getAuthority().equals("ROLE_HEALTHWORKER")) {
                HealthWorker currentUser = healthworkerRepository.findByUsername(currentUsername);

                currentUser.setFirstName(firstName);
                currentUser.setLastName(lastName);
                currentUser.setGender(gender);

                healthworkerRepository.save(currentUser);
                return new ResponseEntity<Object>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Something went wrong ", HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to update the user values of each type op user which is currently logged in
     *
     * @method HTTP PUT
     * @endpoint /api//account/details/me
     * @param mapper
     * @return ResponseEntity
     * @throws Exception
     */
    @CrossOrigin
    @RequestMapping(value = "/account/details/me", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_HEALTHWORKER') or hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<?> editAccountInfo(@RequestBody HashMap<String, Object> mapper) throws Exception {
        CustomUserDetails principal = userHelper.principalHelper();
        String currentUsername = principal.getUsername();

        String email = (String) mapper.get("email");
        String phoneNumber = (String) mapper.get("phonenumber");
        //OffsetDateTime birthday = (OffsetDateTime) mapper.get("birthday");

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        for (GrantedAuthority authority : authorities) {

            //Users with role CLIENT
            if(authority.getAuthority().equals("ROLE_CLIENT")) {
                Client currentUser = clientRepository.findByUsername(currentUsername);

                currentUser.setEmail(email);
                currentUser.setPhonenumber(phoneNumber);
                //currentUser.setBirthDay(birthday);


                clientRepository.save(currentUser);
                return ResponseEntity.ok("User updated successfully");
            }
            //users with role HEALTHWORKER
            else if(authority.getAuthority().equals("ROLE_HEALTHWORKER")) {
                HealthWorker currentUser = healthworkerRepository.findByUsername(currentUsername);

                currentUser.setEmail(email);
                currentUser.setPhonenumber(phoneNumber);
                //currentUser.setBirthDay(birthday);

                healthworkerRepository.save(currentUser);
                return new ResponseEntity<Object>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Something went wrong ", HttpStatus.BAD_REQUEST);
    }


    /**
     * Method to get the clients who are bound to the healthworker
     *
     * @method HTTP GET
     * @endpoint /api/account/getmyclients
     * @return myClients
     */
    @CrossOrigin
    @PreAuthorize("hasAuthority('ROLE_HEALTHWORKER')")
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

    /**
     * Method to chose and set your health worker as a client
     *
     * @method HTTP PUT
     * @endpoint /api/account/setmyhealthworker/{id}
     * @param id
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @RequestMapping(value = "/account/setmyhealthworker/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> setMyHealthWorker(@PathVariable("id") long id) {
        CustomUserDetails principal = userHelper.principalHelper();
        String username = principal.getUsername();
        Client currentUser = clientRepository.findByUsername(username);

        HealthWorker chosenHealthWorker = healthworkerRepository.findOne(id);

        if(chosenHealthWorker != null) {
            currentUser.setHealthWorker(chosenHealthWorker);
            clientRepository.save(currentUser);
            return new ResponseEntity<Object>("Your selected health worker: " + chosenHealthWorker.getFirstName() + " " +chosenHealthWorker.getLastName(), HttpStatus.OK);
        }

        return new ResponseEntity<Object>("Something went wrong", HttpStatus.I_AM_A_TEAPOT);
    }

    /**
     * Method to approve a healthworker by injecting custom JSON
     * it expects "approve": true or false in boolean format
     *
     * @method HTTP PUT
     * @endpoint /account/approve/{id}
     * @param id
     * @param mapper
     * @return ResponseEntity
     */
    @CrossOrigin
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/account/approve/{id}")
    public  ResponseEntity<?> approveHealthWorkerRole(@PathVariable("id") long id, @RequestBody HashMap<String, Object> mapper) {

        Role role = roleRepository.getByRoleName("HEALTHWORKER");
        boolean approve = (boolean) mapper.get("approve");

        HealthWorker healthWorker = healthworkerRepository.findOne(id);
        List<Role> currentRoles = healthWorker.getRoles();

        if(healthWorker != null && !currentRoles.contains(role)) {
            if (approve) {
                currentRoles.clear();
                currentRoles.add(role);

                healthWorker.setRoles(currentRoles);
                healthworkerRepository.save(healthWorker);
                return ResponseEntity.ok("Health worker role is approved" + role.getRoleName());
            } else if (approve == false)
                return ResponseEntity.ok("Health worker is not approved");
        }
        return new ResponseEntity<Object>("Something went wrong", HttpStatus.BAD_REQUEST);
    }
}
