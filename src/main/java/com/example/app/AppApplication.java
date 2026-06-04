package com.example.app;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		Flyway flyway = Flyway.configure()
				.dataSource("jdbc:postgresql://localhost:5432/slicer_db", "postgres", "12345")
				.load();

		flyway.repair();

		flyway.migrate();

		SpringApplication.run(AppApplication.class, args);
	}

}
