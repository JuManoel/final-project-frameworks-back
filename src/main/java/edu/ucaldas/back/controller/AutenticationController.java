package edu.ucaldas.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


import edu.ucaldas.back.DTO.LoginDTO;
import edu.ucaldas.back.DTO.TokenDTO;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.service.TokenService;
import jakarta.validation.Valid;

/**
 * Controller responsible for handling user authentication requests.
 * <p>
 * Exposes an endpoint for user login, authenticates credentials, and issues JWT tokens upon successful authentication.
 * </p>
 * <ul>
 *   <li>POST /login: Authenticates a user and returns a JWT token along with user details.</li>
 * </ul>
 *
 * Dependencies:
 * <ul>
 *   <li>{@link AuthenticationManager} for authenticating user credentials.</li>
 *   <li>{@link TokenService} for generating JWT tokens.</li>
 * </ul>
 *
 * Example usage:
 * <pre>
 * POST /login
 * {
 *   "email": "user@example.com",
 *   "password": "password123"
 * }
 * </pre>
 *
 * Response:
 * <pre>
 * {
 *   "token": "jwt-token",
 *   "email": "user@example.com",
 *   "name": "User Name",
 *   "typeUser": "ADMIN"
 * }
 * </pre>
 *
 * @author juan-manoel
 */
@RestController
@RequestMapping("/login")
public class AutenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Authenticates a user based on the provided login credentials.
     * <p>
     * Expects a {@link LoginDTO} object containing the user's email and password.
     * If authentication is successful, generates a JWT token and returns it along with
     * user details in a {@link TokenDTO} object.
     * </p>
     *
     * @param userData the login credentials of the user (email and password)
     * @return a {@link ResponseEntity} containing the generated token and user information
     */
    @PostMapping()
    public ResponseEntity<TokenDTO> autenticateUser(@RequestBody @Valid LoginDTO userData) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userData.email(), userData.password());
        var userAuthenticated = authenticationManager.authenticate(authentication);
        var user = (User) userAuthenticated.getPrincipal(); // Retrieve the User object
        var token = tokenService.generateToken(user);
        TokenDTO tokenDTO = new TokenDTO(token, user.getEmail(), user.getName(), user.getTypeUser().toString());
        return ResponseEntity.ok(tokenDTO);
    }
}
