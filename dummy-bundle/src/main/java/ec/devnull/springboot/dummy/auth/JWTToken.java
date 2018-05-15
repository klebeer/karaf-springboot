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

package ec.devnull.springboot.dummy.auth;

import feign.RequestTemplate;

import java.util.Optional;

public class JWTToken implements Authentication {

    private String token;

    private JWTToken(String token) {
        this.token = token;
    }

    public static JWTToken build(String token) {
        return new JWTToken(token);
    }

    public static JWTToken build(Optional<String> optionalJwtToken) {
        String token = optionalJwtToken.orElse("ND");
        return new JWTToken(token);
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", token);
    }
}
