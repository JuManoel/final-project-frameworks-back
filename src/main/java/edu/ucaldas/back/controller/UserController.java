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
 * REST controller for managing user-related operations.
 * <p>
 * This controller provides endpoints for creating, retrieving, updating, and deleting users,
 * as well as updating user passwords. All endpoints are prefixed with <code>/user</code>.
 * </p>
 * 
 * <ul>
 *   <li><b>POST /user</b>: Create a new user.</li>
 *   <li><b>GET /user/{email}</b>: Retrieve a user by email.</li>
 *   <li><b>PUT /user/update</b>: Update user information.</li>
 *   <li><b>PUT /user/update/password</b>: Update a user's password.</li>
 *   <li><b>DELETE /user</b>: Delete a user.</li>
 * </ul>
 * 
 * <p>
 * All methods delegate business logic to the {@link UserService}.
 * </p>
 * 
 * @author juan-manoel
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Handles HTTP POST requests to create a new user.
     *
     * @param user The user data to be created, validated from the request body.
     * @return A ResponseEntity containing a success message if the user is created successfully.
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
     * @return a ResponseEntity containing the UserGetTDO object for the specified user
     */
    @GetMapping("/{email}")
    public ResponseEntity<UserGetTDO> getUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUser(email));
    }

    /**
     * Updates an existing user's information.
     *
     * @param user the {@link UserUpdateDTO} object containing updated user details; must be valid
     * @return a {@link ResponseEntity} containing the updated {@link UserUpdateDTO}
     */
    @PutMapping("/update")
    public ResponseEntity<UserUpdateDTO> updateUser(@RequestBody @Valid UserUpdateDTO user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }


    /**
     * Updates the password of a user.
     *
     * <p>This endpoint receives a {@link UserUpdatePasswordDTO} object containing the user's
     * credentials and the new password. It delegates the password update operation to the
     * {@code userService}. If the update is successful, it returns a response indicating
     * that the password was updated successfully.</p>
     *
     * @param userUpdatePasswordDTO the DTO containing the user's current and new password information
     * @return a {@link ResponseEntity} with a success message if the password was updated
     */
    @PutMapping("/update/password")
    public ResponseEntity<String> updateUserPassword(@RequestBody @Valid UserUpdatePasswordDTO userUpdatePasswordDTO) {
        userService.updateUserPassword(userUpdatePasswordDTO);
        return ResponseEntity.ok("Password updated successfully");
    }

    /**
     * Handles HTTP DELETE requests to delete a user.
     * 
     * @return ResponseEntity containing a success message if the user is deleted successfully.
     */
    @DeleteMapping()
    public ResponseEntity<String> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok("User deleted successfully");
    }

}
