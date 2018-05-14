/*
 * mcm-rest-client
 *
 * Copyright (c) 2018.  FISA Group.
 *
 * This software is the confidential and proprietary information FISA GROUP
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with FISA Group.
 */

package ec.devnull.rest.client.config;

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

    @Value("${omnia.http.auth-token}")
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