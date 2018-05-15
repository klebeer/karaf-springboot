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

import feign.Headers;
import feign.RequestLine;
import feign.Response;

import java.util.Collections;
import java.util.Optional;

public interface JWTClient {

    String AUTHORIZATION = "authorization";

    @RequestLine("POST /login")
    @Headers({"Content-Type: application/json"})
    Response auth();

    default Optional<String> getToken() {
        Response response = auth();
        return response.headers().
                getOrDefault(AUTHORIZATION, Collections.singleton("ND")).
                stream().findFirst();
    }

}
