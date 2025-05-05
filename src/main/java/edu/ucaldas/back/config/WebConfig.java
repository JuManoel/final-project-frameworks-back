package edu.ucaldas.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures resource handlers for serving static resources.
     * This method maps requests with the specified URL pattern to the corresponding
     * resource locations.
     *
     * @param registry the {@link ResourceHandlerRegistry} used to register resource handlers.
     *                 In this case, it maps requests starting with "/images/**" to the
     *                 "file:uploads/" directory on the file system.
     */
    @SuppressWarnings("null")
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/");
    }

}
