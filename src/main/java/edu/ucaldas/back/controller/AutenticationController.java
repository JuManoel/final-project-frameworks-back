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
 * Provides an endpoint for user login and token generation.
 *
 * Endpoints:
 * - POST /login: Authenticates a user and generates a token.
 *
 * Dependencies:
 * - AuthenticationManager: Used to authenticate the user credentials.
 * - TokenService: Used to generate a token for the authenticated user.
 *
 * Methods:
 * - autenticateUser(LoginDTO userData): Authenticates the user based on the provided
 *   login credentials and returns a token along with user details.
 *
 * Annotations:
 * - @RestController: Indicates that this class is a REST controller.
 * - @RequestMapping("/login"): Maps HTTP requests to the /login path.
 * - @Autowired: Injects the required dependencies.
 */
@RestController
@RequestMapping("/login")
public class AutenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Authenticates a user based on the provided login credentials and generates a token.
     *
     * @param userData The login credentials provided by the user, including email and password.
     *                 This parameter is validated using the @Valid annotation.
     * @return A ResponseEntity containing a TokenDTO object with the generated token,
     *         the authenticated user's name, and their authorities.
     * @throws AuthenticationException If the authentication process fails.
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
