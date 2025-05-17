package edu.ucaldas.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig is a Spring configuration class that customizes the web application's resource handling.
 * <p>
 * It implements {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurer} to provide
 * additional configuration for serving static resources. Specifically, it maps HTTP requests with the
 * "/images/**" pattern to files located in the local "uploads/" directory, making them accessible via
 * the "/images/" URL path.
 * </p>
 *
 * <p>
 * Example: A file stored at "uploads/example.jpg" will be accessible at "http://<host>/images/example.jpg".
 * </p>
 *
 * <p>
 * This configuration is useful for serving user-uploaded files or other static content that resides
 * outside the application's packaged resources.
 * </p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures resource handlers for serving static resources.
     * <p>
     * This method maps requests to "/images/**" to the local file system directory "uploads/".
     * Any files placed in the "uploads/" directory will be accessible via the "/images/" URL path.
     *
     * @param registry the {@link ResourceHandlerRegistry} to configure resource handlers
     */
    @SuppressWarnings("null")
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/");
    }

}
