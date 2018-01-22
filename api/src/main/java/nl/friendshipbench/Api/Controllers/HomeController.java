package nl.friendshipbench.Api.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/")
    public String home() {
        return "Hello";
    }

    @GetMapping(value = "/private")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String privateArea() {
        return "This is a private page";
    }
}
