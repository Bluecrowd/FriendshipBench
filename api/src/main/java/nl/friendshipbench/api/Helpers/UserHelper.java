package nl.friendshipbench.api.Helpers;

import nl.friendshipbench.oauth2.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {

    /**
     * Method to get the principal of logged in user
     * @return principal
     */
    public CustomUserDetails principalHelper() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        return principal;
    }
}
