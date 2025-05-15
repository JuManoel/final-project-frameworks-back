package edu.ucaldas.back.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.ucaldas.back.infra.exception.ErrorToken;
import edu.ucaldas.back.repository.IUserRepository;
import edu.ucaldas.back.service.TokenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * SecurityFilter is a custom filter that extends OncePerRequestFilter to
 * enforce security measures
 * on incoming HTTP requests. It validates the presence and correctness of an
 * authentication token
 * in the "Authorization" header and sets the authentication in the security
 * context if the token
 * is valid and the user is active.
 * 
 * <p>
 * <b>Key Responsibilities:</b>
 * </p>
 * <ul>
 * <li>Extracts the "Authorization" header from the HTTP request.</li>
 * <li>Validates the token if the header starts with "Bearer ".</li>
 * <li>Retrieves the user details using the token's subject (email).</li>
 * <li>Sets the authentication in the security context if the user is found and
 * active.</li>
 * <li>Throws appropriate exceptions for missing, invalid tokens, or inactive
 * users.</li>
 * </ul>
 * 
 * <p>
 * <b>Dependencies:</b>
 * </p>
 * <ul>
 * <li>{@code TokenService}: A service for token validation and extracting the
 * subject (email).</li>
 * <li>{@code IUserRepository}: A repository for retrieving user details by
 * email.</li>
 * </ul>
 * 
 * <p>
 * <b>Exceptions:</b>
 * </p>
 * <ul>
 * <li>{@code RuntimeException}: Thrown if the token is missing or invalid.</li>
 * <li>{@code UsernameNotFoundException}: Thrown if the user is not found or
 * inactive.</li>
 * </ul>
 * 
 * <p>
 * <b>Usage:</b>
 * </p>
 * <p>
 * This filter is typically used in a Spring Security configuration to intercept
 * and validate
 * requests before they reach the application endpoints.
 * </p>
 * 
 * <p>
 * <b>Example:</b>
 * </p>
 * 
 * <pre>
 * {@code
 * @Component
 * public class SecurityFilter extends OncePerRequestFilter {
 *     // Implementation details
 * }
 * }
 * </pre>
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService; // Assuming you have a TokenService for token validation

    @Autowired
    private IUserRepository userRepository; // Assuming you have a UserRepository for user details

    /**
     * Filters incoming HTTP requests to enforce security measures.
     * <p>
     * This method checks for the presence of an "Authorization" header in the
     * request.
     * If the header is present and starts with "Bearer ", it extracts the token and
     * validates it.
     * Upon successful validation, it retrieves the user details and sets the
     * authentication
     * in the security context. If the token is missing, invalid, or the user is not
     * found or inactive,
     * appropriate exceptions are thrown.
     * </p>
     *
     * @param request     the HTTP request to be filtered
     * @param response    the HTTP response to be sent
     * @param filterChain the filter chain to pass the request and response to the
     *                    next filter
     * @throws ServletException 
     * @throws IOException 
     */
    @SuppressWarnings("null")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Implement your security filter logic here
        // and validate them before allowing access to the requested resource.
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            var token = authorizationHeader.replace("Bearer ", ""); // duvidas sobre os 2 espacos
            var userEmail = tokenService.getSubject(token);
            if (userEmail != null) {
                // Set the authentication in the security context or perform any other necessary
                // actions
                // For example, you can load user details and set them in the security context
                // SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetails user = userRepository.findByEmailAndIsActiveTrue(userEmail);
                if (user != null) {
                    // Set the authentication in the security context or perform any other necessary
                    // actions
                    // SecurityContextHolder.getContext().setAuthentication(authentication);
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Handle the case where the user is not found or inactive
                    throw new EntityNotFoundException("User not found or inactive");
                }
            } else {
                throw new ErrorToken("Invalid token");
            }
        }
        filterChain.doFilter(request, response);
        
    }

}
