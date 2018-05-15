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

public class JWTAuth implements Authentication {

    private String key;
    private String headerName;

    public static JWTAuth get() {
        return new JWTAuth();
    }

    public JWTAuth headerApiKey(String headerName, String key) {
        this.key = key;
        this.headerName = headerName;
        return this;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header(headerName, key);
    }

}