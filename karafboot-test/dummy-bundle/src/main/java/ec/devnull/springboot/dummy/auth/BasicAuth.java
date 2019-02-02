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
