package edu.ucaldas.back.service.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ucaldas.back.repository.IUserRepository;

/**
 * The {@code ValidationsUser} class provides validation methods for user-related operations.
 * It interacts with the {@code IUserRepository} to perform checks such as verifying the existence
 * of users or email addresses in the repository.
 *
 * <p>Methods in this class include:
 * <ul>
 *   <li>{@link #existsEmail(String)} - Checks if an email address exists in the repository.</li>
 *   <li>{@link #existsUser(Long)} - Checks if a user with a given ID exists and is active.</li>
 *   <li>{@link #existsUser(String)} - Checks if a user exists in the repository based on their email.</li>
 * </ul>
 *
 * <p>This class is annotated with {@code @Component}, making it a Spring-managed bean.
 * It uses {@code @Autowired} to inject the {@code IUserRepository} dependency.
 */
@Component
public class ValidationsUser {
    @Autowired
    private IUserRepository userRepository;

    /**
     * Checks if an email address already exists in the user repository.
     *
     * @param email the email address to check for existence
     * @return {@code true} if the email exists in the repository, {@code false} otherwise
     */
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Checks if a user with the given ID exists and is active.
     *
     * @param id the ID of the user to check
     * @return {@code true} if a user with the given ID exists and is active, {@code false} otherwise
     */
    public boolean existsUser(Long id) {
        return userRepository.findByIdAndIsActiveTrue(id).isPresent();
    }

    /**
     * Checks if a user exists in the repository based on their email.
     *
     * @param email the email of the user to check for existence
     * @return true if a user with the given email exists, false otherwise
     */
    public boolean existsUser(String email) {
        return userRepository.existsByEmail(email);
    }
}
