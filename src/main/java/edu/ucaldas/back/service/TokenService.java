package edu.ucaldas.back.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import edu.ucaldas.back.models.user.User;
import org.springframework.beans.factory.annotation.Value;

/**
 * Service class for handling JSON Web Token (JWT) operations.
 * This class provides methods to generate and validate JWT tokens
 * for user authentication and authorization.
 * 
 * <p>
 * Features:
 * <ul>
 * <li>Generates JWT tokens with user-specific claims such as email, ID, and
 * name.</li>
 * <li>Sets token expiration time to ensure security.</li>
 * <li>Validates and extracts the subject (email) from a given JWT token.</li>
 * </ul>
 * 
 * <p>
 * Dependencies:
 * <ul>
 * <li>Uses the `com.auth0.jwt` library for JWT creation and verification.</li>
 * <li>Relies on a secret key (`jwt.secret`) for signing and verifying
 * tokens.</li>
 * </ul>
 * 
 * <p>
 * Usage:
 * 
 * <pre>
 * {@code
 * TokenService tokenService = new TokenService();
 * String token = tokenService.generateToken(user);
 * String subject = tokenService.getSubject(token);
 * }
 * </pre>
 * 
 * <p>
 * Note:
 * <ul>
 * <li>The secret key must be securely stored and not exposed in the
 * codebase.</li>
 * <li>Ensure the system clock is synchronized to avoid issues with token
 * expiration.</li>
 * </ul>
 * 
 * @author Your Name
 * @version 1.0
 * @since 2023
 */
@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Generates a JWT (JSON Web Token) for the given user.
     *
     * @param user The user for whom the token is being generated.
     *             It must contain the user's email, ID, and name.
     * @return A signed JWT string containing the user's email as the subject,
     *         their ID and name as claims, and an expiration date.
     * @throws RuntimeException If an error occurs during token generation.
     */
    public String generateToken(User user) {
        /*
         * JWT.create():
         * 
         * Initializes the creation of a new JWT token.
         * .withIssuer("frameworksjs"):
         * 
         * Adds the iss (issuer) claim to the token. This identifies the entity (in this
         * case, "frameworksjs") that issued the token.
         * .withSubject(user.getEmail()):
         * 
         * Adds the sub (subject) claim to the token. This typically identifies the
         * principal (user) for whom the token is issued. Here, the user's email is used
         * as the subject.
         * .withClaim("id", user.getId()):
         * 
         * Adds a custom claim id to the token, storing the user's unique identifier.
         * .withClaim("name", user.getName()):
         * 
         * Adds another custom claim name to the token, storing the user's name.
         * .withExpiresAt(generateExpliration()):
         * 
         * Sets the exp (expiration) claim, which specifies the token's expiration time.
         * The generateExpliration() method is used to calculate this value.
         * .sign(algorithm):
         * 
         * Signs the token using the provided algorithm. This ensures the token's
         * integrity and authenticity.
         */
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("frameworksjs")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withClaim("name", user.getName())
                    .withExpiresAt(generateExpliration())
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    public Instant generateExpliration() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-5:00"));
    }

    /**
     * Extracts the subject from a given JWT token.
     *
     * @param token the JWT token from which the subject is to be extracted.
     *              Must not be null or empty.
     * @return the subject contained in the token.
     * @throws RuntimeException         if the token is null, empty, or cannot be
     *                                  decoded.
     * @throws JWTVerificationException if the token is invalid or fails
     *                                  verification.
     */
    public String getSubject(String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException();
        }
        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            decodedJWT = JWT.require(algorithm).withIssuer("frameworksjs").build().verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            System.out.println("Invalid token: " + e.getMessage());
        }

        if (decodedJWT == null) {
            throw new RuntimeException("Error decoding token");
        }

        return decodedJWT.getSubject();

    }

}
