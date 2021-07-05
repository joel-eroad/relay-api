package io.relay;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
        java.security.Security.setProperty("networkaddress.cache.ttl", "1");
        java.security.Security.setProperty("networkaddress.cache.negative.ttl", "3");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
