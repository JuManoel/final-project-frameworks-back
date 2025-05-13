package edu.ucaldas.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is responsible for configuring Cross-Origin Resource Sharing (CORS) 
 * settings for the application. It implements the {@link WebMvcConfigurer} interface 
 * to customize the default Spring MVC configuration.
 * 
 * <p>The CORS configuration allows the application to handle requests from different 
 * origins, enabling communication between the frontend and backend services. 
 * Specifically, it permits requests from "http://localhost:8080" and supports 
 * various HTTP methods such as GET, POST, PUT, DELETE, OPTIONS, HEAD, TRACE, and CONNECT.</p>
 * 
 * <p>By mapping all endpoints (/**), this configuration ensures that the API is 
 * accessible from the specified origin(s) while maintaining flexibility for 
 * development and testing purposes.</p>
 * 
 * <p>Usage of this configuration is essential for enabling secure and controlled 
 * cross-origin requests in a web application.</p>
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    // Classe responsable por la configuracion del projecto
    // Definimos la configuracion de CORS para permitir el acceso a la API desde
    // cualquier origen
    // y permitir los metodos GET, POST, PUT y DELETE
    /**
     * Configures Cross-Origin Resource Sharing (CORS) mappings for the application.
     * 
     * @param registry the {@link CorsRegistry} to add CORS mappings to
     * 
     *                 This method allows requests from the specified origin(s) and
     *                 HTTP methods
     *                 to access the application's resources. It maps all endpoints
     *                 (/**) to be
     *                 accessible from "http://localhost:8080" and supports the
     *                 following HTTP methods:
     *                 GET, POST, PUT, DELETE, OPTIONS, HEAD, TRACE, and CONNECT.
     */
    @Override
    @SuppressWarnings("null")
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
