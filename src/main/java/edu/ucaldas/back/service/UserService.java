package edu.ucaldas.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.UserGetTDO;
import edu.ucaldas.back.DTO.UserUpdateDTO;
import edu.ucaldas.back.DTO.UserUpdatePasswordDTO;
import edu.ucaldas.back.infra.exception.EntityAlredyExists;
import edu.ucaldas.back.infra.exception.NotPermited;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.models.user.UserData;
import edu.ucaldas.back.repository.IUserRepository;
import edu.ucaldas.back.service.validations.ValidationsHouse;
import edu.ucaldas.back.service.validations.ValidationsRent;
import edu.ucaldas.back.service.validations.ValidationsUser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Service class for managing user-related operations such as registration, retrieval,
 * update, password management, and deactivation.
 * <p>
 * This service handles business logic for user management, including:
 * <ul>
 *     <li>Registering new users with unique email validation and password encoding.</li>
 *     <li>Retrieving user information by email.</li>
 *     <li>Updating user profile and password for the currently authenticated user.</li>
 *     <li>Deactivating users after checking for active rents or houses.</li>
 * </ul>
 * <p>
 * Exceptions are thrown for cases such as duplicate emails, not found entities,
 * and forbidden operations when business rules are violated.
 * </p>
 *
 * @author juan-manoel
 */
@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ValidationsUser validationsUser;

    @Autowired
    private ValidationsRent validationsRent;

    @Autowired
    private ValidationsHouse validationsHouse;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Saves a new user to the repository after validating that the email does not already exist.
     * If a user with the given email already exists, an EntityAlredyExists exception is thrown.
     * The user's password is securely encoded before saving.
     *
     * @param user the user data to be saved
     * @throws EntityAlredyExists if a user with the provided email already exists
     */
    public void saveUser(UserData user) {
        if (validationsUser.existsUser(user.email())) {
            throw new EntityAlredyExists("Email already exists");
        }
        User newUser = new User(user);
        newUser.setPassword(passwordEncoder.encode(user.password()));
        userRepository.save(newUser);
    }

    /**
     * Retrieves a user's information based on their email address.
     *
     * @param email the email address of the user to retrieve
     * @return a {@link UserGetTDO} object containing the user's name, email, stars, and user type
     * @throws EntityNotFoundException if no user exists with the provided email
     */
    public UserGetTDO getUser(String email) {
        if (!validationsUser.existsUser(email)) {
            throw new EntityNotFoundException("User not found");
        }
        User user = userRepository.getUser(email).get();
        UserGetTDO userGetTDO = new UserGetTDO(user.getName(), user.getEmail(), user.getStars(), user.getTypeUser());
        return userGetTDO;
    }


    /**
     * Updates the current authenticated user's information with the provided data.
     * <p>
     * This method retrieves the currently authenticated user from the security context,
     * checks if the new email provided in the {@code userUpdateDTO} already exists in the system
     * (if it is different from the current email), and throws an {@link EntityAlredyExists}
     * exception if the email is already taken. If the email is available or unchanged,
     * it updates the user's information accordingly.
     * </p>
     *
     * @param userUpdateDTO the data transfer object containing the new user information
     * @return the updated {@link UserUpdateDTO}
     * @throws EntityAlredyExists if the new email already exists in the system
     */
    @Transactional
    public UserUpdateDTO updateUser(UserUpdateDTO userUpdateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!user.getEmail().equals(userUpdateDTO.newEmail()) && validationsUser.existsUser(userUpdateDTO.newEmail())) {
            throw new EntityAlredyExists("Email already exists");
        }
        user.updateUser(userUpdateDTO);
        return userUpdateDTO;
    }

    /**
     * Updates the password of the currently authenticated user.
     * <p>
     * This method verifies that the provided old password matches the user's current password.
     * If the old password is correct, it encodes and updates the user's password with the new one.
     * If the old password does not match, an {@link EntityNotFoundException} is thrown.
     * </p>
     *
     * @param userUpdatePasswordDTO Data transfer object containing the old and new passwords.
     * @throws EntityNotFoundException if the old password is incorrect.
     */
    @Transactional
    public void updateUserPassword(UserUpdatePasswordDTO userUpdatePasswordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!passwordEncoder.matches(userUpdatePasswordDTO.password(), user.getPassword())) {
            throw new EntityNotFoundException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(userUpdatePasswordDTO.newPassword()));
        userRepository.save(user);
    }

    /**
     * Deactivates the currently authenticated user by setting their active status to false.
     * <p>
     * This method first checks if the user has any active rents or houses using the
     * {@code validationsRent} and {@code validationsHouse} services. If the user has active
     * rents or houses, a {@link NotPermited} exception is thrown and the user cannot be deactivated.
     * Otherwise, the user's active status is set to false and the change is persisted.
     * </p>
     *
     * @throws NotPermited if the user has active rents or houses.
     */
    @Transactional
    public void deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User login = (User) authentication.getPrincipal();
        if(validationsRent.userHasRent(login.getEmail())){
            throw new NotPermited("User has active rents");
        }
        if(validationsHouse.userHasHouse(login.getEmail())){
            throw new NotPermited("User has active houses");
        }
        login.setActive(false);
        userRepository.save(login);
    }

}
