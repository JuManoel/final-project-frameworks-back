package edu.ucaldas.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.repository.IUserRepository;

/**
 * Service responsible for handling user authentication logic.
 * <p>
 * Implements the {@link UserDetailsService} interface to provide user details
 * retrieval based on email for Spring Security authentication.
 * </p>
 * <p>
 * This service interacts with the {@link IUserRepository} to fetch user information
 * from the data source, ensuring that only active users can be authenticated.
 * </p>
 *
 * @author juan-manoel
 */
@Service
public class AutenticationService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    /**
     * Loads the user details for authentication based on the provided login (email).
     * This method retrieves a user from the repository whose email matches the given login
     * and who is marked as active.
     *
     * @param login the email of the user to be loaded
     * @return the UserDetails of the active user with the specified email
     * @throws UsernameNotFoundException if no active user with the given email is found
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByEmailAndIsActiveTrue(login);
    }

}
