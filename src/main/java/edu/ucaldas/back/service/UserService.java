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
 * Service class for managing user-related operations.
 * 
 * This class provides methods to handle user operations such as saving a new user,
 * retrieving user details, updating user information, updating user passwords, 
 * and deleting users. It performs necessary validations before executing these 
 * operations to ensure data integrity and consistency.
 * 
 * Dependencies:
 * - IUserRepository: Repository interface for user data persistence.
 * - ValidationsUser: Utility class for user-related validations.
 * - ValidationsRent: Utility class for rent-related validations.
 * - ValidationsHouse: Utility class for house-related validations.
 * 
 * Methods:
 * - saveUser(UserData user): Saves a new user after validating the email.
 * - getUser(String email): Retrieves user details by email if the user exists and is active.
 * - updateUser(UserUpdateDTO userUpdateDTO): Updates user information after performing validations.
 * - updateUserPassword(UserUpdatePasswordDTO userUpdatePasswordDTO): Updates the user's password.
 * - deleteUser(String email): Marks a user as inactive after ensuring no active rents or houses exist.
 * 
 * Exceptions:
 * - EntityAlredyExists: Thrown when attempting to save or update a user with an email that already exists.
 * - EntityNotFoundException: Thrown when a user is not found, or when certain conditions are not met 
 *   (e.g., incorrect password, active rents, or active houses).
 * 
 * Annotations:
 * - @Service: Indicates that this class is a Spring service component.
 * - @Transactional: Ensures atomicity for methods that modify user data.
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
     * Saves a new user to the repository after validating the email.
     *
     * @param user The user data to be saved.
     * @throws EntityAlredyExists if the email provided in the user data already exists.
     */
    public void saveUser(UserData user) {
        if (validationsUser.existsEmail(user.email())) {
            throw new EntityAlredyExists("Email already exists");
        }
        User newUser = new User(user);
        newUser.setPassword(passwordEncoder.encode(user.password()));
        userRepository.save(newUser);
    }

    /**
     * Retrieves a user by their email address if they exist and are active.
     *
     * @param email the email address of the user to retrieve
     * @return a {@link UserGetTDO} object containing the user's details such as
     *         name, email, stars, and type of  user
     * @throws EntityNotFoundException if the user does not exist or is not active
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
     * Updates the information of an existing user.
     *
     * @param userUpdateDTO The data transfer object containing the user's current email,
     *                      new email, and other updated information.
     * @return The updated UserUpdateDTO object.
     * @throws EntityNotFoundException If the user with the provided email does not exist.
     * @throws EntityAlredyExists      If the new email provided already exists in the system.
     */
    @Transactional
    public UserUpdateDTO updateUser(UserUpdateDTO userUpdateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!user.getEmail().equals(userUpdateDTO.newEmail()) && validationsUser.existsEmail(userUpdateDTO.newEmail())) {
            throw new EntityAlredyExists("Email already exists");
        }
        user.updateUser(userUpdateDTO);
        return userUpdateDTO;
    }

    /**
     * Updates the password of a user.
     *
     * @param userUpdatePasswordDTO an object containing the user's email, old password, 
     *                              and the new password to be set.
     * @throws EntityNotFoundException if the user does not exist or if the old password 
     *                                 provided is incorrect.
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
     * Deletes a user by marking them as inactive in the system.
     * 
     * This method performs the following validations before deactivating the user:
     * 1. Checks if the user exists in the system. If not, an EntityNotFoundException is thrown.
     * 2. Checks if the user has any active rents. If so, an EntityNotFoundException is thrown.
     * 3. Checks if the user has any active houses. If so, an EntityNotFoundException is thrown.
     * 
     * If all validations pass, the user's active status is set to false and the changes
     * are saved to the database.
     * 
     * @param email The email of the user to be deleted.
     * @throws EntityNotFoundException if the user does not exist, has active rents, 
     *         or has active houses.
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
