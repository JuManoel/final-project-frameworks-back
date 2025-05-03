package edu.ucaldas.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import edu.ucaldas.back.DTO.UserGetTDO;
import edu.ucaldas.back.models.user.UserData;
import edu.ucaldas.back.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody @Valid UserData user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @GetExchange("/{email}")
    public ResponseEntity<UserGetTDO> getUser(@RequestBody String email) {
        return ResponseEntity.ok(userService.getUser(email));
    }
}
