package nl.friendshipbench.Api.Services;

import nl.friendshipbench.Api.Models.Role;
import nl.friendshipbench.Api.Models.User;
import nl.friendshipbench.Api.Repositories.UserRepository;
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
public class SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostConstruct
    private void setupDefaultUser() {

        if (userRepository.count() == 0) {
            userRepository.save(new User("admin",
                    passwordEncoder.encode("admin"),
                    Arrays.asList(new Role("USER"), new Role("ADMIN"))));
        }
    }


}
