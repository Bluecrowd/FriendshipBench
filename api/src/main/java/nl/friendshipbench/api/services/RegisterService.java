package nl.friendshipbench.api.services;

import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.HealthWorker;
import nl.friendshipbench.api.models.Role;
import nl.friendshipbench.api.models.User;
import nl.friendshipbench.api.repositories.ClientRepository;
import nl.friendshipbench.api.repositories.HealthworkerRepository;
import nl.friendshipbench.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Sign up service with BCrypt password encoder
 *
 * @author Nick Oosterhuis
 */
@Service
@Transactional
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HealthworkerRepository healthworkerRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public HealthWorker addHeathWorker(HealthWorker healthWorker) {
        healthWorker.setPassword(passwordEncoder.encode(healthWorker.getPassword()));
        return healthworkerRepository.save(healthWorker);
    }

    public Client addClient(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    @PostConstruct
    private void setupDefaultUser() {

        if (userRepository.count() == 0) {

            userRepository.save(new User("admin",
                    passwordEncoder.encode("admin"),
                    Arrays.asList(new Role("ADMIN"))));
        }

        if (healthworkerRepository.count() == 0) {

            HealthWorker healthWorker = new HealthWorker();

            healthWorker.setUsername("TestHW");
            healthWorker.setPassword(passwordEncoder.encode("test"));
            healthWorker.setAge(24);
            healthWorker.setEmail("test@example.com");
            healthWorker.setFirstName("firstname");
            healthWorker.setLastName("lastname");
            healthWorker.setGender("male");
            healthWorker.setPhonenumber("06123456789");
            healthWorker.setRoles(Arrays.asList(new Role("HEALTHWORKER"), new Role("PENDING")));

            healthworkerRepository.save(healthWorker);
        }

        if (clientRepository.count() == 0) {

            Client client = new Client();

            client.setUsername("TestClient");
            client.setPassword(passwordEncoder.encode("test"));
            client.setFirstName("firstname");
            client.setLastName("lastname");
            client.setAge(40);
            client.setEmail("test@example.com");
            client.setGender("Female");
            client.setStreetName("streetname");
            client.setHousenumber("10");
            client.setProvince("Grunn");
            client.setDistrict("Nederland");
            client.setPhonenumber("0123456789");
            client.setRoles(Arrays.asList(new Role("CLIENT")));

            userRepository.save(client);
        }
    }


}
