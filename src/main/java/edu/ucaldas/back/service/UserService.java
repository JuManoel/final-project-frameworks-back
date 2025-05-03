package edu.ucaldas.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.UserGetTDO;
import edu.ucaldas.back.infra.exception.EntityAlredyExists;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.models.user.UserData;
import edu.ucaldas.back.repository.IUserRepository;
import edu.ucaldas.back.service.validations.ValidationsUser;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ValidationsUser validationsUser;

    public void saveUser(UserData user) {
        if (validationsUser.existsEmail(user.email())) {
            throw new EntityAlredyExists("Email already exists");
        }
        User newUser = new User(user);
        userRepository.save(newUser);
    }

    public UserGetTDO getUser(String email) {
        if (!validationsUser.existsUser(email)) {
            throw new EntityNotFoundException("User not found");
        }
        User user = userRepository.getByEmailAndIsActiveTrue(email).get();
        UserGetTDO userGetTDO = new UserGetTDO(user.getName(), user.getEmail(), user.getStars(), user.getTypeUser());
        return userGetTDO;
    }
}
