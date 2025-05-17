package edu.ucaldas.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Spring configuration class for setting up Cross-Origin Resource Sharing (CORS) in the application.
 * <p>
 * This class implements {@link WebMvcConfigurer} to customize CORS mappings, allowing the frontend
 * running on <code>http://localhost:8080</code> to interact with the backend. It enables HTTP methods
 * such as GET, POST, PUT, and DELETE for all endpoints.
 * </p>
 *
 * <p>
 * Usage:
 * <ul>
 *   <li>Allows cross-origin requests from the specified origin.</li>
 *   <li>Restricts allowed HTTP methods to GET, POST, PUT, and DELETE.</li>
 *   <li>Applies these settings to all API endpoints ("/**").</li>
 * </ul>
 * </p>
 *
 * @author juan-manoel
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 * @see org.springframework.web.servlet.config.annotation.CorsRegistry
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    /**
     * Configures Cross-Origin Resource Sharing (CORS) mappings for the application.
     * <p>
     * This method allows HTTP requests from the specified origin ("http://localhost:8080")
     * to access resources on the server. It permits the HTTP methods GET, POST, PUT, and DELETE
     * for all endpoints ("/**").
     *
     * @param registry the {@link CorsRegistry} to which CORS mappings are added
     */
    @Override
    @SuppressWarnings("null")
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
