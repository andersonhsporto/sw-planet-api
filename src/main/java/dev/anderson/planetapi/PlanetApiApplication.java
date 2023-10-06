package dev.anderson.planetapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication
public class PlanetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanetApiApplication.class, args);
	}

}
