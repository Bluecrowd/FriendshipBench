package nl.friendshipbench;

import nl.friendshipbench.Api.Models.Role;
import nl.friendshipbench.Api.Models.User;
import nl.friendshipbench.Api.Repositories.UserRepository;
import nl.friendshipbench.OAuth2.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import java.util.Arrays;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
