package edu.ucaldas.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.repository.IUserRepository;

/**
 * Service class for handling user authentication.
 * Implements the {@link UserDetailsService} interface to provide custom user
 * authentication logic.
 * 
 * This service is responsible for loading user details based on the provided
 * login information.
 * It interacts with the {@link IUserRepository} to fetch user data from the
 * database.
 * 
 * Dependencies:
 * - {@link IUserRepository}: Repository for accessing user data.
 * 
 * Methods:
 * - {@code loadUserByUsername(String login)}: Loads user details by email and
 * ensures the user is active.
 * 
 * Annotations:
 * - {@code @Service}: Marks this class as a Spring service component.
 * - {@code @Autowired}: Injects the {@link IUserRepository} dependency.
 */
@Service
public class AutenticationService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    /**
     * Loads the user details based on the provided login (email).
     * This method retrieves an active user from the repository using their email.
     *
     * @param login the email of the user to be loaded.
     * @return the UserDetails object containing user information.
     * @throws UsernameNotFoundException if no active user is found with the given
     *                                   email.
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByEmailAndIsActiveTrue(login);
    }

}
