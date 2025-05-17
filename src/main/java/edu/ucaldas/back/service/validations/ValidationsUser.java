package edu.ucaldas.back.service.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ucaldas.back.repository.IUserRepository;


/**
 * Service class providing validation methods related to user existence and activity status.
 * <p>
 * This component interacts with the {@link IUserRepository} to verify whether users exist
 * and are active, based on their unique identifiers or email addresses.
 * </p>
 *
 * <p>
 * Typical usage:
 * <pre>
 *     boolean exists = validationsUser.existsUser(userId);
 *     boolean emailExists = validationsUser.existsUser(email);
 * </pre>
 * </p>
 *
 * @author juan-manoel
 * @see IUserRepository
 */
@Component
public class ValidationsUser {
    @Autowired
    private IUserRepository userRepository;

    /**
     * Checks if an active user exists with the specified email address.
     *
     * @param email the email address to check for existence
     * @return {@code true} if an active user with the given email exists, {@code false} otherwise
     */
    public boolean existsUser(String email) {
        return userRepository.existsByEmailAndIsActiveTrue(email);
    }
}
