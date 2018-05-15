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

import java.nio.charset.Charset;
import java.util.Base64;

public class BasicAuth implements Authentication {


    private String username;
    private String password;

    public static BasicAuth get() {
        return new BasicAuth();
    }

    public BasicAuth username(String username) {

        this.username = username;
        return this;
    }

    public BasicAuth password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public void apply(RequestTemplate template) {
        String headerValue = "Basic " + Base64.getEncoder().
                encodeToString((username + ":" + password).getBytes(Charset.defaultCharset()));
        template.header("Authorization", headerValue);
    }

}
