package fr.alexandreklotz.enigma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EnigmaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnigmaApplication.class, args);
    }

}
