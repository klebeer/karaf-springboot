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