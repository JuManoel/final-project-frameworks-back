package edu.ucaldas.back.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfiguration class is responsible for configuring the security
 * settings of the application.
 * 
 * <p>
 * This class leverages Spring Security to define the security filter chain,
 * authentication manager,
 * and password encoder. It integrates a custom security filter for token
 * validation and sets up
 * security rules for HTTP requests.
 * 
 * <p>
 * Key Features:
 * <ul>
 * <li>Disables CSRF protection to simplify API interactions.</li>
 * <li>Configures session management to be stateless, ensuring no server-side
 * session is maintained.</li>
 * <li>Defines authorization rules, allowing public access to the "/login"
 * endpoint while securing all other endpoints.</li>
 * <li>Integrates a custom security filter into the filter chain before the
 * default authentication filter.</li>
 * <li>Provides a bean for password encoding using the BCrypt hashing algorithm
 * for secure password storage.</li>
 * <li>Exposes an {@link AuthenticationManager} bean for authentication
 * purposes.</li>
 * </ul>
 * 
 * <p>
 * Annotations:
 * <ul>
 * <li>{@link Configuration} - Marks this class as a configuration class for
 * Spring.</li>
 * <li>{@link EnableWebSecurity} - Enables Spring Security's web security
 * support.</li>
 * </ul>
 * 
 * <p>
 * Beans Provided:
 * <ul>
 * <li>{@link SecurityFilterChain} - Configures the security filter chain for
 * the application.</li>
 * <li>{@link AuthenticationManager} - Provides an authentication manager for
 * handling authentication.</li>
 * <li>{@link PasswordEncoder} - Supplies a password encoder using BCrypt for
 * secure password hashing.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter; // Custom security filter for token validation

    /**
     * Configures the security filter chain for the application.
     * 
     * <p>
     * This method sets up the security configuration for the application, including
     * disabling CSRF protection, setting session management to stateless, and
     * defining
     * authorization rules for HTTP requests. Additionally, it integrates a custom
     * security filter into the filter chain.
     * 
     * @param http the {@link HttpSecurity} object used to configure security
     *             settings
     * @return the configured {@link SecurityFilterChain} instance
     * @throws Exception if an error occurs during the configuration process
     * 
     *                   <ul>
     *                   <li>CSRF protection is disabled to simplify API
     *                   interactions.</li>
     *                   <li>Session management is set to stateless to ensure no
     *                   server-side session is maintained.</li>
     *                   <li>The "/login" endpoint is accessible to all users
     *                   without authentication.</li>
     *                   <li>All other endpoints require authentication.</li>
     *                   <li>A custom security filter is added before the default
     *                   {@link UsernamePasswordAuthenticationFilter}.</li>
     *                   </ul>
     */
    @Bean
    @SuppressWarnings("removal")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable CSRF protection
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set session management to stateless
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/user").permitAll() // Allow access to login and user creation endpoints
                .anyRequest().authenticated() // Require authentication for all other requests
                .and()
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class); // Add custom security                                                                               // filter
        return http.build();
    }

    /**
     * Creates and provides an instance of {@link AuthenticationManager} using the
     * provided {@link AuthenticationConfiguration}.
     *
     * @param authenticationConfiguration the configuration object used to retrieve
     *                                    the {@link AuthenticationManager}.
     * @return an instance of {@link AuthenticationManager}.
     * @throws Exception if an error occurs while retrieving the
     *                   {@link AuthenticationManager}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates and provides a bean for password encoding using the BCrypt hashing
     * algorithm.
     * BCrypt is a strong and secure algorithm designed for password hashing,
     * ensuring
     * that passwords are stored in a way that is resistant to brute force attacks.
     *
     * @return a {@link PasswordEncoder} instance that uses BCrypt for encoding
     *         passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
