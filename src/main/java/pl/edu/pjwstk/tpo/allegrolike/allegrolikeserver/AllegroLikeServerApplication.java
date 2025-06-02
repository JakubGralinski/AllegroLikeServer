package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class AllegroLikeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllegroLikeServerApplication.class, args);
    }

}
