package ec.devnull.karafboot.dummy.error;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;

import static java.lang.String.format;

/**
 * Custom Error Decoder for Rest Service Responses
 *
 * @author Kleber Ayala
 */
public class RestClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        RestClientException exception = getErrorStatus(methodKey, response);
        return exception;
    }

    private RestClientException getErrorStatus(String methodKey, Response response) {
        String message = format("status %s reading %s", response.status(), methodKey);
        try {
            if (response.body() != null) {
                String body = Util.toString(response.body().asReader());
                message += "; content:\n" + body;
            }
        } catch (IOException ignored) { // NOPMD
        }
        return new RestClientException(response.status(), message);
    }
}

