package ec.devnull.rest.springboot.app;


import ec.devnull.springboot.karaf.SpringBootActivator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JWTRestApplicationDummy extends SpringBootActivator<JWTRestApplicationDummy> {

    public static void main(String[] args) {
        SpringApplication.run(JWTRestApplicationDummy.class, args);

    }


}
