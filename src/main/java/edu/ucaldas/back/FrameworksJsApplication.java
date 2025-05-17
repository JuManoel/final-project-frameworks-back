package edu.ucaldas.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the FrameworksJs Spring Boot application.
 * <p>
 * This class serves as the entry point for the application and is responsible for bootstrapping
 * the Spring Boot context.
 * </p>
 *
 * <p>
 * Usage:
 * <pre>
 *     java -jar frameworksjs.jar
 * </pre>
 * </p>
 *
 * @author juan-manoel
 * @since 1.0
 */
@SpringBootApplication
public class FrameworksJsApplication {

	/**
	 * The entry point of the Spring Boot application.
	 * <p>
	 * This method starts the application by invoking {@link SpringApplication#run(Class, String...)}
	 * with the current application class and command-line arguments.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(FrameworksJsApplication.class, args);
	}
}