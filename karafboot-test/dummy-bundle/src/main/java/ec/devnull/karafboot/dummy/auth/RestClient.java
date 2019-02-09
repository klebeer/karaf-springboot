package ec.devnull.karafboot.dummy.auth;


import ec.devnull.karafboot.dummy.error.RestClientErrorDecoder;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

import java.util.Objects;
import java.util.Optional;


/**
 * Feign Rest Service client implementation, used to create easy and lightweight rest clients, including Authentication
 *
 * @author Kleber Ayala
 */
public class RestClient {

    private Authentication authentication;
    private String serviceUrl;
    private Class<?> apiClass;


    public static RestClient get() {
        return new RestClient();
    }

    public RestClient auth(Authentication authentication) {
        this.authentication = authentication;
        return this;
    }

    public <T> T build() {
        return (T) buildFeingClient();
    }

    private Object buildFeingClient() {

        if (Objects.isNull(authentication)) {
            authentication = new NoAuthentication();
        }

        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(apiClass))
                .logLevel(Logger.Level.BASIC)
                .requestInterceptor(authentication)
                .errorDecoder(new RestClientErrorDecoder())
                .target(apiClass, serviceUrl);
    }


    private RestClient withJWTToken(JWTToken jwtToken) {
        this.authentication = jwtToken;
        return this;
    }

    public RestClient withJWTToken(Optional<String> jwtTokenOptional) {
        JWTToken jwtToken = JWTToken.build(jwtTokenOptional);
        return withJWTToken(jwtToken);
    }

    public RestClient apiClass(Class apiClass) {
        this.apiClass = apiClass;
        return this;
    }

    public RestClient url(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        return this;
    }


}
