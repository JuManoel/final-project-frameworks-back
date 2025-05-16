package edu.ucaldas.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ucaldas.back.DTO.UserGetTDO;
import edu.ucaldas.back.DTO.UserUpdateDTO;
import edu.ucaldas.back.DTO.UserUpdatePasswordDTO;
import edu.ucaldas.back.models.user.UserData;
import edu.ucaldas.back.service.UserService;
import jakarta.validation.Valid;

/**
 * UserController is a REST controller that handles HTTP requests related to user operations.
 * It provides endpoints for creating, retrieving, updating, and deleting user information.
 * 
 * Endpoints:
 * - POST /user: Creates a new user.
 * - GET /user/{email}: Retrieves user information by email.
 * - POST /user/update: Updates user information.
 * - POST /user/update/password: Updates the user's password.
 * - DELETE /user/{email}: Deletes a user by email.
 * 
 * Dependencies:
 * - UserService: Service layer for handling user-related business logic.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Handles the HTTP POST request to create a new user.
     *
     * @param user The user data provided in the request body. It must be valid and conform to the constraints defined in the {@code UserData} class.
     * @return A {@link ResponseEntity} containing a success message if the user is created successfully.
     */
    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody @Valid UserData user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to retrieve
     * @return a ResponseEntity containing the UserGetTDO object with the user's details
     */
    @GetMapping("/{email}")
    public ResponseEntity<UserGetTDO> getUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUser(email));
    }

    /**
     * Updates the details of an existing user.
     *
     * @param user The user data transfer object containing the updated user details.
     *             This parameter is validated to ensure it meets the required constraints.
     * @return A ResponseEntity containing the updated UserUpdateDTO object.
     *         The response is returned with an HTTP status of 200 (OK).
     */
    @PutMapping("/update")
    public ResponseEntity<UserUpdateDTO> updateUser(@RequestBody @Valid UserUpdateDTO user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    /**
     * Updates the password of a user.
     *
     * @param userUpdatePasswordDTO the data transfer object containing the user's 
     *                              current password, new password, and any other 
     *                              necessary information for the update.
     * @return a ResponseEntity containing a success message if the password 
     *         update is successful.
     */
    @PutMapping("/update/password")
    public ResponseEntity<String> updateUserPassword(@RequestBody @Valid UserUpdatePasswordDTO userUpdatePasswordDTO) {
        userService.updateUserPassword(userUpdatePasswordDTO);
        return ResponseEntity.ok("Password updated successfully");
    }

    /**
     * Deletes a user identified by their email address.
     *
     * @param email the email address of the user to be deleted
     * @return a ResponseEntity containing a success message if the user is deleted successfully
     */
    @DeleteMapping()
    public ResponseEntity<String> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok("User deleted successfully");
    }

}
