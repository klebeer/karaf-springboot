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
