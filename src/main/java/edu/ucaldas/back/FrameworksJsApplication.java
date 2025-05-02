package edu.ucaldas.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Spring Boot application.
 * <p>
 * This class is annotated with {@code @SpringBootApplication}, which is a 
 * convenience annotation that combines {@code @Configuration}, 
 * {@code @EnableAutoConfiguration}, and {@code @ComponentScan}.
 * </p>
 * <p>
 * The {@code main} method uses {@code SpringApplication.run} to launch the 
 * application.
 * </p>
 */
@SpringBootApplication
public class FrameworksJsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrameworksJsApplication.class, args);
	}
}