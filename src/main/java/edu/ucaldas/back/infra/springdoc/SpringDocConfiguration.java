package edu.ucaldas.back.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    /**
     * Configures a custom OpenAPI bean for the application.
     * 
     * This method sets up the OpenAPI specification with a security scheme
     * that uses the HTTP bearer authentication type with JWT (JSON Web Token)
     * as the bearer format. The configured security scheme is identified by
     * the name "bearer-key".
     * 
     * @return an instance of {@link OpenAPI} with the specified components and security schemes.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
