package edu.ucaldas.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer{
    // Classe responsable por la configuracion del projecto
    // Definimos la configuracion de CORS para permitir el acceso a la API desde cualquier origen
    // y permitir los metodos GET, POST, PUT y DELETE
    /**
     * Configures Cross-Origin Resource Sharing (CORS) mappings for the application.
     * 
     * @param registry the {@link CorsRegistry} to add CORS mappings to
     * 
     * This method allows requests from the specified origin(s) and HTTP methods
     * to access the application's resources. It maps all endpoints (/**) to be
     * accessible from "http://localhost:8080" and supports the following HTTP methods:
     * GET, POST, PUT, DELETE, OPTIONS, HEAD, TRACE, and CONNECT.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD","TRACE","CONNECT");
    }
}
