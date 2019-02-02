package ec.devnull.rest.springboot.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


/**
 * @author kleber
 */
@Component
public class APIAuthenticationManager implements AuthenticationManager {

    @Value("${devnull.http.auth-token}")
    private String principalRequestValue;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String principal = (String) authentication.getPrincipal();
        if (!principalRequestValue.equals(principal)) {
            throw new BadCredentialsException("The API key was not found or not the expected value.");
        }
        // authentication.setAuthenticated(true);

        return authentication;
    }


}