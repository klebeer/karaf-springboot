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
