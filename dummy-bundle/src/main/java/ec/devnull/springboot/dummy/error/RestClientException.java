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

package ec.devnull.springboot.dummy.error;


/**
 * Custom Exception for errors like invalid authorization, invalid keys, in general not valid server responses
 *
 * @author Kleber Ayala
 */
public class RestClientException extends RuntimeException {

    private final int status;


    protected RestClientException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int status() {
        return status;
    }


}
